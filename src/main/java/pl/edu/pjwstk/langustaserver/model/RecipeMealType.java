package pl.edu.pjwstk.langustaserver.model;

public enum RecipeMealType {
    BREAKFAST("BREAKFAST"),
    LUNCH("LUNCH"),
    DINNER("DINNER"),
    APPETIZER("APPETIZER"),
    DESSERT("DESSERT"),
    SNACK("SNACK");

    private final String recipMealType;

    RecipeMealType(final String recipMealType) {
        this.recipMealType = recipMealType;
    }

    @Override
    public String toString() {
        return this.recipMealType;
    }
}
