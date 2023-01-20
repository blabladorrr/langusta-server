package pl.edu.pjwstk.langustaserver.component;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Component;
import pl.edu.pjwstk.langustaserver.model.Recipe;

import java.util.List;
import java.util.Map;

@Component
public class PublicRecipeFetcher extends PublicDataProcessor {
    private static final String FIND_ALL_PUBLIC_RECIPES_QUERY = "SELECT * FROM recipes WHERE is_public = 1";
    private static final String FIND_ALL_PUBLIC_RECIPES_WITH_FILTERS_QUERY =
            "SELECT * FROM recipes WHERE is_public = 1 AND ?";

    public List<Recipe> findAllPublicRecipes(Session session) {
        NativeQuery nativeQuery = createAllPublicDataNativeQuery(session, FIND_ALL_PUBLIC_RECIPES_QUERY, Recipe.class);

        return executePublicRecipeQuery(nativeQuery);
    }

    public List<Recipe> findAllPublicRecipesWithFilters(Session session, Map<String, String> filters) {
        setFilters(filters);

        NativeQuery nativeQuery = createAllPublicDataWithFiltersNativeQuery(session,
                FIND_ALL_PUBLIC_RECIPES_WITH_FILTERS_QUERY, Recipe.class);

        return executePublicRecipeQuery(nativeQuery);
    }

    private List<Recipe> executePublicRecipeQuery(NativeQuery query) {
        List<Recipe> dataList = (List<Recipe>) query.getResultList();

        dataList.forEach(recipe -> getAssociatedRecipeData(recipe));

        return dataList;
    }

    private void getAssociatedRecipeData(Recipe recipe) {
        Hibernate.initialize(recipe.getIngredients());
        Hibernate.initialize(recipe.getSteps());
    }
}
