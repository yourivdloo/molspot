package myproject.molspot.controllers;
import myproject.molspot.exceptions.BadRequestException;
import myproject.molspot.models.Candidate;
import myproject.molspot.services.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.*;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
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
            return new ResponseEntity<>(iCandidate, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<Object> getCandidateById(@PathVariable int id) {
        Candidate candidate = candidateService.getCandidateById(id);
            return new ResponseEntity<>(candidate, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public @ResponseBody ResponseEntity<Object> deleteCandidate(@PathVariable int id) {
        Candidate result = candidateService.deleteCandidate(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/new")
    public @ResponseBody ResponseEntity<Object> createCandidate(@RequestParam String name) {
        if (!name.equals("")){
            Candidate candidate = new Candidate(name);
            Candidate result = candidateService.saveCandidate(candidate);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            throw new BadRequestException("Please enter all the fields");
        }
    }

    @PutMapping("/{id}")
    public @ResponseBody ResponseEntity<Object> updateCandidate(@PathVariable Integer id, @RequestParam(required = false) String name, @RequestParam(required = false) Boolean isEliminated) {
        Candidate candidate = candidateService.getCandidateById(id);
            if (name != null && !name.isEmpty()) { candidate.setName(name); }
            if (isEliminated != null) { candidate.setIsEliminated(isEliminated);}
            Candidate result = candidateService.saveCandidate(candidate);
            return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
