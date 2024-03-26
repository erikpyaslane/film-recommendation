package film.recommendation.filmrecommendation.entity;

import film.recommendation.filmrecommendation.enums.AgeRestriction;
import film.recommendation.filmrecommendation.enums.Genre;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "rating")
    private double rating;

    @Column(name = "age_restriction")
    @Enumerated(EnumType.STRING)
    private AgeRestriction ageRestriction;

    @Column(name = "release_year")
    private int releaseYear;

    @ElementCollection(targetClass = Genre.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "movie_genres", joinColumns = @JoinColumn(name = "movie_id"))
    @Column(name = "genre")
    private Set<Genre> genres;

    public Movie(String title, double rating, AgeRestriction ageRestriction, int releaseYear, Set<Genre> genres) {
        this.title = title;
        this.rating = rating;
        this.ageRestriction = ageRestriction;
        this.releaseYear = releaseYear;
        this.genres = genres;
    }
}