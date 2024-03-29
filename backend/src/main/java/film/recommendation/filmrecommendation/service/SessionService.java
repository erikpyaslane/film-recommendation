package film.recommendation.filmrecommendation.service;

import film.recommendation.filmrecommendation.entity.*;
import film.recommendation.filmrecommendation.enums.AgeRestriction;
import film.recommendation.filmrecommendation.enums.Genre;
import film.recommendation.filmrecommendation.enums.Language;
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
import java.util.*;
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

    /**
     * Retrieves all sessions (Available only with Postman)
     *
     * @return List of sessions DTO
     */
    public List<SessionDTO> getAllSessions(){
        return sessionRepository.findAll()
                .stream()
                .map(sessionMapperDTO::SessionToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves sessions that responds to chosen filters
     * Available in GUI (http://localhost:8081/sessions)
     *
     * @param datetime date and time after current moment
     * @param genresString genre list as String type
     * @param ageRestrictionsString age restrictions list as String
     * @param languagesString language of session
     * @return list of sessions DTO
     */
    public List<SessionDTO> getAllSessionsWithFilters(
            LocalDateTime datetime, Set<String> genresString,
            Set<String> ageRestrictionsString, Set<String> languagesString) {

        LocalDate date = datetime.toLocalDate();
        LocalTime time = datetime.toLocalTime();

        Set<Genre> genres = mapToGenres(genresString);
        Set<AgeRestriction> ageRestrictions = mapToAgeRestrictions(ageRestrictionsString);
        Set<Language> languages = mapToLanguages(languagesString);

        List<Session> sessions;
        if (date.isEqual(LocalDate.now())) {
            sessions = sessionRepository.findAllSessionsByFiltersAndCurrentDate(date, time, genres, ageRestrictions, languages);
        } else {
            sessions = sessionRepository.findAllSessionsByFilters(date, genres, ageRestrictions, languages);
        }

        return sessions.stream()
                .map(sessionMapperDTO::SessionToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Convert set string  to enum set
     *
     * @param genresString list of genres as string
     *
     * @return set of enum genres
     */

    private Set<Genre> mapToGenres(Set<String> genresString) {
        if (genresString == null || genresString.isEmpty()) {
            return null;
        }
        return genresString.stream()
                .map(Genre::getGenreByEstonianName)
                .collect(Collectors.toSet());
    }

    /**
     * Convert set string  to enum set
     *
     * @param ageRestrictionsString list of age restrictions as string
     *
     * @return set of enum age restrictions
     */
    private Set<AgeRestriction> mapToAgeRestrictions(Set<String> ageRestrictionsString) {
        if (ageRestrictionsString == null || ageRestrictionsString.isEmpty()) {
            return null;
        }
        return ageRestrictionsString.stream()
                .map(AgeRestriction::getAgeRestrictionByName)
                .collect(Collectors.toSet());
    }

    /**
     * Convert set string  to enum set
     *
     * @param languagesString language as string
     * @return language as enum
     */
    private Set<Language> mapToLanguages(Set<String> languagesString) {
        if (languagesString == null || languagesString.isEmpty()) {
            return null;
        }
        return languagesString.stream()
                .map(Language::getLanguageByStringValue)
                .collect(Collectors.toSet());
    }


    /**
     * Retrieves session by id
     *
     * @param id the ID of session
     * @return target session DTO
     */
    public SessionDTO getChosenSession(long id) throws SessionNotFoundException {
        Optional<Session> optionalSession = sessionRepository.findById(id);
        if (optionalSession.isEmpty())
            throw new SessionNotFoundException("There is no such session!");
        return sessionMapperDTO.SessionToDTO(optionalSession.get());
    }

    /**
     * Saves new session (Available only through Postman)
     *
     * @param sessionDTOWithoutId session DTO
     * @return DTO of created session
     *
     */
    public SessionDTO saveSession(SessionDTOWithoutId sessionDTOWithoutId) throws FilmNotFoundException {
        MovieDTO movie = movieService.getMovieById(sessionDTOWithoutId.movieId());
        Session session = sessionMapperDTO
                .DTOWithoutIdToSession(sessionDTOWithoutId, movieDTOMapper.DTOToMovie(movie));

        return sessionMapperDTO.SessionToDTO(sessionRepository.save(session));
    }

    /**
     * Method, that complete algorithm of recommending free seats
     *
     * @param id the id of session
     * @param countOfSeats count of seats to recommend
     * @return position of given count of sits as 2D array
     */
    public int[][] getTheBestFreeSeats(long id, int countOfSeats) throws SessionNotFoundException {
        SessionDTO session = getChosenSession(id);
        boolean[][] seats = session.seats();
        return SeatRecommender.recommendFreeSeats(seats, countOfSeats);
    }
}
