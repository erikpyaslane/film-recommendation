package film.recommendation.filmrecommendation.enums;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public enum Language {
    EST ("EST"),
    ENG ("ENG");

    private final String name;

    public static Language getLanguageByStringValue(String string) {
        for (Language language : values()) {
            if (language.name.equals(string))
                return language;
        }
        return ENG;
    }
}
