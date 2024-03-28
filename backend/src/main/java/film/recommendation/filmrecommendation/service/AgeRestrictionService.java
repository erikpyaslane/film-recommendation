package film.recommendation.filmrecommendation.service;

import film.recommendation.filmrecommendation.enums.AgeRestriction;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AgeRestrictionService {

    public Set<String> getAllAgeRestrictions() {
        return Arrays.stream(AgeRestriction.values())
                .map(AgeRestriction::getName)
                .collect(Collectors.toSet());
    }
}
