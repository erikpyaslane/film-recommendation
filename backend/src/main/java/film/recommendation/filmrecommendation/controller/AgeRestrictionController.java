package film.recommendation.filmrecommendation.controller;


import film.recommendation.filmrecommendation.service.AgeRestrictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * Idee selle klassi loomiseks andis ChatGPT https://chat.openai.com/c/8c226f24-4615-4c0c-8ef9-5fbd6eac5363
 */

@RestController
@RequestMapping("/api/age_restrictions")
public class AgeRestrictionController {

    private final AgeRestrictionService ageRestrictionService;

    @Autowired
    public AgeRestrictionController(AgeRestrictionService ageRestrictionService) {
        this.ageRestrictionService = ageRestrictionService;
    }

    @GetMapping
    public ResponseEntity<Set<String>> getAgeRestrictions() {
        return new ResponseEntity<>(ageRestrictionService.getAllAgeRestrictions(), HttpStatus.OK);
    }

}
