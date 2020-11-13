package myproject.molspot.services;

import myproject.molspot.models.Candidate;
import myproject.molspot.repositories.CandidateRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CandidateServiceTest {

    @Mock
    CandidateRepository candidateRepository;

    @InjectMocks
    CandidateService candidateService;

    @Test
    void getCandidateById(){
        //Arrange
        int id = 4;
        Candidate candidate= new Candidate("peter");

        when(candidateRepository.findById(id)).thenReturn(Optional.of(candidate));
        //Act
        Optional<Candidate> actual = candidateService.getCandidateById(id);
        //Assert
    }

}