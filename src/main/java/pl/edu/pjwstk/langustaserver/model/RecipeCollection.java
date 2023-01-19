package pl.edu.pjwstk.langustaserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "collection")
public class RecipeCollection extends PublishableData {
    @Column(nullable = false)
    private String title;
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "recipe_collection",
            joinColumns = @JoinColumn(name = "collection_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    )
    private List<Recipe> recipes;

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

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
