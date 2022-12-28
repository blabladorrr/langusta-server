package pl.edu.pjwstk.langustaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pjwstk.langustaserver.model.Recipe;

import java.util.UUID;

public interface RecipeRepository extends JpaRepository<Recipe, UUID> {
    Recipe save(Recipe recipe);
}
