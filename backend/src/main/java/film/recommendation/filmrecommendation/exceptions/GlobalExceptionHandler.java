package film.recommendation.filmrecommendation.exceptions;

import film.recommendation.filmrecommendation.entity.SessionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FilmNotFoundException.class)
    public ResponseEntity<Object> handleFilmNotFoundException(FilmNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
    }

    @ExceptionHandler(SessionNotFoundException.class)
    public ResponseEntity<Object> handleSessionNotFoundException(SessionNotFoundException e) {
        return new ResponseEntity<>(new ArrayList<SessionDTO>(), HttpStatus.OK);
    }

    @ExceptionHandler(NotFoundSoManySeatsException.class)
    public ResponseEntity<Object> handleNotFoundSoManySeatsException(NotFoundSoManySeatsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
    }
}
