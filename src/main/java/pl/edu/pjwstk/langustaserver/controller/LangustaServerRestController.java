package pl.edu.pjwstk.langustaserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjwstk.langustaserver.service.RecipeService;

@RestController
@RequestMapping("/")
public class LangustaServerRestController {
    private final RecipeService recipeService;

    public LangustaServerRestController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/")
    public ResponseEntity<String> getRecipeByName() {
        return ResponseEntity.ok("hello world");
    }
}
