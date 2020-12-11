package myproject.molspot.services;

import myproject.molspot.exceptions.NotFoundException;
import myproject.molspot.models.Candidate;
import myproject.molspot.repositories.CandidateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
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
        candidate.setId(id);
        candidate.setIsEliminated(false);

        when(candidateRepository.findById(4)).thenReturn(Optional.of(candidate));
        //Act
        Candidate actual = candidateService.getCandidateById(id);
        //Assert
        assertEquals(candidate, actual);
    }

    @Test
    void getCandidateById_Not_found(){
        int id = 0;

        when(candidateRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () ->{
            candidateService.getCandidateById(id);
        });
    }

    @Test
    void saveCandidate(){
        //Arrange
        Candidate candidate = new Candidate("Peter");

        when(candidateRepository.save(candidate)).thenReturn(candidate);
        //Act
        Candidate actual = candidateService.saveCandidate(candidate);
        //Assert
        assertEquals(candidate, actual);
    }

    @Test
    void getAllCandidates(){
        //Arrange
        Candidate c1 = new Candidate("Hans");
        c1.setId(1);
        c1.setIsEliminated(false);

        Candidate c2 = new Candidate("Jasmine");
        c1.setId(2);
        c1.setIsEliminated(true);

        Candidate c3 = new Candidate("Felix");
        c1.setId(3);
        c1.setIsEliminated(false);

        Candidate c4 = new Candidate("Angela");
        c1.setId(4);
        c1.setIsEliminated(false);

        Iterable<Candidate> candidates = new ArrayList<>() {{add(c1); add(c2); add(c3); add(c4);}};

        when(candidateRepository.findAll()).thenReturn(candidates);
        //Act
        Iterable<Candidate> actual = candidateService.getAllCandidates();
        //Assert
        assertEquals(candidates, actual);
    }

    @Test
    void getAllCandidates_Not_found(){
        Iterable<Candidate> candidates = new ArrayList<>();
        when(candidateRepository.findAll()).thenReturn(candidates);
        Assertions.assertThrows(NotFoundException.class, () ->{
            Iterable<Candidate> actual = candidateService.getAllCandidates();
        });
    }

    @Test
    void deleteCandidate(){
        Candidate can = new Candidate("Johan");
        int id = 1;
        can.setId(id);
        can.setIsEliminated(false);

        when(candidateRepository.findById(id)).thenReturn(Optional.of(can));

        Candidate deleted = candidateService.deleteCandidate(id);

        assertEquals(can, deleted);
    }

    @Test
    void deleteCandidate_Not_found(){
        Candidate can = new Candidate("Johan");
        int id = 1;
        can.setId(id);
        can.setIsEliminated(false);
        when(candidateRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () ->{
            candidateService.deleteCandidate(id);
        });
    }
}