package pl.edu.pjwstk.langustaserver.model;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class PublishableData extends SynchronizableData {
    protected Boolean isPublic;
    protected String author;
    protected String description;

    public PublishableData(String author, String description) {
        this.isPublic = false;
        this.author = author;
        this.description = description;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
