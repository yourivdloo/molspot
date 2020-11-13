package myproject.molspot.services;

import myproject.molspot.models.Candidate;
import myproject.molspot.models.Episode;
import myproject.molspot.models.Suspicion;
import myproject.molspot.models.User;
import myproject.molspot.repositories.CandidateRepository;
import myproject.molspot.repositories.EpisodeRepository;
import myproject.molspot.repositories.SuspicionRepository;
import myproject.molspot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Service
public class SuspicionService {

    @Autowired
    private SuspicionRepository suspicionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private EpisodeRepository episodeRepository;

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
            return new ResponseEntity<>(iSuspicion, HttpStatus.OK);
        }
        return new ResponseEntity<>("There were no suspicions in the database for this user", HttpStatus.NOT_FOUND);
    }

    public @ResponseBody ResponseEntity<Object> createSuspicion(Integer userId, Integer candidateId, Integer episodeId, Integer amount){
        Optional<User> optUser = userRepository.findById(userId);
        if (optUser.isPresent()){
            if (amount > 0 && amount <= optUser.get().getPoints()){
                Optional<Candidate> optCandidate = candidateRepository.findById(candidateId);
                if (optCandidate.isPresent()) {
                    Optional<Episode> optEpisode = episodeRepository.findById(episodeId);
                    if (optEpisode.isPresent()) {
                        Suspicion suspicion = new Suspicion(optUser.get(), optCandidate.get(), optEpisode.get(), amount);
                        return new ResponseEntity<>(suspicion, HttpStatus.OK);
                    }
                    return new ResponseEntity<>("Episode with id "+episodeId+" does not exist", HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>("Candidate with id "+candidateId+" does not exist", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>("Please enter a valid amount of points", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("User with id "+userId+" does not exist", HttpStatus.NOT_FOUND);
    }
}
