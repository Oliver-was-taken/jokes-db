package ch.bbw.m151.jokesdb.datamodel;

import lombok.Data;

import java.util.List;

@Data
public class JokeDTO {
    private List<JokeDTO> jokes;
    private boolean error;
    private String category;
    private String type;
    private String setup;
    private String delivery;
    private String joke;
    private FlagsDTO flags;
    private int id;
    private boolean safe;
    private String lang;
}
