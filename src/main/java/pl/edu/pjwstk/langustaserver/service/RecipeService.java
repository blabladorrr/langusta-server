package pl.edu.pjwstk.langustaserver.service;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.langustaserver.component.PublicRecipeProcessor;
import pl.edu.pjwstk.langustaserver.exception.UserNotFoundException;
import pl.edu.pjwstk.langustaserver.model.Recipe;
import pl.edu.pjwstk.langustaserver.model.User;
import pl.edu.pjwstk.langustaserver.repository.RecipeRepository;
import pl.edu.pjwstk.langustaserver.repository.UserRepository;

import java.util.*;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final SessionFactory hibernateFactory;
    private final PublicRecipeProcessor publicRecipeProcessor;

    public RecipeService(
            RecipeRepository recipeRepository,
            UserRepository userRepository,
            SessionFactory hibernateFactory,
            PublicRecipeProcessor publicRecipeProcessor
    ) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;

        if (hibernateFactory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("Factory is not a hibernate factory");
        }
        this.hibernateFactory = hibernateFactory.unwrap(SessionFactory.class);

        this.publicRecipeProcessor = publicRecipeProcessor;
    }

    public List<Recipe> getUserRecipes() {
        List<Recipe> recipeList;

        recipeList = fetchUserRecipes(getCurrentUserId());

        return recipeList;
    }

    private List<Recipe> fetchUserRecipes(UUID id) {
        Session session = hibernateFactory.openSession();

        // TODO: change it to CriteriaQuery
        String query = "SELECT * FROM recipes where author = :userId";
        NativeQuery<Recipe> nativeQuery = session.createNativeQuery(query, Recipe.class);
        nativeQuery.setParameter("userId", id.toString());

        List<Recipe> recipeList = (List<Recipe>) nativeQuery.getResultList();

        recipeList.forEach(recipe -> getUserRecipeAssociatedDataAndSetIsOwnedFlag(recipe));

        session.close();

        return recipeList;
    }

    private void getUserRecipeAssociatedDataAndSetIsOwnedFlag(Recipe recipe) {
        recipe.setIsOwned(true);

        Hibernate.initialize(recipe.getIngredients());
        Hibernate.initialize(recipe.getSteps());
    }

    public List<Recipe> getRecipesById(List<String> idList) {
        List<Recipe> recipeList = new ArrayList<>();

        idList.forEach(id -> recipeList.add(fetchRecipeById(id)));

        return recipeList;
    }

    private Recipe fetchRecipeById(String id) {
        Session session = hibernateFactory.openSession();

        Recipe foundRecipe = (Recipe) session.find(Recipe.class, UUID.fromString(id));

        if (foundRecipe != null) {
            Hibernate.initialize(foundRecipe.getIngredients());
            Hibernate.initialize(foundRecipe.getSteps());
        }

        session.close();

        return foundRecipe;
    }

    public List<Recipe> saveRecipes(List<Recipe> recipesToSave) {
        recipesToSave.forEach(recipe -> setAuthorForRecipesIfEmpty(recipe));

        recipeRepository.saveAll(recipesToSave);

        return recipesToSave;
    }

    private void setAuthorForRecipesIfEmpty(Recipe recipe) {
        if (recipe.getAuthor() == null) {
            recipe.setAuthor(getCurrentUserId().toString());
        }
    }

    public List<String> deleteRecipes(List<String> idList) {
        List<String> deletedRecipeIds = new ArrayList<>();

        idList.forEach(id -> deleteRecipeIfExists(UUID.fromString(id), deletedRecipeIds));

        return deletedRecipeIds;
    }

    private void deleteRecipeIfExists(UUID id, List<String> deletedRecipeIds) {
        if (recipeRepository.existsById(id)) {
            recipeRepository.deleteById(id);

            deletedRecipeIds.add(id.toString());
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

    private UUID getCurrentUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> optionalUser = userRepository.findByUsername(username);

        return getPresentUser(optionalUser, username).getId();
    }

    private User getPresentUser(Optional<User> optionalUser, String username) {
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        else {
            throw new UserNotFoundException(username);
        }
    }
}
