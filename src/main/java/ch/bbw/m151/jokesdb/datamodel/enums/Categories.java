package ch.bbw.m151.jokesdb.datamodel.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Optional;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum Categories {
    PROGRAMMING("Programming"),
    MISC("Misc"),
    DARK("Dark"),
    PUN("Pun"),
    SPOOKY("Spooky"),
    CHRISTMAS("Christmas");

    private final String category;

    Categories(String category) {
        this.category = category;
    }

    @JsonGetter
    public static Categories fromString(String category) {
        return switch (category) {
            case "Programming" -> Categories.PROGRAMMING;
            case "Misc" -> Categories.MISC;
            case "Dark" -> Categories.DARK;
            case "Pun" -> Categories.PUN;
            case "Spooky" -> Categories.SPOOKY;
            case "Christmas" -> Categories.CHRISTMAS;
            default -> throw new IllegalArgumentException("Category does not exist");
        };
    }

    @JsonValue
    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return category;
    }
}
