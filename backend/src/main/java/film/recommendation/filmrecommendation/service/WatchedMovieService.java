package film.recommendation.filmrecommendation.service;

import film.recommendation.filmrecommendation.entity.MovieDTO;
import film.recommendation.filmrecommendation.entity.WatchedMovie;
import film.recommendation.filmrecommendation.mapper.MovieDTOMapper;
import film.recommendation.filmrecommendation.repository.WatchedMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WatchedMovieService {

    private final WatchedMovieRepository watchedMovieRepository;

    private final MovieDTOMapper movieDTOMapper;


    @Autowired
    public WatchedMovieService(WatchedMovieRepository watchedMovieRepository, MovieDTOMapper movieDTOMapper) {
        this.watchedMovieRepository = watchedMovieRepository;
        this.movieDTOMapper = movieDTOMapper;
    }

    /**
     * Shows if in watched movies exist movie with given id
     *
     * Ei tööta
     *
     * @param id of given movie
     * @return true if exists, false if not
     */
    public boolean movieExistsInWatchedMovies(long id) {
        return watchedMovieRepository.existsByMovie_Id(id);
    }

    /**
     * Saves movie as watched
     *
     * @param movie that i "watched"
     * @return watched movie
     */
    public WatchedMovie saveWatchedMovie(MovieDTO movie){
        return watchedMovieRepository.save(new WatchedMovie(movieDTOMapper.DTOToMovie(movie)));
    }
}
