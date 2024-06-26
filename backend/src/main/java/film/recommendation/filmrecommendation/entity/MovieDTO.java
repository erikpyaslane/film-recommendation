package film.recommendation.filmrecommendation.entity;

import java.util.Set;

public record MovieDTO(
        long id,
        String title,
        double rating,
        String ageRestriction,
        int releaseYear,
        Set<String> genres
)  {
}
