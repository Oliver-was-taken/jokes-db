package ch.bbw.m151.jokesdb.service.joke;

import ch.bbw.m151.jokesdb.datamodel.JokeDTO;
import ch.bbw.m151.jokesdb.datamodel.enums.Categories;
import ch.bbw.m151.jokesdb.datamodel.enums.Flags;
import ch.bbw.m151.jokesdb.datamodel.enums.JokeTypes;
import ch.bbw.m151.jokesdb.datamodel.enums.Languages;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class RemoteJokesService {

    private static final Logger log = LoggerFactory.getLogger(JokesService.class);

    public JokeDTO getJokes(@RequestParam List<Categories> categories, @RequestParam Languages language, @RequestParam List<Flags> flags, @RequestParam List<JokeTypes> jokeType, @RequestParam String searchString, @RequestParam int amount) {
        WebClient client = WebClient.create("https://v2.jokeapi.dev");

        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = client.method(HttpMethod.GET);

        WebClient.RequestBodySpec bodySpec = uriSpec.uri(buildUri(categories, language, flags, jokeType, searchString, amount));

        WebClient.RequestHeadersSpec headersSpec = bodySpec.body(
                BodyInserters.fromPublisher(Mono.just("data"), String.class)
        );

        Mono<JokeDTO> response = headersSpec.retrieve()
                .bodyToMono(JokeDTO.class);
        return response.block();
    }

    private String buildUri(List<Categories> categories, Languages language, List<Flags> flags, List<JokeTypes> jokeType, String searchString, int amount) {
        log.info("Build new uri for calling API with parameters - categories: " + categories + ", language: " + language + ", flags: " + flags + ", jokeType: " + jokeType + ", searchString: " + searchString + ", amount: " + amount);
        StringBuilder uri = new StringBuilder();
        //Standard URI
        uri.append("/joke/");

        // Add categories to URI
        if (categories != null) {
            for (Categories c : categories) {
                boolean hasAlreadyCategory = (uri.charAt(uri.length() - 1) != '/');
                if (hasAlreadyCategory) {
                    uri.append(',');
                }
                uri.append(c.getCategory());
            }
        } else {
            uri.append("Any");
        }
        uri.append('?');
        //Add language to URI
        if (language != null && language != Languages.ENGLISH) {
            uri.append("lang=").append(language.getLanguage());
        }

        //add flags
        if (flags != null) {
            boolean isFirstArgument = (uri.charAt(uri.length() - 1) == '?');
            if (!isFirstArgument) {
                uri.append('&');
            }
            uri.append("blacklistFlags=");

            for (Flags f : flags) {
                boolean hasAlreadyFlags = (uri.charAt(uri.length() - 1) != '=');
                if (hasAlreadyFlags) {
                    uri.append(',');
                }

                uri.append(f.getFlag());
            }
        }

        //joke type
        List<JokeTypes> allTypesArray = new ArrayList<>();
        allTypesArray.add(JokeTypes.SINGLE);
        allTypesArray.add(JokeTypes.TWO_PART);
        if (jokeType != null && jokeType != allTypesArray) {
            boolean isFirstArgument = (uri.charAt(uri.length() - 1) == '?');
            if (!isFirstArgument) {
                uri.append('&');
            }
            uri.append("type=");

            for (JokeTypes j : jokeType) {
                uri.append(j);
            }
        }

        //searchString
        if (searchString != null) {
            boolean isFirstArgument = (uri.charAt(uri.length() - 1) == '?');
            if (!isFirstArgument) {
                uri.append('&');
            }
            uri.append("contains=");
            uri.append(searchString);
        }

        //amount
        if (amount > 1 && amount <= 10) {
            boolean isFirstArgument = (uri.charAt(uri.length() - 1) == '?');
            if (!isFirstArgument) {
                uri.append('&');
            }
            uri.append("amount=");
            uri.append(amount);
        }

        return uri.toString();
    }
}
