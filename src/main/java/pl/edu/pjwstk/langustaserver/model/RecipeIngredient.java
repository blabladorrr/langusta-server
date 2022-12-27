package pl.edu.pjwstk.langustaserver.model;


import javax.persistence.*;

@Entity
public class RecipeIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private Integer quantity;
    @Enumerated(EnumType.STRING)
    private RecipeIngredientQuantityUnit recipeIngredientQuantityUnit;

    public long getId() {
        return id;
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

    public RecipeIngredientQuantityUnit getRecipeIngredientQuantityUnit() {
        return recipeIngredientQuantityUnit;
    }

    public void setRecipeIngredientQuantityUnit(RecipeIngredientQuantityUnit recipeIngredientQuantityUnit) {
        this.recipeIngredientQuantityUnit = recipeIngredientQuantityUnit;
    }
}
