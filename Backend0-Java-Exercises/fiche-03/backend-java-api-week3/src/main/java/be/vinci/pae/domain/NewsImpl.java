package be.vinci.pae.domain;

import java.time.LocalDateTime;

public class NewsImpl implements News {

    private int id;
    private String title;
    private String description;
    private String content;
    private String author;
    private StatutDePublication status;
    private LocalDateTime creationDate;
    private LocalDateTime deletionDate;
    private String page;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String getStatus() {
        return status.toString();
    }

    @Override
    public void setStatus(String status) {
        this.status = StatutDePublication.valueOf(status.toUpperCase());
    }

    @Override
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    @Override
    public LocalDateTime getDeletionDate() {
        return deletionDate;
    }

    @Override
    public void setDeletionDate(LocalDateTime deletionDate) {
        this.deletionDate = deletionDate;
    }

    @Override
    public String getPage() {
        return page;
    }

    @Override
    public void setPage(String page) {
        this.page = page;
    }
}
