package ch.bbw.m151.jokesdb.datamodel.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum Languages {
    CZECH("cs"),
    GERMAN("de"),
    ENGLISH("en"),
    SPANISH("es"),
    FRENCH("fr"),
    PORTUGUESE("pt");

    @JsonGetter
    public static Languages fromString(String language) {
        return switch (language) {
            case "cs" -> Languages.CZECH;
            case "de" -> Languages.GERMAN;
            case "en" -> Languages.ENGLISH;
            case "es" -> Languages.SPANISH;
            case "fr" -> Languages.FRENCH;
            case "pt" -> Languages.PORTUGUESE;
            default -> throw new IllegalArgumentException("Language does not exist");
        };
    }

    private final String language;

    Languages(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    @Override
    public String toString() {
        return language;
    }
}
