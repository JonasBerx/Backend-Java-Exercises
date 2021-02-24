package be.vinci.pae.services;

import be.vinci.pae.api.utils.Json;
import be.vinci.pae.domain.News;
import be.vinci.pae.domain.NewsFactory;
import be.vinci.pae.utils.Config;
import jakarta.inject.Inject;
import org.apache.commons.text.StringEscapeUtils;

import java.util.List;

public class DataServiceNewsCollectionImpl implements DataServiceNewsCollection {
    private static final String DB_FILE_PATH = Config.getProperty("DatabaseFilePath");
    private static final String COLLECTION_NAME = "news";

    private List<News> newsList;

    @Inject
    private NewsFactory factory;

    public DataServiceNewsCollectionImpl() {
        this.newsList = Json.loadDataFromFile(DB_FILE_PATH, COLLECTION_NAME, News.class);
        System.out.println("NEWS DATA SERVICE");
    }

    @Override
    public News getNews(int id) {
        return this.newsList.stream().filter(item -> item.getId() == id).findAny().orElse(null);
    }

    @Override
    public List<News> getNewsList() {
        return this.newsList;
    }

    @Override
    public News addNews(News news) {
        news.setId(nextNewsId());
        news.setTitle(StringEscapeUtils.escapeHtml4(news.getTitle()));
        news.setDescription(StringEscapeUtils.escapeHtml4(news.getDescription()));
        news.setContent(StringEscapeUtils.escapeHtml4(news.getContent()));

        this.newsList.add(news);
        Json.saveDataToFile(this.newsList, DB_FILE_PATH, COLLECTION_NAME);
        return news;
    }

    @Override
    public int nextNewsId() {
        if (this.newsList.size() == 0) {
            return 1;
        }
        return this.newsList.get(this.newsList.size() - 1).getId() + 1;
    }

    @Override
    public News updateNews(News news) {
        if (this.newsList.size() == 0 | news == null) {
            return null;
        }
        News newsToUpdate = getNews(news.getId());
        if (newsToUpdate == null) {
            return null;
        }
        news.setTitle(StringEscapeUtils.escapeHtml4(news.getTitle()));
        news.setDescription(StringEscapeUtils.escapeHtml4(news.getDescription()));
        news.setContent(StringEscapeUtils.escapeHtml4(news.getContent()));
        int index = this.newsList.indexOf(newsToUpdate);
        this.newsList.set(index, news);

        Json.saveDataToFile(this.newsList, DB_FILE_PATH, COLLECTION_NAME);

        return news;
    }

    @Override
    public News deleteNews(int id) {
        if (this.newsList.size() == 0 | id == 0) {
            return null;
        }
        News newsToDelete = getNews(id);
        if (newsToDelete == null) {
            return null;
        }
        int index = this.newsList.indexOf(newsToDelete);
        this.newsList.remove(index);
        Json.saveDataToFile(this.newsList, DB_FILE_PATH, COLLECTION_NAME);

        return newsToDelete;

    }
}
