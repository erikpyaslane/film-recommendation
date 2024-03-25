package film.recommendation.filmrecommendation.exceptions;

import org.springframework.data.crossstore.ChangeSetPersister;

public class FilmNotFoundException extends ChangeSetPersister.NotFoundException {

    private final String message;

    public FilmNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
