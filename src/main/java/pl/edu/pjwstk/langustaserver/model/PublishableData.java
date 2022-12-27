package pl.edu.pjwstk.langustaserver.model;

public class PublishableData extends SynchronizableData {
    protected Boolean isPublic;
    protected String author;
    protected String description;

    public PublishableData(String author, String description) {
        isPublic = false;
        this.author = author;
        this.description = description;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean isPublic) {
        isPublic = isPublic;
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
