package myproject.molspot.services;

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
        return candidateRepository.findAll();
    }

    public Optional<Candidate> getCandidateById(int id) {
        return candidateRepository.findById(id);
    }

    public Candidate deleteCandidate(Candidate candidate) {
        candidateRepository.delete(candidate);
        return candidate;
    }

    public @ResponseBody Candidate saveCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }
}
