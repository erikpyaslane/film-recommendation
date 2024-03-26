package film.recommendation.filmrecommendation.entity;

import film.recommendation.filmrecommendation.enums.Language;

import java.time.LocalDate;
import java.time.LocalTime;

public record SessionDTO (
        Movie movie,

        LocalDate dateOfSession,

        LocalTime timeOfSession,

        Language language,

        boolean[][] seats
) {

}
