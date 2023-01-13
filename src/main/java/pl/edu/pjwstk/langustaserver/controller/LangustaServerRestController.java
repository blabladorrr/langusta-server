package pl.edu.pjwstk.langustaserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjwstk.langustaserver.model.Recipe;
import pl.edu.pjwstk.langustaserver.service.RecipeService;

import java.util.List;

@RestController
@RequestMapping("/recipes")
public class LangustaServerRestController {
    private final RecipeService recipeService;

    public LangustaServerRestController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/get")
    public ResponseEntity<List<Recipe>> getRecipesById(@RequestBody List<String> idList) {
        return ResponseEntity.ok(recipeService.getRecipesById(idList));
    }

    @PutMapping("/save")
    public ResponseEntity<List<Recipe>> saveRecipes(@RequestBody List<Recipe> recipesToSave) {
        return ResponseEntity.ok(recipeService.saveRecipes(recipesToSave));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteRecipes(@RequestBody List<String> idList) {
        recipeService.deleteRecipes(idList);

        return ResponseEntity.noContent().build();
    }
}
