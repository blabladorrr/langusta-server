package pl.edu.pjwstk.langustaserver.model;

import java.util.List;
import java.util.UUID;

public class Recipe {
    protected UUID id;
    protected UUID recipeAuthorId;



    public Recipe() {
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
