package be.vinci.pae.services;

import be.vinci.pae.domain.Page;
import be.vinci.pae.domain.PublicUser;
import be.vinci.pae.domain.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.text.StringEscapeUtils;
import org.glassfish.jersey.inject.hk2.RequestContext;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataServicePageCollection {
    private static final String DB_FILE_PATH = "db.json";
    private static final String COLLECTION_NAME = "pages";
    private final static ObjectMapper jsonMapper = new ObjectMapper();

    private static List<Page> pages;
    static {
        pages = loadDataFromFile();
    }
    // TODO Only if status is public or if user is author of page
    public static Page getPage(int id) {
        return pages.stream().filter(item -> item.getId() == id).findAny().orElse(null);
    }

    public static List<Page> getAuthorHiddenPages(int authorId) {
        return pages.stream().filter(item -> item.getPubStatus().equals("hidden")).filter(item -> item.getAuteur() == authorId).collect(Collectors.toList());
    }
    // TODO Return only pages that have public status
    public static List<Page> getPages(User requestor) {
        List<Page> publishedPages = pages.stream().filter(item -> item.getPubStatus().equals("published")).collect(Collectors.toList());
        List<Page> authorHiddenPages = getAuthorHiddenPages(requestor.getID());
        System.out.println(publishedPages);
        System.out.println(authorHiddenPages);

        return Stream.concat(publishedPages.stream(), authorHiddenPages.stream())
                .collect(Collectors.toList());
    }

    public static Page addPage(Page page) {
        System.out.println("Addpage");
        System.out.println(page.getAuteur());
        page.setId(nextPageId());
        // XSS protection
        page.setTitle(StringEscapeUtils.escapeHtml4(page.getTitle()));
        page.setUri(StringEscapeUtils.escapeHtml4(page.getUri()));
        page.setContent(StringEscapeUtils.escapeHtml4(page.getContent()));
        page.setPubStatus(StringEscapeUtils.escapeHtml4(page.getPubStatus()));

        page.setAuteur(DataServiceUserCollection.getUser(page.getAuteur()).getID());

        pages.add(page);
        saveDataToFile();
        return page;
    }

    public static int nextPageId() {
        if (pages.size() == 0)
            return 1;
        return pages.get(pages.size() - 1).getId() + 1;
    }
    // Only author can delete
    public static Page deletePage(int id) {
        if (pages.size() == 0 | id == 0)
            return null;
        Page filmToDelete = getPage(id);
        if (filmToDelete == null)
            return null;
        int index = pages.indexOf(filmToDelete);
        pages.remove(index);
        saveDataToFile();
        return filmToDelete;
    }
    // Only author can update
    public static Page updatePage(Page page) {
        if (pages.size() == 0 | page == null)
            return null;
        Page filmToUpdate = getPage(page.getId());
        if (filmToUpdate == null)
            return null;
        // escape dangerous chars to protect against XSS attacks
        page.setTitle(StringEscapeUtils.escapeHtml4(page.getTitle()));
        page.setUri(StringEscapeUtils.escapeHtml4(page.getUri()));
        // update the data structure
        int index = pages.indexOf(filmToUpdate);
        pages.set(index, page);
        saveDataToFile();
        return page;
    }

    private static List<Page> loadDataFromFile() {
        try {
            JsonNode node = jsonMapper.readTree(Paths.get(DB_FILE_PATH).toFile());
            JsonNode collection = node.get(COLLECTION_NAME);
            if (collection == null)
                return new ArrayList<Page>();
            return jsonMapper.readerForListOf(Page.class).readValue(node.get(COLLECTION_NAME));

        } catch (FileNotFoundException e) {
            return new ArrayList<Page>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<Page>();
        }
    }

    private static void saveDataToFile() {
        try {

            // get all collections
            Path pathToDb = Paths.get(DB_FILE_PATH);
            if (!Files.exists(pathToDb)) {
                // write a new collection to the db file
                ObjectNode newCollection = jsonMapper.createObjectNode().putPOJO(COLLECTION_NAME, pages );
                jsonMapper.writeValue(pathToDb.toFile(), newCollection);
                return;

            }
            // get all collections
            JsonNode allCollections = jsonMapper.readTree(pathToDb.toFile());

            if (allCollections.has(COLLECTION_NAME)) {// remove current collection
                ((ObjectNode) allCollections).remove(COLLECTION_NAME);
            }

            // create a new JsonNode and add it to allCollections
            String currentCollectionAsString = jsonMapper.writeValueAsString(pages);
            JsonNode updatedCollection = jsonMapper.readTree(currentCollectionAsString);
            ((ObjectNode) allCollections).putPOJO(COLLECTION_NAME, updatedCollection);

            // write to the db file
            jsonMapper.writeValue(pathToDb.toFile(), allCollections);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
