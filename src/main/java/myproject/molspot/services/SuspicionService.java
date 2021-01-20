package myproject.molspot.services;

import myproject.molspot.exceptions.BadRequestException;
import myproject.molspot.exceptions.NotFoundException;
import myproject.molspot.models.Candidate;
import myproject.molspot.models.Episode;
import myproject.molspot.models.Suspicion;
import myproject.molspot.models.User;
import myproject.molspot.repositories.EpisodeRepository;
import myproject.molspot.repositories.SuspicionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class SuspicionService {

    @Autowired
    private SuspicionRepository suspicionRepository;

    @Autowired
    private EpisodeService episodeService;

    @Autowired
    private UserService userService;

    public Suspicion getSuspicionById(int id){
        Optional<Suspicion> optSuspicion = suspicionRepository.findById(id);
        if (optSuspicion.isPresent()) {
            return optSuspicion.get();
        } else {
            throw new NotFoundException("Suspicion with id " + id + " does not exist");
        }
    }

    public Iterable<Suspicion> getSuspicionsByUser(int userId) {
        Iterable<Suspicion> iSuspicion = suspicionRepository.findAllByUserId(userId);
        Episode episode = episodeService.getCurrentEpisode();
        ArrayList<Suspicion> Suspicions = new ArrayList<>();
        for (Suspicion sus: iSuspicion
             ) {
            if(sus.getEpisode() == episode) {
                Suspicions.add(sus);
            }
        }
        if (iSuspicion.iterator().hasNext()) {
            return Suspicions;
        } else {
            throw new NotFoundException("User with id " + userId + " does not have any suspicions");
        }
    }

    public Suspicion saveSuspicion(Suspicion suspicion) {
        if (suspicion.getAmount() >= 0 && suspicion.getAmount() <= suspicion.getUser().getPoints() && !suspicion.getEpisode().getHasEnded()) {
            User user = suspicion.getUser();
            user.setPoints(user.getPoints() - suspicion.getAmount());
            userService.updateUser(user);
            return suspicionRepository.save(suspicion);
        } else {
            throw new BadRequestException("Please enter a valid amount of points");
        }
    }

    public Suspicion deleteSuspicion(int id){
            Optional<Suspicion> suspicion = suspicionRepository.findById(id);
            suspicionRepository.delete(suspicion.get());
            return suspicion.get();
    }
}
