package myproject.molspot.services;

import myproject.molspot.exceptions.NotFoundException;
import myproject.molspot.models.Episode;
import myproject.molspot.repositories.EpisodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Service
public class EpisodeService {

    @Autowired
    private EpisodeRepository episodeRepository;

    public Iterable<Episode> getAllEpisodes() { return episodeRepository.findAll(); }

    public Episode getEpisodeById(int id) {
        Optional<Episode> episode = episodeRepository.findById(id);
        if (episode.isPresent()){
            return episode.get();
        } else {
            throw new NotFoundException("There were no episodes in the database");
        }
    }

    public @ResponseBody Episode deleteEpisode(Episode episode) {
        episodeRepository.delete(episode);
        return episode;
    }

    public @ResponseBody Episode saveEpisode(Episode episode) {
        return episodeRepository.save(episode);
    }
}
