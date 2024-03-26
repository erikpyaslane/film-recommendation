package film.recommendation.filmrecommendation.service;

import film.recommendation.filmrecommendation.repository.SessionRepository;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    private SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }
}
