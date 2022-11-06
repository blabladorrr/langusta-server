package pl.edu.pjwstk.langustaserver.model;

public class RecipeIngredient {
    private String name;
    private Integer quantity;
    private RecipeIngredientQuantityUnit recipeIngredientQuantityUnit;

    public RecipeIngredient(String name, Integer quantity, RecipeIngredientQuantityUnit recipeIngredientQuantityUnit) {
        this.name = name;
        this.quantity = quantity;
        this.recipeIngredientQuantityUnit = recipeIngredientQuantityUnit;
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
