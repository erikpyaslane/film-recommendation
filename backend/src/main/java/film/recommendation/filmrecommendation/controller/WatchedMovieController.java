package film.recommendation.filmrecommendation.controller;


import film.recommendation.filmrecommendation.entity.Movie;
import film.recommendation.filmrecommendation.entity.MovieDTO;
import film.recommendation.filmrecommendation.entity.WatchedMovie;
import film.recommendation.filmrecommendation.service.WatchedMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/watched_movies")
public class WatchedMovieController {

    private final WatchedMovieService watchedMovieService;

    @Autowired
    public WatchedMovieController(WatchedMovieService watchedMovieService) {
        this.watchedMovieService = watchedMovieService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Boolean> existWatchedMovie(@PathVariable long id) {
        return new ResponseEntity<>(
                watchedMovieService.movieExistsInWatchedMovies(id),
                HttpStatus.OK);
    }

    /**
     * Controls if movie was watched before
     *
     * Seoses aja puudumisega, funktsionaalsus, mis on seotud varem vaadatud filmidega ei ole lõpuni viidud.
     * Saab ainult salvestada filme, kui vajutada nuppu broneerin (istukohtade lehel).
     * Idee oli selline, et siis lisada filter, mis soovitaks varem vaadatud filmide järgi filme valida
     * (kasvõi žanride järgi)
     *
     *
     * @param requestMovie movie that was watched
     * @return watched movie
     */
    @PostMapping
    public ResponseEntity<WatchedMovie> saveWatchedMovie(@RequestBody Map<String, MovieDTO> requestMovie) {
        MovieDTO movie = requestMovie.get("movie");
        return new ResponseEntity<>(watchedMovieService.saveWatchedMovie(movie), HttpStatus.OK);
    }
}
