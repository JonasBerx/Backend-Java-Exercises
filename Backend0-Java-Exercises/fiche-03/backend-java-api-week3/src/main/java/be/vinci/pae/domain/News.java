package be.vinci.pae.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDateTime;

@JsonDeserialize(as= NewsImpl.class)
public interface News {
    int getId();
    void setId(int id);

    String getTitle();
    void setTitle(String title);

    String getDescription();
    void setDescription(String description);

    String getContent();
    void setContent(String content);

    // TODO Change au Auteur Classe?
    String getAuthor();
    void setAuthor(String author);

    // TODO Enum?
    String getStatus();
    void setStatus(String status);

    LocalDateTime getCreationDate();

    LocalDateTime getDeletionDate();
    void setDeletionDate(LocalDateTime deletionDate);

    // TODO Page classe?
    String getPage();
    void setPage(String page);

    String toString();


}
