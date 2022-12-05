package ch.bbw.m151.jokesdb.datamodel.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum Flags {
    NSFW("nsfw"),
    RELIGIOUS("religious"),
    POLITICAL("political"),
    RACIST("racist"),
    SEXIST("sexist"),
    EXPLICIT("explicit");
    private final String flags;

    Flags(String flags) {
        this.flags = flags;
    }

    @JsonGetter
    public static Flags fromString(String flag) {
        return switch (flag) {
            case "nsfw" -> Flags.NSFW;
            case "religious" -> Flags.RELIGIOUS;
            case "political" -> Flags.POLITICAL;
            case "racist" -> Flags.RACIST;
            case "sexist" -> Flags.SEXIST;
            case "explicit" -> Flags.EXPLICIT;
            default -> throw new IllegalArgumentException("Flag does not exist");
        };
    }

    @JsonValue
    public String getFlag() {
        return flags;
    }

    @Override
    public String toString() {
        return flags;
    }
}
