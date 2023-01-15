package pl.edu.pjwstk.langustaserver.model;

import javax.persistence.*;

@Entity
public class RecipePreparationTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int value;
    @Enumerated(EnumType.STRING)
    private RecipePreparationTimeUnit unit;

    public RecipePreparationTime() {
    }

    public Integer getId() {
        return id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public RecipePreparationTimeUnit getUnit() {
        return unit;
    }

    public void setUnit(RecipePreparationTimeUnit unit) {
        this.unit = unit;
    }
}
