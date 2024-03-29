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

    public List<SessionDTO> getAllSessions(){
        return sessionRepository.findAll()
                .stream()
                .map(sessionMapperDTO::SessionToDTO)
                .collect(Collectors.toList());
    }

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

    private Set<Genre> mapToGenres(Set<String> genresString) {
        if (genresString == null || genresString.isEmpty()) {
            return null;
        }
        return genresString.stream()
                .map(Genre::getGenreByEstonianName)
                .collect(Collectors.toSet());
    }

    private Set<AgeRestriction> mapToAgeRestrictions(Set<String> ageRestrictionsString) {
        if (ageRestrictionsString == null || ageRestrictionsString.isEmpty()) {
            return null;
        }
        return ageRestrictionsString.stream()
                .map(AgeRestriction::getAgeRestrictionByName)
                .collect(Collectors.toSet());
    }

    private Set<Language> mapToLanguages(Set<String> languagesString) {
        if (languagesString == null || languagesString.isEmpty()) {
            return null;
        }
        return languagesString.stream()
                .map(Language::getLanguageByStringValue)
                .collect(Collectors.toSet());
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
