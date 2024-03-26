package film.recommendation.filmrecommendation.entity;

import film.recommendation.filmrecommendation.enums.Language;
import film.recommendation.filmrecommendation.utils.SeatGenerator;
import film.recommendation.filmrecommendation.utils.SeatsMatrixConverter;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "sessions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private Movie movie;

    private LocalDate dateOfSession;

    private LocalTime timeOfSession;

    private Language language;

    @Convert(converter = SeatsMatrixConverter.class)
    private boolean[][] seats;

    public Session(Movie movie, LocalDate dateOfSession, LocalTime timeOfSession, Language language) {
        this.movie = movie;
        this.dateOfSession = dateOfSession;
        this.timeOfSession = timeOfSession;
        this.language = language;
        this.seats = SeatGenerator.fillSeats(10, 10);
    }
}
