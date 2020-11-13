package myproject.molspot.services;

import myproject.molspot.models.Candidate;
import myproject.molspot.repositories.CandidateRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
public class CandidateServiceTest {

    @Mock
    CandidateRepository candidateRepository;

    @InjectMocks
    CandidateService candidateService;

    @Test
    void getCandidateById(){
        //Arrange
        int id = 4;
        Candidate candidate= new Candidate("peter");
        candidate.setId(id);
        candidate.setIsEliminated(false);

        when(candidateRepository.findById(4)).thenReturn(Optional.of(candidate));
        //Act
        Candidate actual = candidateService.getCandidateById(id).get();
        //Assert
        assertEquals(candidate, actual);
    }

    @Test
    void createCandidate(){
        Candidate candidate = new Candidate("Peter");
        when(candidateRepository.save(candidate)).thenReturn(candidate);
        Candidate actual = candidateService.saveCandidate(candidate);
        assertEquals(candidate, actual);
    }
}