package pl.edu.pjwstk.langustaserver.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
public class SynchronizableData {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(
            name = "uuid2",
            strategy = "uuid2"
    )
    @Type(type = "uuid-char")
    protected UUID id;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
    /**
     * data created by the current user is "owned" by them
     * it can be modified or deleted
     * do not save in database
     */
    @Transient
    protected Boolean isOwned;

    public SynchronizableData() {
        createdAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId() {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}