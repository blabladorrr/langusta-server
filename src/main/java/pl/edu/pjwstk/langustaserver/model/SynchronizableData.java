package pl.edu.pjwstk.langustaserver.model;

import java.util.UUID;

public class SynchronizableData {
    protected UUID id;
    protected UUID recipeAuthorId;

    public SynchronizableData() {
        id = UUID.randomUUID();
        recipeAuthorId = UUID.randomUUID();
    }

    public UUID getRecipeId() {
        return id;
    }

    public UUID getRecipeAuthorId() {
        return recipeAuthorId;
    }
}
