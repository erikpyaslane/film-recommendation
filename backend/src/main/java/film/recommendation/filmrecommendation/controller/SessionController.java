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
import java.util.Set;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    private final SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    /**
     * Endpoint for getting chosen session
     *
     * @param id the id of session
     * @return The DTO of found session
     */
    @GetMapping("/{id}")
    public ResponseEntity<SessionDTO> getChosenSession(@PathVariable long id) throws SessionNotFoundException {
        return new ResponseEntity<>(sessionService.getChosenSession(id), HttpStatus.OK);
    }

    /**
     * Retrieves sessions that response to given filters (Possible to use in GUI)
     * http://localhost:8081/sessions
     *
     * @param date date of session
     * @param genres genres of movie
     * @param ageRestrictions restrictions for movie
     * @param languages language of movie
     *
     * @return list of sessions DTO
     */
    @GetMapping
    public ResponseEntity<List<SessionDTO>> getSessionsByFilters(
            @RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime date,
            @RequestParam(name="genres", required = false) Set<String> genres,
            @RequestParam(name="ageRestrictions", required = false) Set<String> ageRestrictions,
            @RequestParam(name="languages", required = false) Set<String> languages) {
        return new ResponseEntity<>(
                sessionService.getAllSessionsWithFilters(date, genres, ageRestrictions, languages),
                HttpStatus.OK
        );
    }

    /**
     * Retrieves all existing sessions (Only for Postman)
     *
     * @return list of sessions DTO
     */
    @GetMapping("/all")
    public ResponseEntity<List<SessionDTO>> getAllSessions() {
        return new ResponseEntity<>(
                sessionService.getAllSessions(),
                HttpStatus.OK
        );
    }

    /**
     *  Creates session
     *
     * @param sessionDTOWithoutId parameters of request
     * @return DTO of created session
     *
     * POST http://localhost:8080/api/sessions
     * {
     *  "movieId": 11,
     *  "dateOfSession": "2024-03-30",
     *  "timeOfSession": "17:15:00",
     *  "language": "Eesti"
     * }
     *
     */
    @PostMapping
    public ResponseEntity<SessionDTO> createSession(@RequestBody SessionDTOWithoutId sessionDTOWithoutId)
            throws FilmNotFoundException {
        return new ResponseEntity<>(sessionService.saveSession(sessionDTOWithoutId), HttpStatus.CREATED);
    }

    /**
     * Retrieves the best free seats
     *
     * @param id of session
     * @param seatsCount count of seats to recommend
     * @return positions of seats as 2D array
     */
    @GetMapping("/{id}/free_seats={seatsCount}")
    public ResponseEntity<int[][]> getFreeSeats(@PathVariable long id, @PathVariable int seatsCount)
            throws SessionNotFoundException {
        return new ResponseEntity<>(
                sessionService.getTheBestFreeSeats(id, seatsCount), HttpStatus.OK
        );
    }

}
