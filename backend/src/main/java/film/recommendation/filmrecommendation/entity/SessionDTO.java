package film.recommendation.filmrecommendation.entity;

import film.recommendation.filmrecommendation.enums.Language;

import java.time.LocalDate;
import java.time.LocalTime;

public record SessionDTO (
        Long id,

        MovieDTO movie,

        LocalDate dateOfSession,

        LocalTime timeOfSession,

        String language,

        boolean[][] seats
) {

}
