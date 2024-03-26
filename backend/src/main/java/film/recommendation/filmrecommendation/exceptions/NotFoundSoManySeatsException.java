package film.recommendation.filmrecommendation.exceptions;

public class NotFoundSoManySeatsException extends IllegalArgumentException{
    private final String message;

    public NotFoundSoManySeatsException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
