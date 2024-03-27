package film.recommendation.filmrecommendation.controller;

import film.recommendation.filmrecommendation.entity.SessionDTO;
import film.recommendation.filmrecommendation.entity.SessionDTOWithoutId;
import film.recommendation.filmrecommendation.exceptions.FilmNotFoundException;
import film.recommendation.filmrecommendation.exceptions.SessionNotFoundException;
import film.recommendation.filmrecommendation.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    private final SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<SessionDTO>> getAllSessions() throws SessionNotFoundException {
        return new ResponseEntity<>(sessionService.getAllSessions(), HttpStatus.OK);
    }

    @GetMapping("/actual")
    public ResponseEntity<List<SessionDTO>> getActualSessions() throws SessionNotFoundException {
        return new ResponseEntity<>(sessionService.getAllActualSessions(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionDTO> getChosenSession(@PathVariable long id) throws SessionNotFoundException {
        return new ResponseEntity<>(sessionService.getChosenSession(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<SessionDTO>> getSessionsByDate(
            @RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime date)
            throws SessionNotFoundException {
        System.out.println(date);
        return new ResponseEntity<>(sessionService.getSessionsByDateAndTime(date), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SessionDTO> createSession(@RequestBody SessionDTOWithoutId sessionDTOWithoutId)
            throws FilmNotFoundException {
        return new ResponseEntity<>(sessionService.saveSession(sessionDTOWithoutId), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/free_seats={seatsCount}")
    public ResponseEntity<int[][]> getFreeSeats(@PathVariable long id, @PathVariable int seatsCount)
            throws SessionNotFoundException {
        return new ResponseEntity<>(
                sessionService.getTheBestFreeSeats(id, seatsCount), HttpStatus.OK
        );
    }

}
