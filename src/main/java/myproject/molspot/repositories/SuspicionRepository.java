package myproject.molspot.repositories;
import myproject.molspot.models.Suspicion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface SuspicionRepository extends CrudRepository<Suspicion, Integer> {
    Iterable<Suspicion>findAllByUserId(int id);
}
