package myproject.molspot.models;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SuspicionTest {
    @Test
    void SuspicionCtor(){
        //arrange
        User user = new User();
        Candidate candidate = new Candidate();
        Episode episode = new Episode();
        int amount = 150;
        //act
        Suspicion sus = new Suspicion(user, candidate, episode, amount);
        //assert
        assertEquals(amount, sus.getAmount());
        assertEquals(user, sus.getUser());
        assertEquals(candidate, sus.getCandidate());
        assertEquals(episode, sus.getEpisode());

    }

}