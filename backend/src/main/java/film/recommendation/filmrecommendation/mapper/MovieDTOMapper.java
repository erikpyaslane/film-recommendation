package film.recommendation.filmrecommendation.mapper;

import film.recommendation.filmrecommendation.entity.Movie;
import film.recommendation.filmrecommendation.entity.MovieDTO;
import film.recommendation.filmrecommendation.entity.MovieDTOWithoutId;
import film.recommendation.filmrecommendation.enums.AgeRestriction;
import film.recommendation.filmrecommendation.enums.Genre;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MovieDTOMapper {

    public MovieDTO MovieToDTO(Movie movie) {

        return new MovieDTO(
                movie.getId(),
                movie.getTitle(),
                movie.getRating(),
                movie.getAgeRestriction().name(),
                movie.getReleaseYear(),
                movie.getGenres()
                        .stream()
                        .map(Genre::name)
                        .collect(Collectors.toSet())
        );
    }

    public Movie DTOWithoutIdToMovie(MovieDTOWithoutId movieDTO) {
        //Converts list of genres(as String) to Set<Genre>


        AgeRestriction ageRestriction = AgeRestriction
                .getAgeRestrictionByName(String.valueOf(movieDTO.ageRestriction()));

        return new Movie(
                movieDTO.title(),
                movieDTO.rating(),
                ageRestriction,
                movieDTO.releaseYear(),
                movieDTO.genres().stream()
                        .map(Genre::getGenreByEstonianName)
                        .collect(Collectors.toSet())
        );
    }

    public Movie DTOToMovie(MovieDTO movieDTO) {

        return Movie.builder()
                .id(movieDTO.id())
                .title(movieDTO.title())
                .rating(movieDTO.rating())
                .ageRestriction(AgeRestriction.valueOf(movieDTO.ageRestriction()))
                .releaseYear(movieDTO.releaseYear())
                .genres(movieDTO.genres().stream()
                        .map(Genre::getGenreByEstonianName)
                        .collect(Collectors.toSet()))
                .build();
    }


}
