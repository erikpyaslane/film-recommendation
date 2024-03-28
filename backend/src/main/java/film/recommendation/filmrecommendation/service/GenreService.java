package film.recommendation.filmrecommendation.service;

import film.recommendation.filmrecommendation.enums.Genre;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GenreService {

    public Set<String> getAllGenres() {
        return Arrays.stream(Genre.values())
                .map(Genre::getNameEstonian)
                .collect(Collectors.toSet());
    }
}
