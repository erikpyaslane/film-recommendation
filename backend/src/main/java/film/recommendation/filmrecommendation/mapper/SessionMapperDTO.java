package film.recommendation.filmrecommendation.mapper;

import film.recommendation.filmrecommendation.entity.MovieDTO;
import film.recommendation.filmrecommendation.entity.Session;
import film.recommendation.filmrecommendation.entity.SessionDTO;
import film.recommendation.filmrecommendation.entity.SessionDTOWithoutId;
import film.recommendation.filmrecommendation.enums.Language;
import film.recommendation.filmrecommendation.exceptions.FilmNotFoundException;
import film.recommendation.filmrecommendation.service.MovieService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class SessionMapperDTO {

    private final MovieService movieService;
    private final MovieDTOMapper movieDTOMapper;

    public SessionMapperDTO(MovieService movieService, MovieDTOMapper movieDTOMapper) {
        this.movieService = movieService;
        this.movieDTOMapper = movieDTOMapper;
    }

    public Session DTOWithoutIdToSession(SessionDTOWithoutId sessionDTO) throws FilmNotFoundException {
        MovieDTO movieDTO = movieService.getMovieById(sessionDTO.movieId());

        return new Session(
                movieDTOMapper.DTOToMovie(movieDTO),
                LocalDate.parse(sessionDTO.dateOfSession()),
                LocalTime.parse(sessionDTO.timeOfSession()),
                Language.getLanguageByStringValue(sessionDTO.language())
        );
    }

    public SessionDTO SessionToDTO(Session session) {
        return new SessionDTO(
                session.getMovie(),
                session.getDateOfSession(),
                session.getTimeOfSession(),
                session.getLanguage(),
                session.getSeats()
        );
    }
}
