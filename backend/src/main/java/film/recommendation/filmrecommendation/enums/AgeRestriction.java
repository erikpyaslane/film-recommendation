package film.recommendation.filmrecommendation.enums;

public enum AgeRestriction {
    G("G"),
    PG("PG"),
    PG_13("PG-13"),
    R("R"),
    NC_17("NC-17");

    private final String name;

    AgeRestriction(String name) {
        this.name = name;
    }

    public static AgeRestriction getAgeRestrictionByName(String name){
        for (AgeRestriction ageRestriction : values()) {
            if (ageRestriction.name.equals(name))
                return ageRestriction;
        }
        return G;
    }
}
