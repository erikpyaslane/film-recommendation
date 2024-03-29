package film.recommendation.filmrecommendation.repository;

import film.recommendation.filmrecommendation.entity.Session;
import film.recommendation.filmrecommendation.enums.AgeRestriction;
import film.recommendation.filmrecommendation.enums.Genre;
import film.recommendation.filmrecommendation.enums.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {


    Optional<Session> findById(long id);


    @Query("SELECT DISTINCT ss " +
            "FROM Session ss " +
            "JOIN ss.movie mv JOIN mv.genres genre " +
            "WHERE ss.dateOfSession = :date " +
            "AND ss.timeOfSession >= :time " +
            "AND (:ageRestrictions IS NULL OR mv.ageRestriction IN :ageRestrictions)" +
            "AND (:genres IS NULL OR genre in :genres) " +
            "AND (:languages IS NULL OR ss.language IN :languages) " +
            "ORDER BY ss.timeOfSession")
    List<Session> findAllSessionsByFiltersAndCurrentDate(
            @Param("date") LocalDate date,
            @Param("time") LocalTime time,
            @Param("genres") Set<Genre> genres,
            @Param("ageRestrictions") Set<AgeRestriction> ageRestrictions,
            @Param("languages")Set<Language> languages);


    @Query("SELECT DISTINCT ss " + "FROM Session ss " +
            "JOIN ss.movie mv JOIN mv.genres genre WHERE " +
            "ss.dateOfSession = :date " +
            "AND (:ageRestrictions IS NULL OR mv.ageRestriction IN :ageRestrictions)" +
            "AND (:genres IS NULL OR genre in :genres) " +
            "AND (:languages IS NULL OR ss.language IN :languages) " +
            "ORDER BY ss.timeOfSession ")
    List<Session> findAllSessionsByFilters(
            @Param("date") LocalDate date,
            @Param("genres") Set<Genre> genres,
            @Param("ageRestrictions") Set<AgeRestriction> ageRestrictions,
            @Param("languages")Set<Language> languages);


    @Query("SELECT ss FROM Session ss WHERE " +
            "ss.dateOfSession = :date AND ss.timeOfSession >= :time " +
            "ORDER BY ss.timeOfSession")
    List<Session> findAllByDateAndTime(
            LocalDate date, LocalTime time);


    @Query("SELECT DISTINCT ss FROM Session ss " +
            "JOIN ss.movie mv " +
            "JOIN mv.genres genre " +
            "WHERE genre IN :genres AND " +
            "ss.dateOfSession = :date " +
            "ORDER BY ss.timeOfSession")
    List<Session> findAllSessionsByGenresAndDate(
            @Param("genres") Set<Genre> genres, @Param("date") LocalDate date);


    @Query("SELECT DISTINCT ss FROM Session ss " +
            "JOIN ss.movie mv " +
            "JOIN mv.genres genre " +
            "WHERE genre IN :genres AND " +
            " ss.timeOfSession >= :time " +
            "ORDER BY ss.timeOfSession")
    List<Session> findAllSessionsByGenresAndCurrentDate(
            @Param("genres") Set<Genre> genres, @Param("time") LocalTime time);


    @Query("SELECT ss FROM Session ss JOIN ss.movie mv WHERE " +
            "(ss.dateOfSession = :date)" +
            " AND mv.ageRestriction IN :ageRestrictions")
    List<Session> findAllByDateAndAgeRestrictionList(
            Set<AgeRestriction> ageRestrictions, LocalDate date);


    @Query("SELECT ss FROM Session ss JOIN ss.movie mv WHERE " +
            "mv.ageRestriction IN :ageRestrictions AND ss.dateOfSession = :date AND ss.timeOfSession >= :time " +
            "ORDER BY ss.timeOfSession")
    List<Session> findAllSessionsByCurrentDateAndTimeAndAgeRestrictionList(
            Set<AgeRestriction> ageRestrictions, LocalDate date, LocalTime time);
}
