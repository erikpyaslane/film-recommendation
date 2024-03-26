package film.recommendation.filmrecommendation.entity;

import film.recommendation.filmrecommendation.enums.Language;

import java.sql.Time;
import java.util.Date;

public record SessionDTOWithoutId(

        Long movieId,

        String dateOfSession,

        String timeOfSession,

        String language

) {
}
