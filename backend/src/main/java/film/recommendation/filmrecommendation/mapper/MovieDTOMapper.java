package film.recommendation.filmrecommendation.mapper;

import film.recommendation.filmrecommendation.entity.Movie;
import film.recommendation.filmrecommendation.entity.MovieDTO;
import film.recommendation.filmrecommendation.enums.AgeRestriction;
import film.recommendation.filmrecommendation.enums.Genre;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MovieDTOMapper {

    public MovieDTO MovieToDTO(Movie movie) {

        return new MovieDTO(
                movie.getTitle(),
                movie.getRating(),
                movie.getAgeRestriction().name(),
                movie.getReleaseYear(),
                movie.getGenres()
                        .stream()
                        .map(Genre::getNameEstonian)
                        .collect(Collectors.toSet())
        );
    }

    public Movie DTOToMovie(MovieDTO movieDTO) {
        //Converts list of genres(as String) to Set<Genre>
        Set<Genre> genres = movieDTO.genres().stream()
                .map(Genre::getGenreByEstonianName)
                .collect(Collectors.toSet());

        AgeRestriction ageRestriction = AgeRestriction
                .getAgeRestrictionByName(String.valueOf(movieDTO.ageRestriction()));

        return new Movie(
                movieDTO.title(),
                movieDTO.rating(),
                ageRestriction,
                movieDTO.releaseYear(),
                genres
        );
    }

}
