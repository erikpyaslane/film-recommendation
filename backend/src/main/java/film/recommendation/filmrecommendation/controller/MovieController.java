package film.recommendation.filmrecommendation.controller;

import film.recommendation.filmrecommendation.entity.MovieDTO;
import film.recommendation.filmrecommendation.entity.MovieDTOWithoutId;
import film.recommendation.filmrecommendation.exceptions.FilmNotFoundException;
import film.recommendation.filmrecommendation.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    /**
     * Retrieves all movies(Available only on Postman)
     *
     * @return list of all movies
     */
    @GetMapping
    public ResponseEntity<List<MovieDTO>> getAllMovies() {
        return new ResponseEntity<>(movieService.getAllMovies(), HttpStatus.OK);
    }

    /**
     * Retrieves movie by id
     *
     * @param id The id of movie
     * @return movieDTO as response
     */
    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable long id) throws FilmNotFoundException {
        return new ResponseEntity<>(movieService.getMovieById(id), HttpStatus.OK);
    }

    /**
     * Creates movie (Available only in Postman)
     *
     * @param movieDTO parameters of movie to create
     * @return created movie
     *
     * POST http://localhost:8080/api/movies
     * {
     *     "title": "Titanik",
     *     "rating": 8.4,
     *     "ageRestriction": "PG-13",
     *     "releaseYear": 2007,
     *     "genres": ["Draama", "Romantika"]
     * }
     */
    @PostMapping
    public ResponseEntity<MovieDTO> createMovie(@RequestBody MovieDTOWithoutId movieDTO) {
        return new ResponseEntity<>(movieService.createMovie(movieDTO), HttpStatus.CREATED);
    }
}
