package ch.bbw.m151.jokesdb.service.rating;

import ch.bbw.m151.jokesdb.datamodel.RatingEntity;
import ch.bbw.m151.jokesdb.repository.JokesRepository;
import ch.bbw.m151.jokesdb.repository.RatingRepository;
import ch.bbw.m151.jokesdb.service.joke.JokesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RatingService {
    private static final Logger log = LoggerFactory.getLogger(JokesService.class);

    private final RatingRepository ratingRepository;
    private final JokesRepository jokesRepository;

    public RatingService(RatingRepository ratingRepository, JokesRepository jokesRepository) {
        this.ratingRepository = ratingRepository;
        this.jokesRepository = jokesRepository;
    }

    public void addRating(int id, int starRating, String description) {
        RatingEntity ratingEntity = new RatingEntity();
        ratingEntity.setJoke(jokesRepository.getReferenceById(id));
        ratingEntity.setStarRating(starRating);
        ratingEntity.setDescription(description);
        ratingRepository.save(ratingEntity);
    }
}
