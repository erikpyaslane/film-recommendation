package film.recommendation.filmrecommendation.exceptions;

import org.springframework.data.crossstore.ChangeSetPersister;

public class SessionNotFoundException extends ChangeSetPersister.NotFoundException {

    private final String message;

    public SessionNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
