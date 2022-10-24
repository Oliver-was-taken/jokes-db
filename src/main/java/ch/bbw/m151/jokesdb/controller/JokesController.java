package ch.bbw.m151.jokesdb.controller;

import java.util.List;

import ch.bbw.m151.jokesdb.datamodel.JokesEntity;
import ch.bbw.m151.jokesdb.repository.JokesRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JokesController {

	private final JokesRepository jokesRepository;

	public JokesController(JokesRepository jokesRepository) {
		this.jokesRepository = jokesRepository;
	}

	/**
	 * @param pageable to be called with params `?page=3&size=5`
	 * @return hilarious content
	 */
	@GetMapping("/jokes")
	public List<JokesEntity> getJokes(Pageable pageable) {
		return jokesRepository.findAll(pageable)
				.getContent();
	}
}
