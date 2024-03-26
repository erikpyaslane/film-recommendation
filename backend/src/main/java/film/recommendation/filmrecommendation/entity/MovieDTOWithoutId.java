package film.recommendation.filmrecommendation.entity;

import java.util.Set;

public record MovieDTOWithoutId(
        String title,
        Double rating,
        String ageRestriction,
        Integer releaseYear,
        Set<String> genres
){
}
