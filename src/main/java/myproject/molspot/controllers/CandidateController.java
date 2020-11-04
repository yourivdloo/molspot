package myproject.molspot.controllers;
import myproject.molspot.models.Candidate;
import myproject.molspot.repositories.CandidateRepository;
import myproject.molspot.services.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.ws.rs.core.*;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @Context
    private UriInfo uriInfo;
    // this has to be static because the service is stateless:
    //private final FakeDataStore fakeDataStore = FakeDataStore.getInstance();

    public int getIterableSize(Iterable<Candidate> candidates){
        int size = 0;

        for(Candidate candidate : candidates){
            size++;
        }

        return size;
    }

    @GetMapping("") //GET at http://localhost:XXXX/candidates
    public @ResponseBody ResponseEntity<Object> getAllCandidates() {
        return candidateService.getAllCandidates();
    }

    @GetMapping("/{id}") //GET at http://localhost:XXXX/candidates/3
    public @ResponseBody ResponseEntity<Object> getCandidateById(@PathVariable int id) {
        return candidateService.getCandidateById(id);
    }

    @DeleteMapping("/{id}") //DELETE at http://localhost:XXXX/candidates/3
    public @ResponseBody ResponseEntity<Object> deleteUser(@PathVariable int id) {
        return candidateService.deleteUser(id);
    }

    @PostMapping("/new") //POST at http://localhost:XXXX/candidates/new
    public @ResponseBody ResponseEntity<Object> createCandidate(@RequestParam String name) {
        return candidateService.createCandidate(name);
    }

    @PutMapping("/{id}") //PUT at http://localhost:XXXX/candidates/{id}
    public @ResponseBody ResponseEntity<Object> updateCandidate(@PathVariable Integer id, @RequestParam(required = false) String name, @RequestParam(required = false) Boolean isEliminated) {
        return candidateService.updateCandidate(id, name, isEliminated);
    }
}
