package film.recommendation.filmrecommendation.service;

import film.recommendation.filmrecommendation.enums.AgeRestriction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgeRestrictionService {

    public List<AgeRestriction> getAllAgeRestrictions() {
        return List.of(AgeRestriction.values());
    }
}
