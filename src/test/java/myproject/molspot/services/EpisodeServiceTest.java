package myproject.molspot.services;

import myproject.molspot.models.Episode;
import myproject.molspot.repositories.EpisodeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class EpisodeServiceTest {

    @Mock
    EpisodeRepository episodeRepository;

    @InjectMocks
    EpisodeService episodeService;

    @Test
    void getEpisodeById(){
//        //Arrange
//        int id = 4;
//        LocalDateTime startDate = LocalDateTime.now();
//        Episode episode = new Episode(startDate);
//        episode.setId(id);
//
//        when(episodeRepository.findById(4)).thenReturn(Optional.of(episode));
//        //Act
//        Episode actual = episodeService.getEpisodeById(id);
//        //Assert
//        assertEquals(episode, actual);
    }

}