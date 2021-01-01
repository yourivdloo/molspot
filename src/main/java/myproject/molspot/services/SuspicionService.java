package myproject.molspot.services;

import myproject.molspot.exceptions.BadRequestException;
import myproject.molspot.exceptions.NotFoundException;
import myproject.molspot.models.Suspicion;
import myproject.molspot.repositories.SuspicionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SuspicionService {

    @Autowired
    private SuspicionRepository suspicionRepository;

    public Iterable<Suspicion> getSuspicionsByUser(int userId) {
        Iterable<Suspicion> iSuspicion = suspicionRepository.findAllByUserId(userId);
        if (iSuspicion.iterator().hasNext()) {
            return iSuspicion;
        } else {
            throw new NotFoundException("User with id " + userId + " does not have any suspicions");
        }
    }

    public Suspicion saveSuspicion(Suspicion suspicion) {
        if (suspicion.getAmount() > 0 && suspicion.getAmount() <= suspicion.getUser().getPoints() && !suspicion.getEpisode().getHasEnded()) {
            return suspicionRepository.save(suspicion);
        } else {
            throw new BadRequestException("Please enter a valid amount of points");
        }
    }
}
