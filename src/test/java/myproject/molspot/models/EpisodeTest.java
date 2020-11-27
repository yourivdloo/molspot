package myproject.molspot.models;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class EpisodeTest {

    @Test
    void episodeCtor(){
        //arrange
        LocalDateTime expected = LocalDateTime.now();
        //act
        Episode episode = new Episode(expected);
        //assert
        assertEquals(expected, episode.getStartDate());
    }

}