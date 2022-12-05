package ch.bbw.m151.jokesdb.datamodel;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.query.criteria.internal.expression.function.AggregationFunction;

import javax.persistence.*;

@Entity
@Table(name = "ratings")
@Getter
@Setter
public class RatingEntity {
    @Id
    private int id;
    @OneToOne @MapsId
    private JokesEntity joke;
    private int starRating;
    private String description;
}
