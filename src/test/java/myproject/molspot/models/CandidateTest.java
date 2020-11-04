package myproject.molspot.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CandidateTest {

   @Test
    void candidateCtor(){
       //arrange
       String expected = "hans";
       //act
       Candidate candidate = new Candidate(expected);
       //assert
       assertEquals(expected, candidate.getName());
   }
}