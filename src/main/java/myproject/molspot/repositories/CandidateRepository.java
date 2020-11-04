package myproject.molspot.repositories;

import myproject.molspot.models.Candidate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends CrudRepository<Candidate, Integer> {
}
