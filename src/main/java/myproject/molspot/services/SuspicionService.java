package myproject.molspot.services;

import myproject.molspot.models.Candidate;
import myproject.molspot.models.Suspicion;
import myproject.molspot.models.User;
import myproject.molspot.repositories.CandidateRepository;
import myproject.molspot.repositories.SuspicionRepository;
import myproject.molspot.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Service
public class SuspicionService {

    private SuspicionRepository suspicionRepository;
    private UserRepository userRepository;
    private CandidateRepository candidateRepository;

    private int getIterableSize(Iterable<Suspicion> suspicions){
        int size = 0;

        for(Suspicion suspicion: suspicions){
            size++;
        }

        return size;
    }

    public @ResponseBody ResponseEntity<Object> getSuspicionsByUser(int userId) {
        Iterable<Suspicion> iSuspicion = suspicionRepository.findAllByUserId(userId);
        if (getIterableSize(iSuspicion)>0){
            return new ResponseEntity<>(iSuspicion, HttpStatus.FOUND);
        }
        return new ResponseEntity<>("There were no suspicions in the database for this user", HttpStatus.NOT_FOUND);
    }

    public @ResponseBody ResponseEntity<Object> CreateSuspicion(Integer userId, Integer candidateId, Integer amount){
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()){
            if (amount > 0 && amount <= user.get().getPoints()){
                Optional<Candidate> candidate = candidateRepository.findById(candidateId);
                if (candidate.isPresent()) {
                    Suspicion suspicion = new Suspicion(user.get(), candidate.get(), amount);
                    return new ResponseEntity<>(suspicion, HttpStatus.CREATED);
                }
                return new ResponseEntity<>("Candidate with id "+candidateId+" does not exist", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>("Please enter a valid amount of points", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("User with id "+userId+" does not exist", HttpStatus.NOT_FOUND);
    }
}
