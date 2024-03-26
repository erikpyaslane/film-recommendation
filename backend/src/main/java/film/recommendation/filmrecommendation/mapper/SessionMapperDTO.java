package film.recommendation.filmrecommendation.mapper;

import film.recommendation.filmrecommendation.entity.*;
import film.recommendation.filmrecommendation.enums.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class SessionMapperDTO {

    public Session DTOWithoutIdToSession(SessionDTOWithoutId sessionDTO, Movie movie) {

        return new Session(
                movie,
                LocalDate.parse(sessionDTO.dateOfSession()),
                LocalTime.parse(sessionDTO.timeOfSession()),
                Language.getLanguageByStringValue(sessionDTO.language())
        );
    }

    public SessionDTO SessionToDTO(Session session) {
        return new SessionDTO(
                session.getId(),
                session.getMovie(),
                session.getDateOfSession(),
                session.getTimeOfSession(),
                session.getLanguage(),
                session.getSeats()
        );
    }
}
