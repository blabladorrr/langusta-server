package pl.edu.pjwstk.langustaserver.model;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class PublishableData extends SynchronizableData {
    protected Boolean isPublic;
    protected String author;
    protected String description;

    public PublishableData() {
    }

    public PublishableData(String author, String description) {
        this.author = author;
        this.description = description;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
