package myproject.molspot.services;

import myproject.molspot.exceptions.BadRequestException;
import myproject.molspot.exceptions.NotFoundException;
import myproject.molspot.models.Candidate;
import myproject.molspot.models.Episode;
import myproject.molspot.repositories.EpisodeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@SpringBootTest
class EpisodeServiceTest {

    @Mock
    EpisodeRepository episodeRepository;

    @InjectMocks
    EpisodeService episodeService;

    @Test
    void getEpisodeById(){
        //Arrange
        int id = 4;
        LocalDateTime startDate = LocalDateTime.now();
        Episode episode = new Episode(startDate);
        episode.setId(id);

        when(episodeRepository.findById(id)).thenReturn(Optional.of(episode));
        //Act
        Episode actual = episodeService.getEpisodeById(id);
        //Assert
        assertEquals(episode, actual);
    }

    @Test
    void getEpisodeById_Not_found(){
        int id = 0;

        when(episodeRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () ->{
            episodeService.getEpisodeById(id);
        });
    }

    @Test
    void getAllEpisodes(){
        //Arrange
        Episode e1 = new Episode(LocalDateTime.now().minusWeeks(3));
        Episode e2 = new Episode(LocalDateTime.now().minusWeeks(2));
        Episode e3 = new Episode(LocalDateTime.now().minusWeeks(1));
        Episode e4 = new Episode(LocalDateTime.now());
        Iterable<Episode> episodes = new ArrayList<>(){{add(e1); add(e2); add(e3); add(e4);}};

        when(episodeRepository.findAll()).thenReturn(episodes);
        //Act
        Iterable<Episode> actual = episodeService.getAllEpisodes();
        //Assert
        assertEquals(episodes, actual);
    }

    @Test
    void getAllEpisodes_Not_found(){
        Episode[] episodes = {};
        Iterable<Episode> iEpisodes = Arrays.asList(episodes);
        when(episodeRepository.findAll()).thenReturn(iEpisodes);
        Assertions.assertThrows(NotFoundException.class, () ->{
            episodeService.getAllEpisodes();
        });
    }

    @Test
    void saveEpisode(){
        //Arrange
        Episode episode = new Episode(LocalDateTime.now().plusWeeks(1));
        when(episodeRepository.save(episode)).thenReturn(episode);
        //Act
        Episode actual = episodeService.saveEpisode(episode);
        //Assert
        assertEquals(episode, actual);
    }

    @Test
    void saveEpisode_Wrong_date(){
        Episode episode = new Episode(LocalDateTime.now().minusWeeks(1));
        when(episodeRepository.save(episode)).thenReturn(episode);

        Assertions.assertThrows(BadRequestException.class, () ->{
            episodeService.saveEpisode(episode);
        });
    }

    @Test
    void deleteEpisode(){
        //Arrange
        Episode episode = new Episode(LocalDateTime.now());
        int id = 1;
        episode.setId(1);
        when(episodeRepository.findById(id)).thenReturn(Optional.of(episode));
        //Act
        Episode deleted = episodeService.deleteEpisode(id);
        //Assert
        assertEquals(episode, deleted);
    }

    @Test
    void deleteEpisode_Not_found(){
        Episode episode = new Episode(LocalDateTime.now());
        int id = 1;
        episode.setId(1);
        when(episodeRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () ->{
            episodeService.deleteEpisode(id);
        });
    }
}