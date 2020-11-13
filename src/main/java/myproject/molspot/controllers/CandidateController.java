package myproject.molspot.controllers;
import myproject.molspot.models.Candidate;
import myproject.molspot.services.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.ws.rs.core.*;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @Context
    private UriInfo uriInfo;

    @GetMapping("")
    public @ResponseBody ResponseEntity<Object> getAllCandidates() {
        Iterable<Candidate> iCandidate = candidateService.getAllCandidates();
        if (iCandidate != null){
            return new ResponseEntity<>(iCandidate, HttpStatus.OK);
        } else{
            return new ResponseEntity<>("There were no candidates in the database", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<Object> getCandidateById(@PathVariable int id) {
        Optional<Candidate> optCandidate = candidateService.getCandidateById(id);
        if (optCandidate.isPresent()) {
            return new ResponseEntity<>(optCandidate.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Please provide a valid candidate ID", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public @ResponseBody ResponseEntity<Object> deleteUser(@PathVariable int id) {
        Optional<Candidate> optCandidate = candidateService.getCandidateById(id);
        if (optCandidate.isPresent()){
            Candidate candidate = candidateService.deleteCandidate(optCandidate.get());
            return new ResponseEntity<>(candidate, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Candidate with id "+id+" does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/new")
    public @ResponseBody ResponseEntity<Object> createCandidate(@RequestParam String name) {
        if (!name.equals("")){
            Candidate candidate = new Candidate(name);
            Candidate createdCandidate = candidateService.saveCandidate(candidate);
            return new ResponseEntity<>(createdCandidate, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Please enter all the fields", HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{id}")
    public @ResponseBody ResponseEntity<Object> updateCandidate(@PathVariable Integer id, @RequestParam(required = false) String name, @RequestParam(required = false) Boolean isEliminated) {
        Optional<Candidate> optCandidate = candidateService.getCandidateById(id);
        if (optCandidate.isPresent()){
            Candidate candidate = optCandidate.get();
            if (name != null && !name.isEmpty()) { candidate.setName(name); }
            if (isEliminated != null) { candidate.setIsEliminated(isEliminated);}
            Candidate updatedCandidate = candidateService.saveCandidate(candidate);
            return new ResponseEntity<>(updatedCandidate, HttpStatus.OK);
        } else{
            return new ResponseEntity<>("Candidate with id "+id+" does not exist", HttpStatus.NOT_FOUND);
        }
    }
}
