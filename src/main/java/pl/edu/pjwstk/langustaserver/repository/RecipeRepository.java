package pl.edu.pjwstk.langustaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pjwstk.langustaserver.model.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, String> {
    Recipe save(Recipe recipe);
}
