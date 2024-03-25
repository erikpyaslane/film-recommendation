package film.recommendation.filmrecommendation.mapper;

import film.recommendation.filmrecommendation.entity.Movie;
import film.recommendation.filmrecommendation.entity.MovieDTO;
import org.springframework.stereotype.Component;

@Component
public class MovieDTOMapper {

    public MovieDTO MovieToDTO(Movie movie) {
        return new MovieDTO(
                movie.getTitle(),
                movie.getRating(),
                movie.getAgeRestriction(),
                movie.getReleaseYear(),
                movie.getGenres()
        );
    }

    public Movie DTOToMovie(MovieDTO movieDTO) {
        return new Movie(
                movieDTO.title(),
                movieDTO.rating(),
                movieDTO.ageRestriction(),
                movieDTO.releaseYear(),
                movieDTO.genres()
        );
    }

}
