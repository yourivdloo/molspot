package myproject.molspot.services;

import myproject.molspot.exceptions.NotFoundException;
import myproject.molspot.models.Candidate;
import myproject.molspot.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    public Iterable<Candidate> getAllCandidates() {
        Iterable<Candidate> iCandidate = candidateRepository.findAll();
        if (iCandidate.iterator().hasNext()){
            return iCandidate;
        } else{
            throw new NotFoundException("There were no candidates in the database");
        }
    }

    public Candidate getCandidateById(int id) {
        Optional<Candidate> optCandidate = candidateRepository.findById(id);
        if (optCandidate.isPresent()) {
            return optCandidate.get();
        } else {
            throw new NotFoundException("Candidate with id "+id+" does not exist");
        }
    }

    public Candidate deleteCandidate(int id) {
        Candidate candidate = getCandidateById(id);
        candidateRepository.delete(candidate);
        return candidate;
    }

    public @ResponseBody Candidate saveCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }
}
