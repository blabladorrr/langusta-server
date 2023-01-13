package pl.edu.pjwstk.langustaserver.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class Recipe extends PublishableData {
    private String title;
    private String externalSourceUrl;
    private Integer likeCount;
    private Integer rating;
    private Integer calorieCount;
    @Enumerated(EnumType.STRING)
    private RecipeMealType mealType;
    @JsonManagedReference
    @OneToMany(mappedBy = "associatedRecipe", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RecipeIngredient> ingredients;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private RecipePreparationTime preparationTime;
    @JsonManagedReference
    @OneToMany(mappedBy = "associatedRecipe", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RecipeStep> steps;

    public Recipe() {
    }

    public Recipe(String title, String description, String author,
                  String externalSourceUrl, Integer likeCount, Integer rating,
                  Integer calorieCount, RecipeMealType mealType, List<RecipeIngredient> ingredients,
                  RecipePreparationTime preparationTime, List<RecipeStep> steps) {
        super(author, description);
        this.title = title;
        this.externalSourceUrl = externalSourceUrl;
        this.likeCount = likeCount;
        this.rating = rating;
        this.calorieCount = calorieCount;
        this.mealType = mealType;
        this.ingredients = ingredients;
        this.preparationTime = preparationTime;
        this.steps = steps;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExternalSourceUrl() {
        return externalSourceUrl;
    }

    public void setExternalSourceUrl(String externalSourceUrl) {
        this.externalSourceUrl = externalSourceUrl;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getCalorieCount() {
        return calorieCount;
    }

    public void setCalorieCount(Integer calorieCount) {
        this.calorieCount = calorieCount;
    }

    public RecipeMealType getMealType() {
        return mealType;
    }

    public void setMealType(RecipeMealType mealType) {
        this.mealType = mealType;
    }

    public List<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<RecipeIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    public RecipePreparationTime getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(RecipePreparationTime preparationTime) {
        this.preparationTime = preparationTime;
    }

    public List<RecipeStep> getSteps() {
        return steps;
    }

    public void setSteps(List<RecipeStep> steps) {
        this.steps = steps;
    }
}
