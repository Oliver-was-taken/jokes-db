package ch.bbw.m151.jokesdb.datamodel;

import ch.bbw.m151.jokesdb.datamodel.enums.Categories;
import ch.bbw.m151.jokesdb.datamodel.enums.Flags;
import ch.bbw.m151.jokesdb.datamodel.enums.JokeTypes;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "jokes")
@Getter
@Setter
public class JokesEntity {
    @Id
    @GeneratedValue
    private int id;
    @Column(length = 500)
    private String joke;
    private String setup;
    private String delivery;
    private String categories;
    private String languages;
    private String flags;
    private String jokeType;
    private String searchString;
    private LocalDateTime creationTimeStamp;

    public void setCategories(List<Categories> categories) {
        this.categories = categories.stream().map(Categories::getCategory).collect(Collectors.joining(","));
    }

    public void setFlags(List<Flags> flags) {
        this.flags = flags.stream().map(Flags::getFlag).collect(Collectors.joining(","));
    }

    public void setJokeType(List<JokeTypes> jokeTypes) {
        this.jokeType = jokeTypes.stream().map(JokeTypes::getJokeType).collect(Collectors.joining(","));
    }
}
