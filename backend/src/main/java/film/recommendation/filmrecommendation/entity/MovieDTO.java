package film.recommendation.filmrecommendation.entity;

import java.util.Set;

public record MovieDTO(
        String title,
        Double rating,
        String ageRestriction,
        Integer releaseYear,
        Set<String> genres
)  {
}
