package pl.edu.pjwstk.langustaserver.component;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Component;
import pl.edu.pjwstk.langustaserver.model.RecipeCollection;

import java.util.List;
import java.util.Map;

@Component
public class PublicRecipeCollectionFetcher extends PublicDataProcessor {
    private static final String FIND_ALL_PUBLIC_RECIPE_COLLECTIONS_QUERY =
            "SELECT * FROM collections WHERE is_public = 1";
    private static final String FIND_ALL_PUBLIC_RECIPE_COLLECTIONS_WITH_FILTERS_QUERY =
            "SELECT * FROM collections WHERE is_public = 1 AND ?";

    public List<RecipeCollection> findAllPublicRecipes(Session session) {
        NativeQuery nativeQuery = createAllPublicDataNativeQuery(session, FIND_ALL_PUBLIC_RECIPE_COLLECTIONS_QUERY,
                RecipeCollection.class);

        return executePublicRecipeCollectionQuery(nativeQuery);
    }

    public List<RecipeCollection> findAllPublicRecipesWithFilters(Session session, Map<String, String> filters) {
        setFilters(filters);

        NativeQuery nativeQuery = createAllPublicDataWithFiltersNativeQuery(session,
                FIND_ALL_PUBLIC_RECIPE_COLLECTIONS_WITH_FILTERS_QUERY, RecipeCollection.class);

        return executePublicRecipeCollectionQuery(nativeQuery);
    }

    private List<RecipeCollection> executePublicRecipeCollectionQuery(NativeQuery query) {
        List<RecipeCollection> dataList = (List<RecipeCollection>) query.getResultList();

        dataList.forEach(recipeCollection -> getAssociatedRecipeCollectionData(recipeCollection));

        return dataList;
    }

    private void getAssociatedRecipeCollectionData(RecipeCollection recipeCollection) {
        Hibernate.initialize(recipeCollection.getRecipes());
    }
}
