package ch.bbw.m151.jokesdb.controller;

import ch.bbw.m151.jokesdb.datamodel.JokesEntity;
import ch.bbw.m151.jokesdb.repository.JokesRepository;
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
class JokesControllerTest implements WithAssertions {

    @Autowired
    JokesRepository jokesRepository;
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void getOneJoke() {
        webTestClient.post().uri("/loadNewJokes?categories=Programming&flags=nsfw,political&jokeTypes=twopart&language=es&searchString=&amount=1").exchange();
        WebTestClient.ResponseSpec entity = webTestClient.post().uri("/getOneJoke").exchange();
        assertThat(entity).isNotNull();
    }

    @Test
    void getAllJokes() {
        jokesRepository.deleteAll();
        webTestClient.get().uri("/jokes").exchange()
                .expectBodyList(JokesEntity.class)
                .hasSize((int) jokesRepository.count());

    }

    @Test
    void loadNewJokes() {
        jokesRepository.deleteAll();
        webTestClient.post().uri("/loadNewJokes?categories=Programming&flags=nsfw,political&jokeTypes=twopart&language=es&searchString=&amount=3").exchange();
        assertThat(jokesRepository.count()).isEqualTo(3);
    }

}
