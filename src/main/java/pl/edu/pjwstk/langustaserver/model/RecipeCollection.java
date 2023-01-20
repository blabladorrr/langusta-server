package pl.edu.pjwstk.langustaserver.model;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "collections")
public class RecipeCollection extends PublishableData {
    @Column(nullable = false)
    private String title;
    // TODO: Change to proper relation
    // TODO: Change to field name to "recipe"
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "collection_recipes", joinColumns = @JoinColumn(name = "collection_id"))
    @Column(name = "recipe_id")
    private List<String> recipeIds;

    public RecipeCollection(String author, String description, String title) {
        super(author, description);
        this.title = title;
    }

    public RecipeCollection() {
    }

    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getRecipeIds() {
        return recipeIds;
    }

    public void setRecipeIds(List<String> recipeIds) {
        this.recipeIds = recipeIds;
    }
}
