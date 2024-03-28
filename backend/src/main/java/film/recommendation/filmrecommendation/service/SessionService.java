package film.recommendation.filmrecommendation.service;

import film.recommendation.filmrecommendation.entity.*;
import film.recommendation.filmrecommendation.enums.AgeRestriction;
import film.recommendation.filmrecommendation.enums.Genre;
import film.recommendation.filmrecommendation.exceptions.FilmNotFoundException;
import film.recommendation.filmrecommendation.exceptions.SessionNotFoundException;
import film.recommendation.filmrecommendation.mapper.MovieDTOMapper;
import film.recommendation.filmrecommendation.mapper.SessionMapperDTO;
import film.recommendation.filmrecommendation.repository.SessionRepository;
import film.recommendation.filmrecommendation.utils.SeatRecommender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

    public List<SessionDTO> getAllSessions(){
        return sessionRepository.findAll()
                .stream()
                .map(sessionMapperDTO::SessionToDTO)
                .collect(Collectors.toList());
    }

    public List<SessionDTO> getAllSessionsWithFilters(
            LocalDateTime datetime, Set<String> genresString, Set<String> ageRestrictionsString)
            throws SessionNotFoundException {
        if (genresString == null && ageRestrictionsString == null)
            return getSessionsByDateAndTime(datetime);

        Set<Genre> genres;
        Set<AgeRestriction> ageRestrictions;

        if (genresString == null || genresString.isEmpty()) {
            ageRestrictions = ageRestrictionsString
                    .stream()
                    .map(AgeRestriction::valueOf).collect(Collectors.toSet());
            return getSessionsByAgeRestrictions(datetime, ageRestrictions);
        }

        if (ageRestrictionsString == null || ageRestrictionsString.isEmpty()) {
            genres = genresString.stream().map(Genre::getGenreByEstonianName).collect(Collectors.toSet());
            return getAllSessionsByGenres(datetime, genres);
        }

        return getSessionsByDateAndTime(datetime);
    }

    public List<SessionDTO> getSessionsByAgeRestrictions(LocalDateTime datetime, Set<AgeRestriction> ageRestrictions) {
        LocalDate date = datetime.toLocalDate();
        LocalTime time = datetime.toLocalTime();

        return sessionRepository.findAllByDateAndTimeAndAgeRestrictionList(date, time, ageRestrictions)
                .stream()
                .map(sessionMapperDTO::SessionToDTO)
                .collect(Collectors.toList());

    }

    public List<SessionDTO> getAllSessionsByGenres(LocalDateTime datetime, Set<Genre> genres) {

        LocalDate date = datetime.toLocalDate();
        LocalTime time = datetime.toLocalTime();
        /*
        List<Session> sessions = new ArrayList<>();
        for (Genre genre : genres){
            System.out.println(genre);
            sessions.addAll(
                    sessionRepository.findAllSessionsByGenreAndDate(date, genre)
            );
        }

         */
        return sessionRepository.findAllSessionsByGenresAndDate(genres, date)
                .stream()
                .map(sessionMapperDTO::SessionToDTO)
                .collect(Collectors.toList());
    }

    public List<SessionDTO> getSessionsByDateAndTime(LocalDateTime datetime) throws SessionNotFoundException {

        LocalDate date = datetime.toLocalDate();
        LocalTime time = datetime.toLocalTime();
        List<Session> sessions;
        if (date.isAfter(LocalDate.now()))
            sessions = sessionRepository.findAllByDateOfSession(date);
        else
            sessions = sessionRepository
                .findAllByDateAndTime(date, time);
        if (sessions.isEmpty())
            throw new SessionNotFoundException("No sessions found");
        return sessions.stream()
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
