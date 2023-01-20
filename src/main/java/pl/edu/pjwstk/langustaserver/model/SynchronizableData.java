package pl.edu.pjwstk.langustaserver.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.UUID;

@MappedSuperclass
public class SynchronizableData {
    @Id
    @Type(type = "uuid-char")
    protected UUID id;
    // TODO: Change date types in SynchronizableData back to LocalDateTime /ticket 37
    // When frontend will properly format date change it back to proper date type
    protected String createdAt;
    protected String updatedAt;
    /**
     * data created by the current user is "owned" by them
     * it can be modified or deleted
     * do not save in database
     */
    @Transient
    @JsonProperty
    protected Boolean isOwned;

    public SynchronizableData() {
        //createdAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId() {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setIsOwned(Boolean isOwned) {
        this.isOwned = isOwned;
    }
}
