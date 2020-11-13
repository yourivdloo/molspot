package myproject.molspot.repositories;

import myproject.molspot.models.Episode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EpisodeRepository extends CrudRepository<Episode, Integer> {
}
