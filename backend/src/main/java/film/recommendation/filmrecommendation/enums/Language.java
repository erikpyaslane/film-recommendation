package film.recommendation.filmrecommendation.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Language {
    EST ("Eesti"),
    ENG ("Inglise");

    private final String name;

    public static Language getLanguageByStringValue(String string) {
        for (Language language : values()) {
            if (language.name.equals(string))
                return language;
        }
        return ENG;
    }
}
