package pl.edu.pjwstk.langustaserver.model;

import java.util.UUID;

public class SynchronizableData {
    protected UUID id;

    public SynchronizableData() {
        id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }
}
