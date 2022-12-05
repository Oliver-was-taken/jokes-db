package ch.bbw.m151.jokesdb.datamodel.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum JokeTypes {
    SINGLE("single"),
    TWO_PART("twopart");

    private final String jokeType;

    JokeTypes(String jokeType) {
        this.jokeType = jokeType;
    }

    @JsonGetter
    public static JokeTypes fromString(String jokeType) {
        return switch (jokeType) {
            case "single" -> JokeTypes.SINGLE;
            case "twopart" -> JokeTypes.TWO_PART;
            default -> throw new IllegalArgumentException("JokeType does not exist");
        };
    }

    @JsonValue
    public String getJokeType() {
        return jokeType;
    }

    @Override
    public String toString() {
        return jokeType;
    }
}
