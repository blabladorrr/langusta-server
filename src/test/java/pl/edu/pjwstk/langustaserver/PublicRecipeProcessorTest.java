package pl.edu.pjwstk.langustaserver;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.edu.pjwstk.langustaserver.component.PublicRecipeProcessor;
import pl.edu.pjwstk.langustaserver.model.Recipe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PublicRecipeProcessorTest {
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        // Mocking common called method behaviors
        when(session.createNativeQuery(any(), any(Class.class))).thenReturn(nativeQuery);
        when(nativeQuery.getResultList()).thenReturn(List.of(recipe));
    }

    @AfterEach
    public void cleanup() {
        Mockito.reset(session, nativeQuery);
    }

    private PublicRecipeProcessor publicRecipeProcessor = new PublicRecipeProcessor();

    @Mock
    private Session session;

    @Mock
    private NativeQuery nativeQuery;

    private Recipe recipe = new Recipe();

    @Test
    void givenEmptyFilters_whenFindAllPublicRecipes_thenRecipeListShouldBeReturned() {
        // GIVEN
        Map<String, String> filters = new HashMap<>();
        // WHEN
        List<Recipe> recipeList = publicRecipeProcessor.findAllPublicRecipes(session);
        // THEN
        assertThat(recipeList).isNotEmpty().hasSize(1).contains(recipe);
    }

    @Test
    void givenFilledFilters_whenGetPublicRecipes_thenPublicRecipesWithFiltersShouldBeCalled() {
        // GIVEN
        Map<String, String> filters = new HashMap<>();
        filters.put("search", "Burrito");
        // WHEN
        List<Recipe> recipeList = publicRecipeProcessor.findAllPublicRecipesWithFilters(session, filters);
        // THEN
        assertThat(recipeList).isNotEmpty().hasSize(1).contains(recipe);
    }

    @Test
    void whenFindAllPublicRecipes_thenQueryWithoutFiltersShouldBePerformed() {
        String sqlQuery = "SELECT * FROM recipes WHERE is_public = 1";
        // WHEN
        publicRecipeProcessor.findAllPublicRecipes(session);
        // THEN
        verify(session, times(1)).createNativeQuery(sqlQuery, Recipe.class);
    }

    @Test
    void givenFilters_whenFindAllPublicRecipesWithFilters_thenQueryWithoutFiltersShouldBePerformed() {
        String sqlQuery =
                "SELECT * FROM recipes WHERE is_public = 1 AND LOWER(title) LIKE CONCAT('%', LOWER('Burrito'), '%')";
        // GIVEN
        Map<String, String> filters = new HashMap<>();
        filters.put("search", "Burrito");
        // WHEN
        publicRecipeProcessor.findAllPublicRecipesWithFilters(session, filters);
        // THEN
        verify(session, times(1)).createNativeQuery(sqlQuery, Recipe.class);
    }
}
