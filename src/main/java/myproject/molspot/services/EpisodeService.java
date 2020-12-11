package myproject.molspot.services;

import myproject.molspot.exceptions.BadRequestException;
import myproject.molspot.exceptions.NotFoundException;
import myproject.molspot.models.Episode;
import myproject.molspot.repositories.EpisodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EpisodeService {

    @Autowired
    private EpisodeRepository episodeRepository;

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
            throw new NotFoundException("Episode with id "+id+" does not exist");
        }
    }

    public @ResponseBody
    Episode deleteEpisode(int id) {
        Episode episode = getEpisodeById(id);
        episodeRepository.delete(episode);
        return episode;
    }

    public @ResponseBody
    Episode saveEpisode(Episode episode) {
        if (episode.getStartDate().compareTo(LocalDateTime.now()) > 0) {
            return episodeRepository.save(episode);
        } else {
            throw new BadRequestException("You can't create an episode with a startdate in the past");
        }
    }
}
