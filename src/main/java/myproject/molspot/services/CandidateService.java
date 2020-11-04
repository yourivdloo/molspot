package myproject.molspot.services;

import myproject.molspot.models.Candidate;
import myproject.molspot.models.User;
import myproject.molspot.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    private int getIterableSize(Iterable<Candidate> candidates){
        int size = 0;

        for(Candidate candidate : candidates){
            size++;
        }

        return size;
    }

    public @ResponseBody ResponseEntity<Object> getAllCandidates() {
        Iterable<Candidate> iCandidate= candidateRepository.findAll();
        if (getIterableSize(iCandidate) > 0){
            return new ResponseEntity<>(iCandidate, HttpStatus.FOUND);
        }
        return new ResponseEntity<>("There were no candidates in the database", HttpStatus.NOT_FOUND);
    }

    public @ResponseBody ResponseEntity<Object> getCandidateById(int id) {
        Optional<Candidate> candidate = candidateRepository.findById(id);
        if (candidate.isPresent()) {
            return new ResponseEntity<>(candidate.get(), HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>("Please provide a valid candidate ID", HttpStatus.NOT_FOUND);
        }
    }

    public @ResponseBody ResponseEntity<Object> deleteUser(int id) {
        Optional<Candidate> candidate = candidateRepository.findById(id);
        if (candidate.isPresent()){
            candidateRepository.delete(candidate.get());
            return new ResponseEntity<>(candidate, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Candidate with id "+id+" does not exist", HttpStatus.NOT_FOUND);
        }
    }

    public @ResponseBody ResponseEntity<Object> createCandidate(String name) {
        if (!name.equals("")){
            Candidate candidate = new Candidate(name);
            Candidate createdCandidate = candidateRepository.save(candidate);
            return new ResponseEntity<>(createdCandidate,HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Please enter all the fields", HttpStatus.CONFLICT);
        }
    }

    public @ResponseBody ResponseEntity<Object> updateCandidate(Integer id, String name, Boolean isEliminated) {
        Optional<Candidate> optCandidate = candidateRepository.findById(id);
        if (optCandidate.isPresent()){
            Candidate candidate = optCandidate.get();
            if (name != null && !name.isEmpty()) { candidate.setName(name); }
            if (isEliminated != null) { candidate.setIsEliminated(isEliminated);}
            Candidate updatedCandidate = candidateRepository.save(candidate);
            return new ResponseEntity<>(updatedCandidate, HttpStatus.OK);
        } else{
            return new ResponseEntity<>("Candidate with id "+id+" does not exist", HttpStatus.NOT_FOUND);
        }
    }
}
