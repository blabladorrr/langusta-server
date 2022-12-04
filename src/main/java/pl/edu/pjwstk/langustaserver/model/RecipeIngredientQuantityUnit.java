package pl.edu.pjwstk.langustaserver.model;

public enum RecipeIngredientQuantityUnit {
    /**
     * gram
     */
    G("G"),
    /**
     * kilogram
     */
    KG("KG"),
    /**
     * liter
     */
    L("L"),
    /**
     * milliliter
     */
    ML("ML"),
    /**
     * 1 glass -> 200 ml
     */
    GLASS("GLASS"),
    /**
     * 1 spoon -> 15 g
     * 1 spoon -> 15 ml
     */
    SPOON("SPOON"),
    /**
     * 1 teaspoon -> 5 g
     * 1 teaspoon -> 5 ml
     */
    TEASPOON("TEASPOON");

    private final String recipeIngredientQuantityUnit;

    RecipeIngredientQuantityUnit(String recipeIngredientQuantityUnit) {
        this.recipeIngredientQuantityUnit = recipeIngredientQuantityUnit;
    }

    @Override
    public String toString() {
        return this.recipeIngredientQuantityUnit;
    }
}
