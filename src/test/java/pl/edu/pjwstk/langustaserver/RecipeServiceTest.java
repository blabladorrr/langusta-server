package pl.edu.pjwstk.langustaserver;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.edu.pjwstk.langustaserver.model.Recipe;
import pl.edu.pjwstk.langustaserver.repository.RecipeRepository;
import pl.edu.pjwstk.langustaserver.service.RecipeService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        // Mocking service constructor behavior because hibernateFactory is null
        when(hibernateFactory.unwrap(Mockito.any())).thenReturn(hibernateFactory);
        // Creating new object because of the above issue, @InjectMocks did not work in this case
        recipeService = new RecipeService(recipeRepository, hibernateFactory);
    }

    @AfterEach
    public void cleanup() {
        Mockito.reset(recipeRepository, hibernateFactory, session);
    }

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private static SessionFactory hibernateFactory;

    @Mock
    private Session session;

    private RecipeService recipeService;

    @Test
    void givenProperIdList_whenGetRecipesById_thenShouldFindById() {
        // GIVEN
        List<String> idList = List.of("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454", "5bd8faa3-e052-44d1-822b-4b3c2b477f8e");
        // WHEN
        when(hibernateFactory.openSession()).thenReturn(session);
        when(session.find(Mockito.any(), Mockito.any())).thenReturn(new Recipe());

        List<Recipe> recipes = recipeService.getRecipesById(idList);
        // THEN
        assertThat(recipes).isNotEmpty();
    }

    @Test
    void givenInvalidIdList_whenGetRecipesById_thenShouldFindById() {
        // GIVEN
        List<String> idList = List.of("invalid_id_1", "invalid_id_2");
        // WHEN
        when(hibernateFactory.openSession()).thenReturn(session);
        when(session.find(Mockito.any(), Mockito.any())).thenReturn(null);

        List<Recipe> recipes = recipeService.getRecipesById(idList);
        // THEN
        assertThat(recipes).containsOnlyNulls();
    }

    @Test
    void givenRecipeList_whenSaveRecipes_thenSaveAllMethodShouldBeCalledWithGivenList() {
        // GIVEN
        List<Recipe> recipesToSave = List.of(new Recipe(), new Recipe());
        // WHEN
        recipeService.saveRecipes(recipesToSave);
        // THEN
        verify(recipeRepository, times(1)).saveAll(recipesToSave);
    }

    @Test
    void givenListOf2ExistingRecipesIds_whenDeleteRecipes_thenDeleteByIdMethodShouldBeCalled2Times() {
        // GIVEN
        List<String> idList = List.of("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454", "5bd8faa3-e052-44d1-822b-4b3c2b477f8e");
        // WHEN
        when(recipeRepository.existsById(Mockito.anyString())).thenReturn(true);

        recipeService.deleteRecipes(idList);
        // THEN
        verify(recipeRepository, times(2)).deleteById(Mockito.anyString());
    }

    @Test
    void givenListOfRecipeIds_whenDeleteRecipes_thenDeleteMethodShouldBeCalledOnlyFor1ExistingRecipe() {
        // GIVEN
        List<String> idList = List.of(
                "f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454",
                "5bd8faa3-e052-44d1-822b-4b3c2b477f8e",
                "63a04e88-b2bd-46ab-88d8-201f9dbde54f"
        );
        // WHEN
        when(recipeRepository.existsById("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454")).thenReturn(false);
        // This one exists
        when(recipeRepository.existsById("5bd8faa3-e052-44d1-822b-4b3c2b477f8e")).thenReturn(true);
        when(recipeRepository.existsById("63a04e88-b2bd-46ab-88d8-201f9dbde54f")).thenReturn(false);

        recipeService.deleteRecipes(idList);
        // THEN
        verify(recipeRepository, times(1)).deleteById("5bd8faa3-e052-44d1-822b-4b3c2b477f8e");
    }
}
