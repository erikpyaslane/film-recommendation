package film.recommendation.filmrecommendation.repository;

import film.recommendation.filmrecommendation.entity.Movie;
import film.recommendation.filmrecommendation.enums.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Optional<Movie> findById(long id);

    Optional<List<Movie>> findAllByGenresIsNotContainingIgnoreCase(Genre genre);

}
