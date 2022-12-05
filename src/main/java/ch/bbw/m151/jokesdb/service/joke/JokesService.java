package ch.bbw.m151.jokesdb.service.joke;

import ch.bbw.m151.jokesdb.datamodel.JokeDTO;
import ch.bbw.m151.jokesdb.datamodel.JokesEntity;
import ch.bbw.m151.jokesdb.datamodel.enums.Categories;
import ch.bbw.m151.jokesdb.datamodel.enums.Flags;
import ch.bbw.m151.jokesdb.datamodel.enums.JokeTypes;
import ch.bbw.m151.jokesdb.repository.JokesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class JokesService {

    private static final Logger log = LoggerFactory.getLogger(JokesService.class);

    private final JokesRepository jokesRepository;

    public JokesService(JokesRepository jokesRepository) {
        this.jokesRepository = jokesRepository;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void preloadDB() {
        RemoteJokesService remoteJokesService = new RemoteJokesService();
        JokeDTO jokeDTO = remoteJokesService.getJokes(null, null, null, null, null, 10);

        if (jokeDTO.getJokes() != null) {
            List<JokesEntity> entityList = new ArrayList<>();
            for (JokeDTO joke : jokeDTO.getJokes()) {
                entityList.add(mapDtoToEntity(joke));
            }
            jokesRepository.saveAll(entityList);
        } else {
            jokesRepository.save(mapDtoToEntity(jokeDTO));
        }
    }

    public Optional<JokesEntity> getRandomJoke(){
        List<JokesEntity> allJokes = jokesRepository.findAll();
        int maxId = allJokes.size();
        int randomId = (int) Math.floor(Math.random() * maxId + 1);
        return jokesRepository.findById(randomId);
    }

    public JokesEntity mapDtoToEntity(JokeDTO dto) {
        JokesEntity entity = new JokesEntity();
        entity.setJoke(dto.getJoke());
        entity.setSetup(dto.getSetup());
        entity.setDelivery(dto.getDelivery());
        entity.setCategories(Collections.singletonList(Categories.fromString(dto.getCategory())));
        entity.setLanguages(dto.getLang());
        List<Flags> flagArray = new ArrayList<>();
        if (dto.getFlags().isExplicit()) {
            flagArray.add(Flags.EXPLICIT);
        }
        if (dto.getFlags().isNsfw()) {
            flagArray.add(Flags.NSFW);
        }
        if (dto.getFlags().isPolitical()) {
            flagArray.add(Flags.POLITICAL);
        }
        if (dto.getFlags().isSexist()) {
            flagArray.add(Flags.SEXIST);
        }
        if (dto.getFlags().isRacist()) {
            flagArray.add(Flags.RACIST);
        }
        if (dto.getFlags().isReligious()) {
            flagArray.add(Flags.RELIGIOUS);
        }
        entity.setFlags(flagArray);
        entity.setJokeType(Collections.singletonList(JokeTypes.fromString(dto.getType())));
        entity.setCreationTimeStamp(LocalDateTime.now());

        return entity;
    }
}
