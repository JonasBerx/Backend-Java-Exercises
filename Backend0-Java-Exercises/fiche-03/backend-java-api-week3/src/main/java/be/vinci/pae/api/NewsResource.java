package be.vinci.pae.api;


import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.domain.News;
import be.vinci.pae.domain.NewsFactory;
import be.vinci.pae.services.DataServiceNewsCollection;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Singleton
@Path("/news")
public class NewsResource {
    @Inject
    private DataServiceNewsCollection dataService;
    @Inject
    private NewsFactory newsFactory;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Authorize
    public News create(News news) {
        if (news == null || news.getTitle() == null || news.getTitle().isEmpty()) {
            throw new WebApplicationException("Lack of mandatory info", null, Response.Status.BAD_REQUEST);
        }
        this.dataService.addNews(news);
        return news;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Authorize
    public News getNews(@PathParam("id") int id) {
        if (id == 0)
            throw new WebApplicationException("Lacks of mandatory id info", null, Response.Status.BAD_REQUEST);

        News newsFound = this.dataService.getNews(id);

        if (newsFound == null)
            throw new WebApplicationException("Resource with id = " + id + " could not be found", null,
                    Response.Status.NOT_FOUND);

        return newsFound;
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Authorize

    public News updateNews(News news, @PathParam("id") int id) {

        if (news == null || news.getTitle() == null || news.getTitle().isEmpty()) {
            throw new WebApplicationException("Lack of mandatory info", null, Response.Status.BAD_REQUEST);
        }
        news.setId(id);
        News updatedNews = this.dataService.updateNews(news);
        if (updatedNews == null) {
            throw new WebApplicationException("Resource not found", null, Response.Status.NOT_FOUND);
        }
        return updatedNews;
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Authorize
    public News deleteNews(@PathParam("id") int id) {
        if (id == 0) {
            throw new WebApplicationException("Lack of mandatory info", null, Response.Status.BAD_REQUEST);
        }
        News deletedNews = this.dataService.deleteNews(id);

        if (deletedNews == null) {
            throw new WebApplicationException("No resource found", null, Response.Status.NOT_FOUND);
        }
        return deletedNews;
    }

}
