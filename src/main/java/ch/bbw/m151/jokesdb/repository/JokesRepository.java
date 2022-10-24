package ch.bbw.m151.jokesdb.repository;

import ch.bbw.m151.jokesdb.datamodel.JokesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JokesRepository extends JpaRepository<JokesEntity, Integer> {

}
