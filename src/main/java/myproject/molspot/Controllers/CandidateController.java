package myproject.molspot.Controllers;

import myproject.molspot.Models.Candidate;
import myproject.molspot.Repositories.CandidateRepository;
import myproject.molspot.Repositories.FakeDataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/candidates")
public class CandidateController {

    @Autowired
    private CandidateRepository candidateRepository;

    @Context
    private UriInfo uriInfo;
    // this has to be static because the service is stateless:
    private final FakeDataStore fakeDataStore = FakeDataStore.getInstance();

    @GetMapping("/hello") //GET at http://localhost:XXXX/candidates/hello
    public @ResponseBody String sayHello() {
        return "Hello, your resource works!";
    }

    public int getIterableSize(Iterable<Candidate> candidates){
        int size = 0;

        for(Candidate candidate : candidates){
            size++;
        }

        return size;
    }

    @GetMapping("") //GET at http://localhost:XXXX/candidates
    public @ResponseBody ResponseEntity<Iterable<Candidate>> getAllCandidates() {

        Iterable<Candidate> iCandidate= candidateRepository.findAll();
        if (getIterableSize(iCandidate) > 0){
            return new ResponseEntity<Iterable<Candidate>>(iCandidate, HttpStatus.FOUND);
        }
        return new ResponseEntity("There were no candidates in the database", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}") //GET at http://localhost:XXXX/candidates/3
    public @ResponseBody ResponseEntity<Candidate> getCandidatePath(@PathVariable int id) {
        Optional<Candidate> candidate = candidateRepository.findById(id);
        if (candidate.isPresent()) {
            return new ResponseEntity<Candidate>(candidate.get(), HttpStatus.FOUND);
        } else {
            return new ResponseEntity("Please provide a valid candidate ID", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}") //DELETE at http://localhost:XXXX/candidates/3
    public @ResponseBody ResponseEntity deleteUser(@PathVariable int id) {
        Optional<Candidate> candidate = candidateRepository.findById(id);
        if (candidate.isPresent()){
            candidateRepository.delete(candidate.get());
            return new ResponseEntity("Candidate["+candidate+" successfully deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity("Candidate with id "+id+" does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/new") //POST at http://localhost:XXXX/candidates/new
    public @ResponseBody ResponseEntity<Candidate> createCandidate(@RequestParam String name) {
        if (!name.equals("")){
            Candidate candidate = new Candidate(name);
            Candidate createdCandidate = candidateRepository.save(candidate);
            return new ResponseEntity(createdCandidate,HttpStatus.CREATED);
        } else {
            return new ResponseEntity("Please enter all the fields", HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{id}") //PUT at http://localhost:XXXX/candidates/{id}
    public @ResponseBody ResponseEntity<Candidate> updateCandidate(@PathVariable Integer id, @RequestParam(required = false) String name, @RequestParam(required = false) Boolean isEliminated) {
        Optional<Candidate> optCandidate = candidateRepository.findById(id);
        if (optCandidate.isPresent()){
            Candidate candidate = optCandidate.get();
            if (name != null && !name.isEmpty()) { candidate.setName(name); }
            if (isEliminated != null) { candidate.setIsEliminated(isEliminated);}
            Candidate updatedCandidate = candidateRepository.save(candidate);
            return new ResponseEntity(updatedCandidate, HttpStatus.OK);
        } else{
            return new ResponseEntity("Candidate with id "+id+" does not exist", HttpStatus.NOT_FOUND);
        }

    }


}
