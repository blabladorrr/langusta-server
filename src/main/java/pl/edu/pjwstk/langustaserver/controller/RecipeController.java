package pl.edu.pjwstk.langustaserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjwstk.langustaserver.model.Recipe;
import pl.edu.pjwstk.langustaserver.service.RecipeService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/get/user")
    public ResponseEntity<List<Recipe>> getUserRecipes() {
        return ResponseEntity.ok(recipeService.getUserRecipes());
    }

    @PostMapping("/get/by-id")
    public ResponseEntity<List<Recipe>> getRecipesById(@RequestBody List<String> idList) {
        return ResponseEntity.ok(recipeService.getRecipesById(idList));
    }

    @PostMapping("/get/public")
    public ResponseEntity<List<Recipe>> getPublicRecipes(@RequestBody Map<String, String> filters) {
        return ResponseEntity.ok(recipeService.getPublicRecipes(filters));
    }

    @PutMapping("/save")
    public ResponseEntity<List<Recipe>> saveRecipes(@RequestBody List<Recipe> recipesToSave) {
        return ResponseEntity.ok(recipeService.saveRecipes(recipesToSave));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<List<String>> deleteRecipes(@RequestBody List<String> idList) {
        return ResponseEntity.ok(recipeService.deleteRecipes(idList));
    }
}
