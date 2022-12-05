package ch.bbw.m151.jokesdb.controller;

import ch.bbw.m151.jokesdb.repository.RatingRepository;
import ch.bbw.m151.jokesdb.service.rating.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RatingsController {

    @Autowired
    private final RatingRepository ratingRepository;

    @Autowired
    private final RatingService ratingService;

    /**
     * Add a rating to a joke
     * @param jokeId - OneToOne foreign key to joke
     * @param starRating
     * @param description
     */
    @CrossOrigin
    @PostMapping("/rateJoke")
    public void rateAJoke(@RequestParam("jokeId") int jokeId, @RequestParam("starRating") int starRating, @RequestParam("description") String description){
        ratingService.addRating(jokeId, starRating, description);
    }
}
