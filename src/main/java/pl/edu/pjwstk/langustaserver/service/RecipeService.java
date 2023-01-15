package pl.edu.pjwstk.langustaserver.service;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.langustaserver.component.PublicRecipeProcessor;
import pl.edu.pjwstk.langustaserver.model.Recipe;
import pl.edu.pjwstk.langustaserver.repository.RecipeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final SessionFactory hibernateFactory;
    private final PublicRecipeProcessor publicRecipeProcessor;

    public RecipeService(
            RecipeRepository recipeRepository,
            SessionFactory hibernateFactory,
            PublicRecipeProcessor publicRecipeProcessor
    ) {
        this.recipeRepository = recipeRepository;

        if (hibernateFactory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("Factory is not a hibernate factory");
        }
        this.hibernateFactory = hibernateFactory.unwrap(SessionFactory.class);

        this.publicRecipeProcessor = publicRecipeProcessor;
    }

    public List<Recipe> getRecipesById(List<String> idList) {
        List<Recipe> recipeList = new ArrayList<>();

        idList.forEach(id -> recipeList.add(fetchRecipeById(id)));

        return recipeList;
    }

    private Recipe fetchRecipeById(String id) {
        Session session = hibernateFactory.openSession();

        Recipe foundRecipe = (Recipe) session.find(Recipe.class, id);

        if (foundRecipe != null) {
            Hibernate.initialize(foundRecipe.getIngredients());
            Hibernate.initialize(foundRecipe.getSteps());
        }

        session.close();

        return foundRecipe;
    }

    public List<Recipe> saveRecipes(List<Recipe> recipesToSave) {
        recipeRepository.saveAll(recipesToSave);

        return recipesToSave;
    }

    public List<String> deleteRecipes(List<String> idList) {
        List<String> deletedRecipeIds = new ArrayList<>();

        idList.forEach(id -> deleteRecipeIfExists(id, deletedRecipeIds));

        return deletedRecipeIds;
    }

    private void deleteRecipeIfExists(String id, List<String> deletedRecipeIds) {
        if (recipeRepository.existsById(id)) {
            recipeRepository.deleteById(id);

            deletedRecipeIds.add(id);
        }
    }

    public List<Recipe> getPublicRecipes(Map<String, String> filters) {
        List<Recipe> foundRecipes = findPublicRecipesAccordingToFilters(filters);

        return foundRecipes;
    }

    private List<Recipe> findPublicRecipesAccordingToFilters(Map<String, String> filters) {
        List<Recipe> foundRecipes;
        Session session = hibernateFactory.openSession();

        if (filters.isEmpty()) {
            foundRecipes = publicRecipeProcessor.findAllPublicRecipes(session);
        }
        else {
            foundRecipes = publicRecipeProcessor.findAllPublicRecipesWithFilters(session, filters);
        }

        session.close();

        return foundRecipes;
    }
}
