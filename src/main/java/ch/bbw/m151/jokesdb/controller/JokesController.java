package ch.bbw.m151.jokesdb.controller;

import ch.bbw.m151.jokesdb.datamodel.JokeDTO;
import ch.bbw.m151.jokesdb.datamodel.JokesEntity;
import ch.bbw.m151.jokesdb.datamodel.enums.Categories;
import ch.bbw.m151.jokesdb.datamodel.enums.Flags;
import ch.bbw.m151.jokesdb.datamodel.enums.JokeTypes;
import ch.bbw.m151.jokesdb.datamodel.enums.Languages;
import ch.bbw.m151.jokesdb.repository.JokesRepository;
import ch.bbw.m151.jokesdb.service.joke.JokesService;
import ch.bbw.m151.jokesdb.service.joke.RemoteJokesService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class JokesController {
    Logger logger = LoggerFactory.getLogger(JokesController.class);
    private final JokesRepository jokesRepository;
    @Autowired
    private final RemoteJokesService remoteJokesService;
    @Autowired
    private JokesService jokesService;

    /**
     * Get all jokes from database
     * @param pageable
     * @return randomJokes
     */
    @GetMapping("/jokes")
    public List<JokesEntity> getJokes(Pageable pageable) {
        logger.info("JokesController.getJokes" + LocalDateTime.now());
        return jokesRepository.findAll(pageable).getContent();
    }

    /**
     * Get a random joke from database
     * @return randomJoke
     */
    @CrossOrigin
    @GetMapping("/getOneJoke")
    public Optional<JokesEntity> getJoke() {
        logger.info("JokesController.getOneJoke - " + LocalDateTime.now());
        return jokesService.getRandomJoke();
    }

    /**
     * Load new jokes int database
     * @param categories
     * @param language
     * @param flags
     * @param jokeTypes
     * @param searchString
     * @param amount
     */
    @CrossOrigin
    @PostMapping("/loadNewJokes")
    public void loadNewJokes(@RequestParam("categories") List<String> categories, @RequestParam("language") String language, @RequestParam("flags") List<String> flags, @RequestParam("jokeTypes") List<String> jokeTypes, @RequestParam("searchString") String searchString, @RequestParam("amount") int amount) {
        logger.info("JokesController - loadNewJokes - " + LocalDateTime.now());

        List<Categories> c = new ArrayList<>();
        for (String cat : categories) {
            c.add(Categories.fromString(cat));
        }

        Languages l = Languages.fromString(language);

        List<Flags> f = new ArrayList<>();
        for (String fl : flags) {
            f.add(Flags.fromString(fl));
        }

        List<JokeTypes> jt = new ArrayList<>();
        for (String j : jokeTypes) {
            jt.add(JokeTypes.fromString(j));
        }

        JokeDTO jokeDTO = remoteJokesService.getJokes(c, l, f, jt, searchString, amount);

        if (jokeDTO.getJokes() != null) {
            List<JokesEntity> entityList = new ArrayList<>();
            for (JokeDTO joke : jokeDTO.getJokes()) {
                entityList.add(jokesService.mapDtoToEntity(joke));
            }
            jokesRepository.saveAll(entityList);
        } else {
            jokesRepository.save(jokesService.mapDtoToEntity(jokeDTO));
        }
    }

}
