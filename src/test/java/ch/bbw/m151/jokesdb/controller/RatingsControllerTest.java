package ch.bbw.m151.jokesdb.controller;

import ch.bbw.m151.jokesdb.datamodel.JokesEntity;
import ch.bbw.m151.jokesdb.repository.JokesRepository;
import ch.bbw.m151.jokesdb.repository.RatingRepository;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class RatingsControllerTest implements WithAssertions {

    @Autowired
    RatingRepository repository;
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void rateAJoke() {
        webTestClient.post().uri("/rateJoke?jokeId=1&starRating=1,political&description=KeinGuterWitz").exchange();
        assertThat(repository.findAll()).isNotNull();
    }
}
