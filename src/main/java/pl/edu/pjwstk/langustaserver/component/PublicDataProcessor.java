package pl.edu.pjwstk.langustaserver.component;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.ParameterMetadata;

import java.util.Map;
import java.util.Set;

public abstract class PublicDataProcessor {
    private Map<String, String> filters;

    protected NativeQuery createAllPublicDataNativeQuery(Session session, String sqlQuery, Class<?> classType) {
        NativeQuery nativeQuery = session.createNativeQuery(sqlQuery, classType);

        return nativeQuery;
    }

    protected NativeQuery createAllPublicDataWithFiltersNativeQuery(Session session, String sqlQuery,
                                                                    Class<?> classType) {
        String sqlQueryWhereStatement = prepareWhereStatementFromFilters();

        sqlQuery = sqlQuery.replace("?", sqlQueryWhereStatement);

        NativeQuery nativeQuery = session.createNativeQuery(sqlQuery, classType);

        // Setting parameters for values to prevent from SQL Injection
        setAllFiltersParametersForNativeQuery(nativeQuery);

        return nativeQuery;
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
            case "search" -> mapSearchFilterKeyToColumnNames(whereStatement);
        }
    }

    private void mapSearchFilterKeyToColumnNames(StringBuilder whereStatement) {
        String searchValue = filters.remove("search");
        // Title
        filters.put("title", searchValue);
        whereStatement.append(addFilterKeyAndValueToWhereStatementToBeParametrized("title"));
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

    protected void setFilters(Map<String, String> filters) {
        this.filters = filters;
    }
}
