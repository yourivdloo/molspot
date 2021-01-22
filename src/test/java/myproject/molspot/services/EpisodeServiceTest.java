package myproject.molspot.services;

import myproject.molspot.exceptions.BadRequestException;
import myproject.molspot.exceptions.NotFoundException;
import myproject.molspot.models.Candidate;
import myproject.molspot.models.Episode;
import myproject.molspot.models.Suspicion;
import myproject.molspot.models.User;
import myproject.molspot.repositories.EpisodeRepository;
import myproject.molspot.repositories.SuspicionRepository;
import myproject.molspot.repositories.UserRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@SpringBootTest
class EpisodeServiceTest {

    @Mock
    EpisodeRepository episodeRepository;

    @Mock
    UserService userService;

    @Mock
    SuspicionService suspicionService;

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

        Assertions.assertThrows(NotFoundException.class, () ->
            episodeService.getEpisodeById(id)
        );
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
        Assertions.assertThrows(NotFoundException.class, () ->
            episodeService.getAllEpisodes()
        );
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

        Assertions.assertThrows(BadRequestException.class, () ->
            episodeService.saveEpisode(episode)
        );
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

        Assertions.assertThrows(NotFoundException.class, () ->
            episodeService.deleteEpisode(id)
        );
    }

    @Test
    void getCurrentEpisode(){
        Episode e1 = new Episode(LocalDateTime.now().minusWeeks(2));
        e1.setHasEnded(true);
        Episode e2 = new Episode(LocalDateTime.now().plusDays(1));
        Episode e3 = new Episode(LocalDateTime.now().plusWeeks(1));
        Iterable<Episode> iEpisode = new ArrayList<>(){{add(e1); add(e2); add(e3);}};

        when(episodeRepository.findAll()).thenReturn(iEpisode);

        Episode actual = episodeService.getCurrentEpisode();
        assertEquals(e2, actual);
    }

//    @Test
//    void endEpisode(){
//        //Arrange
//        User expectedU1 = new User("testuser1", "test1@gmail.com", "12345");
//        expectedU1.setId(1);
//        expectedU1.setPoints(100);
//
//        User u1 = new User("testuser1", "test1@gmail.com", "12345");
//        u1.setId(1);
//        u1.setPoints(200);
//        User u2 = new User("testuser2", "test2@gmail.com", "12345");
//        u1.setId(2);
//        u2.setPoints(200);
//        Iterable<User> users = new ArrayList<>(){{add(u1); add(u2);}};
//        when(userService.getAllUsers()).thenReturn(users);
//
//        Episode episode = new Episode(LocalDateTime.now().minusHours(3));
//
//        Candidate can1 = new Candidate("Fred");
//        can1.setId(1);
//        can1.setIsEliminated(false);
//        Candidate can2 = new Candidate("Peter");
//        can2.setId(2);
//        can2.setIsEliminated(false);
//        Candidate can3 = new Candidate("Ingrid");
//        can3.setId(3);
//        can3.setIsEliminated(true);
//
//        Suspicion sus1 = new Suspicion(u1,can1,episode, 50);
//        Suspicion sus2 = new Suspicion(u1,can3,episode, 150);
//        Iterable<Suspicion> suspicionsU1 = new ArrayList<>(){{add(sus1); add(sus2);}};
//
//        Suspicion sus3 = new Suspicion(u2,can2,episode, 200);
//        Iterable<Suspicion> suspicionsU2 = new ArrayList<>(){{add(sus3);}};
//
//        when(suspicionService.getSuspicionsByUser(u1.getId())).thenReturn(suspicionsU1);
//        when(suspicionService.getSuspicionsByUser(u2.getId())).thenReturn(suspicionsU2);
//
//        when(userService.updateUser(any(User.class))).thenReturn(any(User.class));
//
//        //Act
//        Episode endedEpisode = episodeService.endEpisode(episode.getId());
//
//        //Assert
//        assertEquals(true, endedEpisode.getHasEnded());
//    }
}