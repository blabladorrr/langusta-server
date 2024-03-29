package pl.edu.pjwstk.langustaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pjwstk.langustaserver.model.RecipeCollection;

import java.util.UUID;

public interface RecipeCollectionRepository extends JpaRepository<RecipeCollection, UUID> {
    RecipeCollection save(RecipeCollection recipeCollection);
}
