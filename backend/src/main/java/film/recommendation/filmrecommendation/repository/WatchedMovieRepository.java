package film.recommendation.filmrecommendation.repository;

import film.recommendation.filmrecommendation.entity.Movie;
import film.recommendation.filmrecommendation.entity.WatchedMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchedMovieRepository extends JpaRepository<WatchedMovie, Long> {

    WatchedMovie getWatchedMovieByMovie_Title(String title);

    boolean existsByMovie_Id(long id);
}
