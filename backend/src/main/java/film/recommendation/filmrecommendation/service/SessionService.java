package film.recommendation.filmrecommendation.service;

import film.recommendation.filmrecommendation.entity.*;
import film.recommendation.filmrecommendation.exceptions.FilmNotFoundException;
import film.recommendation.filmrecommendation.exceptions.SessionNotFoundException;
import film.recommendation.filmrecommendation.mapper.MovieDTOMapper;
import film.recommendation.filmrecommendation.mapper.SessionMapperDTO;
import film.recommendation.filmrecommendation.repository.SessionRepository;
import film.recommendation.filmrecommendation.utils.SeatRecommender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final MovieService movieService;
    private final MovieDTOMapper movieDTOMapper;
    private final SessionMapperDTO sessionMapperDTO;

    @Autowired
    public SessionService(SessionRepository sessionRepository, MovieService movieService,
                          MovieDTOMapper movieDTOMapper, SessionMapperDTO sessionMapperDTO) {
        this.sessionRepository = sessionRepository;
        this.movieService = movieService;
        this.movieDTOMapper = movieDTOMapper;
        this.sessionMapperDTO = sessionMapperDTO;
    }

    public List<SessionDTO> getAllSessions() throws SessionNotFoundException {
        List<Session> sessions = sessionRepository.findAll();
        if (sessions.isEmpty())
            throw new SessionNotFoundException("No sessions found");
        return sessions
                .stream()
                .map(sessionMapperDTO::SessionToDTO)
                .collect(Collectors.toList());
    }

    public List<SessionDTO> getAllActualSessions() throws SessionNotFoundException {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        Optional<List<Session>> sessions = sessionRepository.findAllActualSessions(date, time);
        if (sessions.isEmpty())
            throw new SessionNotFoundException("No actual sessions found");
        return sessions.get()
                .stream()
                .map(sessionMapperDTO::SessionToDTO)
                .collect(Collectors.toList());
    }

    public SessionDTO getChosenSession(long id) throws SessionNotFoundException {
        Optional<Session> optionalSession = sessionRepository.findById(id);
        if (optionalSession.isEmpty())
            throw new SessionNotFoundException("There is no such session!");
        return sessionMapperDTO.SessionToDTO(optionalSession.get());
    }

    public SessionDTO saveSession(SessionDTOWithoutId sessionDTOWithoutId) throws FilmNotFoundException {
        MovieDTO movie = movieService.getMovieById(sessionDTOWithoutId.movieId());
        Session session = sessionMapperDTO
                .DTOWithoutIdToSession(sessionDTOWithoutId, movieDTOMapper.DTOToMovie(movie));

        return sessionMapperDTO.SessionToDTO(sessionRepository.save(session));
    }

    public int[][] getTheBestFreeSeats(long id, int countOfSeats) throws SessionNotFoundException {
        SessionDTO session = getChosenSession(id);
        boolean[][] seats = session.seats();
        return SeatRecommender.recommendFreeSeats(seats, countOfSeats);
    }
}
