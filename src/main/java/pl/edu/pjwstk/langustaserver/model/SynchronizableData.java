package pl.edu.pjwstk.langustaserver.model;

import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.UUID;

public class SynchronizableData {
    protected UUID id;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;

    public SynchronizableData() {
        id = UUID.randomUUID();
        createdAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
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
