package be.vinci.pae.domain;

import jakarta.ws.rs.BadRequestException;

public class Page {
    private int id;
    private String title;
    private String uri;
    private String content;
    private int auteurId;
    private String pubStatus;


    // Getters
    public int getId() {
        return this.id;
    }
    public String getTitle() {
        return this.title;
    }
    public String getUri() {
        return this.uri;
    }
    public String getContent() {
        return this.content;
    }
    public int getAuteur() {
        return this.auteurId;
    }
    // Why not boolean?
    public String getPubStatus() {
        return this.pubStatus;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setAuteur(int auteurId) {
        this.auteurId = auteurId;
    }
    public void setPubStatus(String status) {
        if (status.equals("published") || status.equals("hidden")) {
            this.pubStatus = status;
        } else {
            throw new BadRequestException("Invalid status given");
        }
    }

    public String toString() {
        return "PageImpl [id=" + id + ", title=" + title + ", content="
                + content + ", auteur=" + auteurId + ", uri="
                + uri + "]";
    }



    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Page other = (Page) obj;
        if (id != other.getId())
            return false;
        return true;
    }

}
