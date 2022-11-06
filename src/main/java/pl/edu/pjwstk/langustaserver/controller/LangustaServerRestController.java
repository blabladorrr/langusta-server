package pl.edu.pjwstk.langustaserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjwstk.langustaserver.model.Recipe;

@RestController
@RequestMapping("/")
public class LangustaServerRestController {

    @GetMapping("/")
    public ResponseEntity<String> getRecipeByName() {
        return ResponseEntity.ok("hello world");
    }
}
