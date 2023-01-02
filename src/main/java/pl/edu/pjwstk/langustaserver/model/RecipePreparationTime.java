package pl.edu.pjwstk.langustaserver.model;

import javax.persistence.*;

@Entity
public class RecipePreparationTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int value;
    @Enumerated(EnumType.STRING)
    private RecipePreparationTimeUnit recipePreparationTimeUnit;

    public RecipePreparationTime() {}

    public long getId() {
        return id;
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
