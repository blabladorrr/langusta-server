package pl.edu.pjwstk.langustaserver.model;

public enum RecipePreparationTimeUnit {
    HOUR("HOUR"),
    MINUTE("MINUTE");

    private final String recipePreparationTimeUnit;

    RecipePreparationTimeUnit(String recipePreparationTimeUnit) {
        this.recipePreparationTimeUnit = recipePreparationTimeUnit;
    }

    @Override
    public String toString() {
        return this.recipePreparationTimeUnit;
    }
}
