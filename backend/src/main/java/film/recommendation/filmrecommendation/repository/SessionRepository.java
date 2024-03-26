package film.recommendation.filmrecommendation.repository;

import film.recommendation.filmrecommendation.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
}
