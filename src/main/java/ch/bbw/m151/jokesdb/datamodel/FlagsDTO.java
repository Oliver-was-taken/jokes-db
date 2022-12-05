package ch.bbw.m151.jokesdb.datamodel;

import lombok.Data;

@Data
public class FlagsDTO {
    private boolean nsfw;
    private boolean religious;
    private boolean political;
    private boolean racist;
    private boolean sexist;
    private boolean explicit;
}
