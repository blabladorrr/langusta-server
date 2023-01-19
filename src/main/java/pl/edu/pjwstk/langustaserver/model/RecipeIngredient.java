package pl.edu.pjwstk.langustaserver.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "recipe_ingredients")
public class RecipeIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe associatedRecipe;
    private String name;
    private Integer quantity;
    @Enumerated(EnumType.STRING)
    private RecipeIngredientQuantityUnit quantityUnit;

    public RecipeIngredient() {}

    public Integer getId() {
        return id;
    }

    public Recipe getAssociatedRecipe() {
        return associatedRecipe;
    }

    public void setAssociatedRecipe(Recipe associatedRecipe) {
        this.associatedRecipe = associatedRecipe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public RecipeIngredientQuantityUnit getQuantityUnit() {
        return quantityUnit;
    }

    public void setQuantityUnit(RecipeIngredientQuantityUnit quantityUnit) {
        this.quantityUnit = quantityUnit;
    }
}
