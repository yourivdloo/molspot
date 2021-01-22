package myproject.molspot.models;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CandidateTest {

   @Test
    void candidateCtor(){
       //arrange
       String name = "hans";
       int id = 1;
       boolean isEliminated = true;
       //act
       Candidate candidate = new Candidate(name);
       candidate.setId(id);
       candidate.setIsEliminated(isEliminated);
       //assert
       assertEquals(name, candidate.getName());
       assertEquals(id, candidate.getId());
       assertEquals(isEliminated, candidate.getIsEliminated());
   }
}