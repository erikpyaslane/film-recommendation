package film.recommendation.filmrecommendation.entity;

public record SessionDTOWithoutId(

        Long movieId,

        String dateOfSession,

        String timeOfSession,

        String language

) {
}
