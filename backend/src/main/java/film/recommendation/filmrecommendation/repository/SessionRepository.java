package film.recommendation.filmrecommendation.repository;

import film.recommendation.filmrecommendation.entity.Session;
import film.recommendation.filmrecommendation.enums.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    List<Session> findAll();

    @Query("SELECT ss FROM Session ss WHERE " +
            "ss.dateOfSession <= :date AND ss.timeOfSession <= :time")
    List<Session> findAllActualSessions(LocalDate date, LocalTime time);

    @Query("SELECT ss FROM Session ss JOIN ss.movie mv WHERE :genre MEMBER OF mv.genres")
    List<Session> findAllSessionsByGenre(Genre genre);

    Optional<Session> findById(long id);

    List<Session> findAllByDateOfSession(LocalDate date);

    @Query("SELECT ss FROM Session ss WHERE " +
            "ss.dateOfSession = :date AND ss.timeOfSession >= :time " +
            "ORDER BY ss.timeOfSession")
    List<Session> findAllByDateOfSessionAndTimeOfSession(
            LocalDate date, LocalTime time);


}
