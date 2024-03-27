package film.recommendation.filmrecommendation.enums;

import lombok.Getter;

@Getter
public enum Genre {
    ACTION("Märul"), ADVENTURE("Seiklus"), ANIMATION("Animatsioon"),
    BIOGRAPHY("Biograafia"), COMEDY("Komöödia"), CRIME("Kuritegevus"),
    DOCUMENTARY("Dokumentaal"), DRAMA("Draama"), FAMILY("Pere"),
    FANTASY("Ulmefilm"), HISTORY("Ajalugu"), HORROR("Õudus"),
    MUSIC("Musikaalne"), MYSTERY("Müstika"), ROMANCE("Romantika"),
    SPORT("Sport"), THRILLER("Triller"), WAR("Sõda"),
    WESTERN("Lääne"), NOPE("");

    private final String nameEstonian;

    Genre(String nameEstonian) {
        this.nameEstonian = nameEstonian;
    }

    public static Genre getGenreByEstonianName(String nameEstonian) {
        for (Genre genre : values()) {
            if (nameEstonian.equals(genre.nameEstonian))
                return genre;
        }
        return NOPE;
    }

}
