package myproject.molspot.services;

import myproject.molspot.exceptions.BadRequestException;
import myproject.molspot.exceptions.NotFoundException;
import myproject.molspot.models.Episode;
import myproject.molspot.models.Suspicion;
import myproject.molspot.models.User;
import myproject.molspot.repositories.EpisodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class EpisodeService {

    @Autowired
    private EpisodeRepository episodeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SuspicionService suspicionService;

    public Iterable<Episode> getAllEpisodes() {
        Iterable<Episode> iEpisode = episodeRepository.findAll();
        if (iEpisode.iterator().hasNext()) {
            return iEpisode;
        } else {
            throw new NotFoundException("There were no episodes in the database");
        }
    }

    public Episode getEpisodeById(int id) {
        Optional<Episode> episode = episodeRepository.findById(id);
        if (episode.isPresent()) {
            return episode.get();
        } else {
            throw new NotFoundException("Episode with id " + id + " does not exist");
        }
    }

    public @ResponseBody
    Episode deleteEpisode(int id) {
        Episode episode = getEpisodeById(id);
        episodeRepository.delete(episode);
        return episode;
    }

    public Episode saveEpisode(Episode episode) {
        if (episode.getStartDate().compareTo(LocalDateTime.now()) > 0) {
            return episodeRepository.save(episode);
        } else {
            throw new BadRequestException("You can't create an episode with a startdate in the past");
        }
    }

    public void endEpisode(Episode episode){
        Iterable<User> users = userService.getAllUsers();
        for (User u:users) {
            int points = 0;
            Iterable<Suspicion> allSuspicions = suspicionService.getSuspicionsByUser(u.getId());
            ArrayList<Suspicion> episodeSuspicions = new ArrayList<>();
            for(Suspicion sus:allSuspicions){
                if (sus.getEpisode() == episode){
                    episodeSuspicions.add(sus);
                }
            }
            for(Suspicion sus:episodeSuspicions){
                u.setPoints(u.getPoints() - sus.getAmount());
                if(!sus.getCandidate().getIsEliminated()){
                    points = points + sus.getAmount() * 2;
                }
            }
            u.setPoints(u.getPoints() + points);
            userService.updateUser(u);
        }
    }

    public Episode getCurrentEpisode(){
        Iterable<Episode> episodes = getAllEpisodes();
        Episode currentEpisode = null;
        int i = 0;
        for (Episode e: episodes
             ) {
            if(!e.hasEnded){
                if(i == 0){
                    currentEpisode = e;
                    i++;
                } else{
                    if (e.getStartDate().isBefore(currentEpisode.getStartDate())){
                        currentEpisode = e;
                    }
                }
            }
        }
        return currentEpisode;
    }
}
