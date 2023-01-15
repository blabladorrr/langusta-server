package pl.edu.pjwstk.langustaserver.component;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Component;
import pl.edu.pjwstk.langustaserver.model.Recipe;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class PublicRecipeProcessor {
    private static final String FIND_ALL_PUBLIC_RECIPES_QUERY = "SELECT * FROM recipes WHERE is_public = 1";
    private static final String FIND_ALL_PUBLIC_RECIPES_WITH_SEARCH_QUERY =
            "SELECT * FROM recipes WHERE is_public = 1 AND ?";

    public PublicRecipeProcessor() {
    }

    public List<Recipe> findAllPublicRecipes(Session session) {
        NativeQuery nativeQuery = session.createNativeQuery(FIND_ALL_PUBLIC_RECIPES_QUERY, Recipe.class);

        return executePublicRecipeQuery(nativeQuery);
    }

    public List<Recipe> findAllPublicRecipesWithFilters(Session session, Map<String, String> filters) {
        String sqlQueryWhereStatement = prepareWhereStatementFromFilters(filters);
        String sqlQuery = FIND_ALL_PUBLIC_RECIPES_WITH_SEARCH_QUERY.replace("?", sqlQueryWhereStatement);

        NativeQuery nativeQuery = session.createNativeQuery(sqlQuery, Recipe.class);

        return executePublicRecipeQuery(nativeQuery);
    }

    private String prepareWhereStatementFromFilters(Map<String, String> filters) {
        StringBuilder whereStatement = new StringBuilder();

        mapSearchKeyToColumnNames(filters);
        Set<String> filterKeys = filters.keySet();

        filterKeys.forEach(key -> whereStatement
                .append(addFilterKeyAndValueToWhereStatement(key, filters.get(key))));

        String strWhereStatement = remove5LastCharactersFromWhereStatement(whereStatement.toString());

        return strWhereStatement;
    }

    private void mapSearchKeyToColumnNames(Map<String, String> filters) {
        // Replacing "search" key name to appropriate column names from recipes table
        String searchValue = filters.remove("search");
        filters.put("title", searchValue);
    }

    private String addFilterKeyAndValueToWhereStatement(String key, String value) {
        // Deleting 5 last characters from where statement " AND " in order to prevent for SQL error
        return "LOWER(" + key + ") LIKE CONCAT('%', LOWER('" + value + "'), '%') AND ";
    }

    private String remove5LastCharactersFromWhereStatement(String whereStatement) {
        return whereStatement.substring(0, whereStatement.length() - 5);
    }

    private List<Recipe> executePublicRecipeQuery(NativeQuery query) {
        List<Recipe> recipeList = (List<Recipe>) query.getResultList();

        recipeList.forEach(recipe -> getAssociatedRecipeData(recipe));

        return recipeList;
    }

    private void getAssociatedRecipeData(Recipe recipe) {
        Hibernate.initialize(recipe.getIngredients());
        Hibernate.initialize(recipe.getSteps());
    }
}
