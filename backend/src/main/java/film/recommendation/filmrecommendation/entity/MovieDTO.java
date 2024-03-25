package film.recommendation.filmrecommendation.entity;

import film.recommendation.filmrecommendation.enums.AgeRestriction;
import film.recommendation.filmrecommendation.enums.Genre;

import java.util.Set;

public record MovieDTO(
        String title,
        Double rating,
        AgeRestriction ageRestriction,
        Integer releaseYear,
        Set<Genre> genres
)  {
}
