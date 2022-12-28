package pl.edu.pjwstk.langustaserver.service;

import org.springframework.stereotype.Service;
import pl.edu.pjwstk.langustaserver.repository.RecipeRepository;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }
}
