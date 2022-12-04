package pl.edu.pjwstk.langustaserver.model;

public class RecipePreparationTime {
    private int value;
    private RecipePreparationTimeUnit recipePreparationTimeUnit;

    public RecipePreparationTime(int value, RecipePreparationTimeUnit recipePreparationTimeUnit) {
        this.value = value;
        this.recipePreparationTimeUnit = recipePreparationTimeUnit;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public RecipePreparationTimeUnit getRecipePreparationTimeUnit() {
        return recipePreparationTimeUnit;
    }

    public void setRecipePreparationTimeUnit(RecipePreparationTimeUnit recipePreparationTimeUnit) {
        this.recipePreparationTimeUnit = recipePreparationTimeUnit;
    }
}
