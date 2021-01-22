package myproject.molspot.services;

import myproject.molspot.exceptions.BadRequestException;
import myproject.molspot.exceptions.NotFoundException;
import myproject.molspot.models.Candidate;
import myproject.molspot.models.Episode;
import myproject.molspot.models.Suspicion;
import myproject.molspot.models.User;
import myproject.molspot.repositories.SuspicionRepository;
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
class SuspicionServiceTest {

    @Mock
    SuspicionRepository suspicionRepository;

    @Mock
    EpisodeService episodeService;

    @Mock
    UserService userService;

    @InjectMocks
    SuspicionService suspicionService;

    @Test
    void saveSuspicion(){
        //Arrange
        User user = new User("Molspotter", "Molspotter@gmail.com", "Testwachtwoord123");
        user.setId(1);
        Candidate candidate = new Candidate("Peter");
        candidate.setId(1);
        Episode episode = new Episode(LocalDateTime.now().plusDays(5));
        episode.setId(1);
        int amount = 200;
        user.setPoints(amount);
        Suspicion suspicion = new Suspicion(user, candidate, episode, amount);

        when(suspicionRepository.save(suspicion)).thenReturn(suspicion);
        when(userService.updateUser(user)).thenReturn(user);
        //Act
        Suspicion actual = suspicionService.saveSuspicion(suspicion);
        //Assert
        assertEquals(suspicion, actual);
    }

    @Test
    void saveSuspicion_Not_enough_points(){
        User user = new User("Molspotter", "Molspotter@gmail.com", "Testwachtwoord123");
        user.setPoints(100);
        Candidate candidate = new Candidate("Peter");
        Episode episode = new Episode(LocalDateTime.now().plusDays(5));
        int amount = 200;
        Suspicion suspicion = new Suspicion(user, candidate, episode, amount);

        Assertions.assertThrows(BadRequestException.class, () ->
            suspicionService.saveSuspicion(suspicion)
        );
    }

    @Test
    void saveSuspicion_Negative_amount(){
        User user = new User("Molspotter", "Molspotter@gmail.com", "Testwachtwoord123");
        user.setPoints(100);
        Candidate candidate = new Candidate("Peter");
        Episode episode = new Episode(LocalDateTime.now());
        int amount = -200;
        Suspicion suspicion = new Suspicion(user, candidate, episode, amount);

        Assertions.assertThrows(BadRequestException.class, () ->
            suspicionService.saveSuspicion(suspicion)
        );
    }

    @Test
    void getSuspicionsByUser(){
        //Arrange
        int userId = 5;
        User user = new User("Molspotter", "Molspotter@gmail.com", "Testwachtwoord123");
        Episode episode = new Episode(LocalDateTime.now());
        Candidate c1 = new Candidate("Peter");
        Candidate c2 = new Candidate("Saskia");
        Candidate c3 = new Candidate("Danilo");

        Suspicion sus1 = new Suspicion(user, c1, episode, 50);
        Suspicion sus2 = new Suspicion(user, c2, episode, 100);
        Suspicion sus3 = new Suspicion(user, c3, episode, 50);
        Iterable<Suspicion> iSuspicion = new ArrayList<>() {{add(sus1); add(sus2); add(sus3);}};
        when(suspicionRepository.findAllByUserId(userId)).thenReturn(iSuspicion);
        when(episodeService.getCurrentEpisode()).thenReturn(episode);
        //Act
        Iterable<Suspicion> actual = suspicionService.getSuspicionsByUser(userId);
        //Assert
        assertEquals(iSuspicion, actual);
    }

    @Test
    void getSuspicionsByUser_Not_found(){
        Suspicion[] suspicions = {};
        Episode episode = new Episode(LocalDateTime.now().plusDays(5));
        Iterable<Suspicion> iSuspicion = Arrays.asList(suspicions);
        int id = 0;
        when(suspicionRepository.findAllByUserId(id)).thenReturn(iSuspicion);
        when(episodeService.getCurrentEpisode()).thenReturn(episode);
        Assertions.assertThrows(NotFoundException.class, () ->
            suspicionService.getSuspicionsByUser(id)
        );
    }

    @Test
    void getSuspicionById(){
        //Arrange
        int id = 1;
        User user = new User("Molspotter", "Molspotter@gmail.com", "Testwachtwoord123");
        Episode e = new Episode(LocalDateTime.now());
        Candidate c = new Candidate("Peter");
        Suspicion sus = new Suspicion(user, c, e, 100);
        sus.setId(id);

        when(suspicionRepository.findById(id)).thenReturn(Optional.of(sus));
        //Act
        Suspicion actual = suspicionService.getSuspicionById(id);
        //Assert
        assertEquals(sus, actual);
    }

    @Test
    void getSuspicionById_Not_Found(){
        int id = 1;

        when(suspicionRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () ->{
            Suspicion actual = suspicionService.getSuspicionById(id);
        });
    }

    @Test
    void deleteSuspicion(){
        //Arrange
        int id = 1;
        User user = new User("Molspotter", "Molspotter@gmail.com", "Testwachtwoord123");
        Episode e = new Episode(LocalDateTime.now());
        Candidate c = new Candidate("Peter");
        Suspicion sus = new Suspicion(user, c, e, 100);
        sus.setId(id);

        when(suspicionRepository.findById(id)).thenReturn(Optional.of(sus));
        //Act
        Suspicion actual = suspicionService.deleteSuspicion(id);
        //Assert
        assertEquals(sus, actual);
    }
}