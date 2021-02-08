package be.vinci.pae.api;

import java.util.List;

import be.vinci.pae.api.filters.AuthorizationRequestFilter;
import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.domain.*;
import be.vinci.pae.domain.Page;
import be.vinci.pae.services.DataServiceUserCollection;
import be.vinci.pae.services.DataServicePageCollection;
import be.vinci.pae.services.DataServicePageCollection;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Singleton
@Path("/pages")
public class PageResource {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Authorize
    public Page create(Page page) {
        if (page == null || page.getTitle() == null || page.getTitle().isEmpty())
            throw new WebApplicationException(
                    Response.status(Status.BAD_REQUEST).entity("Lacks of mandatory info").type("text/plain").build());
        System.out.println(page.toString());
        DataServicePageCollection.addPage(page);

        return page;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Authorize
    public Page getPage(@PathParam("id") int id, @Context ContainerRequestContext requestContext) {
        if (id == 0)
            throw new WebApplicationException(Response.status(Status.BAD_REQUEST).entity("Lacks of mandatory id info")
                    .type("text/plain").build());
        Page pageFound = DataServicePageCollection.getPage(id);
        User u = (User) requestContext.getProperty("user");
        if (u.getID() != pageFound.getAuteur() && pageFound.getPubStatus().equals("hidden")) {
            throw new WebApplicationException(Response.status(Status.NOT_FOUND).entity("Not sufficient rights to see this content").type("text/plain").build());
        }

        if (pageFound == null)
            throw new WebApplicationException(Response.status(Status.NOT_FOUND)
                    .entity("Resource with id = " + id + " could not be found").type("text/plain").build());

        return pageFound;
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Authorize
    public Page updatePage(Page page, @PathParam("id") int id, @Context ContainerRequestContext requestContext) {

        if (page == null || page.getTitle() == null || page.getTitle().isEmpty())
            throw new WebApplicationException(
                    Response.status(Status.BAD_REQUEST).entity("Lacks of mandatory info").type("text/plain").build());
        User u = (User) requestContext.getProperty("user");
        page.setId(id);
        if (u.getID() != page.getAuteur()) {
            throw new WebApplicationException(Response.status(Status.NOT_FOUND).entity("Not sufficient rights to see this content").type("text/plain").build());
        }
        Page updatedPage = DataServicePageCollection.updatePage(page);

        if (updatedPage == null)
            throw new WebApplicationException(Response.status(Status.NOT_FOUND)
                    .entity("Resource with id = " + id + " could not be found").type("text/plain").build());

        return updatedPage;
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Authorize
    public Page deletePage(@PathParam("id") int id, @Context ContainerRequestContext requestContext) {
        if (id == 0)
            throw new WebApplicationException(Response.status(Status.BAD_REQUEST).entity("Lacks of mandatory id info")
                    .type("text/plain").build());
        User u = (User) requestContext.getProperty("user");
        Page deletedPage = DataServicePageCollection.getPage(id);
        if (u.getID() != deletedPage.getAuteur()) {
            throw new WebApplicationException(Response.status(Status.NOT_FOUND).entity("Not sufficient rights to see this content").type("text/plain").build());
        }
        deletedPage = DataServicePageCollection.deletePage(id);

        if (deletedPage == null)
            throw new WebApplicationException(Response.status(Status.NOT_FOUND)
                    .entity("Ressource with id = " + id + " could not be found").type("text/plain").build());

        return deletedPage;
    }

    // Public pages
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Page> getAllPages() {
        return DataServicePageCollection.getPages();

    }

}
