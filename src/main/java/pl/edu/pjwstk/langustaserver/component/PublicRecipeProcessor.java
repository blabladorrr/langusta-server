package pl.edu.pjwstk.langustaserver.component;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.ParameterMetadata;
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

    private Map<String, String> filters;

    public PublicRecipeProcessor() {
    }

    private void setFilters(Map<String, String> filters) {
        this.filters = filters;
    }

    public List<Recipe> findAllPublicRecipes(Session session) {
        NativeQuery nativeQuery = session.createNativeQuery(FIND_ALL_PUBLIC_RECIPES_QUERY, Recipe.class);

        return executePublicRecipeQuery(nativeQuery);
    }

    public List<Recipe> findAllPublicRecipesWithFilters(Session session, Map<String, String> filters) {
        setFilters(filters);

        String sqlQuery = FIND_ALL_PUBLIC_RECIPES_QUERY;

        String sqlQueryWhereStatement = prepareWhereStatementFromFilters();
        if (sqlQueryWhereStatement.length() > 0) {
            sqlQuery = FIND_ALL_PUBLIC_RECIPES_WITH_SEARCH_QUERY.replace("?", sqlQueryWhereStatement);
        }

        NativeQuery nativeQuery = session.createNativeQuery(sqlQuery, Recipe.class);

        // Setting parameters for values to prevent from SQL Injection
        setAllFiltersParametersForNativeQuery(nativeQuery);

        return executePublicRecipeQuery(nativeQuery);
    }

    private String prepareWhereStatementFromFilters() {
        StringBuilder whereStatement = new StringBuilder();

        // Mapping known keys to prevent from SQL Injection
        Set<String> filterKeys = filters.keySet();

        for (String key : filterKeys) {
            addFilterValueToWhereStatementToBeParametrizedIfKeyIsSafe(whereStatement, key);
        }

        String strWhereStatement = whereStatement.toString();
        if (whereStatement.toString().length() > 0) {
            strWhereStatement = remove5LastCharactersFromWhereStatement(strWhereStatement);
        }

        return strWhereStatement;
    }

    private void addFilterValueToWhereStatementToBeParametrizedIfKeyIsSafe(StringBuilder whereStatement, String key) {
        // Appending whereStatement only by known and safe keys
        switch (key) {
            case "search":
                String searchValue = filters.remove(key);
                // Title
                filters.put("title", searchValue);
                whereStatement.append(addFilterKeyAndValueToWhereStatementToBeParametrized("title"));

                break;
        }
    }

    private String addFilterKeyAndValueToWhereStatementToBeParametrized(String key) {
        return "LOWER(" + key + ") LIKE CONCAT('%', LOWER(:" + key + "), '%') AND ";
    }

    private String remove5LastCharactersFromWhereStatement(String whereStatement) {
        // Deleting 5 last characters from where statement " AND " in order to prevent for SQL error
        return whereStatement.substring(0, whereStatement.length() - 5);
    }

    private void setAllFiltersParametersForNativeQuery(NativeQuery query) {
        ParameterMetadata parameterMetadata = query.getParameterMetadata();
        Set<String> namedParameters = parameterMetadata.getNamedParameterNames();

        for (String namedParameter : namedParameters) {
            String filterValue = filters.get(namedParameter);

            query.setParameter(namedParameter, filterValue);
        }
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
