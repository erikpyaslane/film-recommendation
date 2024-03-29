package film.recommendation.filmrecommendation.service;

import film.recommendation.filmrecommendation.entity.Movie;
import film.recommendation.filmrecommendation.entity.MovieDTO;
import film.recommendation.filmrecommendation.entity.MovieDTOWithoutId;
import film.recommendation.filmrecommendation.enums.Genre;
import film.recommendation.filmrecommendation.exceptions.FilmNotFoundException;
import film.recommendation.filmrecommendation.mapper.MovieDTOMapper;
import film.recommendation.filmrecommendation.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    private final MovieDTOMapper movieDTOMapper;

    @Autowired
    public MovieService(MovieRepository movieRepository, MovieDTOMapper movieDTOMapper) {
        this.movieRepository = movieRepository;
        this.movieDTOMapper = movieDTOMapper;
    }

    /**
     * Retrieves all movies
     *
     * @return list of movies
     */
    public List<MovieDTO> getAllMovies() {
        return movieRepository.findAll()
                .stream()
                .map(movieDTOMapper::MovieToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves movie by its id
     *
     * @param id the id of movie
     * @return DTO of target movie
     */
    public MovieDTO getMovieById(long id) throws FilmNotFoundException {
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isEmpty())
            throw new FilmNotFoundException("Sellist filmi ei leidu");
        return movieDTOMapper.MovieToDTO(movie.get());
    }

    /**
     * Creates movie
     *
     * @param movieDTO parameters of movie to create
     * @return DTO of movie
     */
    public MovieDTO createMovie(MovieDTOWithoutId movieDTO) {
        return movieDTOMapper.MovieToDTO(
                movieRepository.save(
                        movieDTOMapper.DTOWithoutIdToMovie(movieDTO)));
    }


}
