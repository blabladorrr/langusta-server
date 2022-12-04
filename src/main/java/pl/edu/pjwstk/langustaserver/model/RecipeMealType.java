package pl.edu.pjwstk.langustaserver.model;

public enum RecipeMealType {
    BREAKFAST("BREAKFAST"),
    LUNCH("LUNCH"),
    DINNER("DINNER"),
    APPETIZER("APPETIZER"),
    DESSERT("DESSERT"),
    SNACK("SNACK");

    private final String recipeMealType;

    RecipeMealType(final String recipeMealType) {
        this.recipeMealType = recipeMealType;
    }

    @Override
    public String toString() {
        return this.recipeMealType;
    }
}
