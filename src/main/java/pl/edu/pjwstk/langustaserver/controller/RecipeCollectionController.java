package pl.edu.pjwstk.langustaserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjwstk.langustaserver.model.RecipeCollection;
import pl.edu.pjwstk.langustaserver.service.RecipeCollectionService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/collections")
public class RecipeCollectionController {
    private RecipeCollectionService recipeCollectionService;

    public RecipeCollectionController(RecipeCollectionService recipeCollectionService) {
        this.recipeCollectionService = recipeCollectionService;
    }

    @GetMapping("/get/user")
    public ResponseEntity<List<RecipeCollection>> getUserRecipeCollections() {
        return ResponseEntity.ok(recipeCollectionService.getUserCollections());
    }

    @PostMapping("/get/by-id")
    public ResponseEntity<List<RecipeCollection>> getRecipeCollectionsById(@RequestBody List<String> idList) {
        return ResponseEntity.ok(recipeCollectionService.getRecipeCollectionsById(idList));
    }

    @PostMapping("/get/public")
    public ResponseEntity<List<RecipeCollection>> getPublicRecipeCollections(@RequestBody Map<String, String> filters) {
        return ResponseEntity.ok(recipeCollectionService.getPublicRecipeCollections(filters));
    }

    @PutMapping("/save")
    public ResponseEntity<List<RecipeCollection>> saveRecipeCollections(
            @RequestBody List<RecipeCollection> collectionsToSave) {
        return ResponseEntity.ok(recipeCollectionService.saveCollections(collectionsToSave));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<List<String>> deleteRecipeCollections(@RequestBody List<String> idList) {
        return ResponseEntity.ok(recipeCollectionService.deleteRecipeCollectionsById(idList));
    }
}
