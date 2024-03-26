package film.recommendation.filmrecommendation.repository;

import film.recommendation.filmrecommendation.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    List<Session> findAll();
    /**
    @Query("SELECT ss FROM Session ss WHERE " +
            "ss.dateOfSession <= :date AND ss.timeOfSession <= :time")
    Optional<List<Session>> findAllActualSessions(LocalDate date, LocalTime time);
    */
    Optional<Session> findById(long id);
}
