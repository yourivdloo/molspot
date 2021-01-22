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
        LocalDateTime startDate = LocalDateTime.now();
        int id = 1;
        //act
        Episode episode = new Episode(startDate);
        episode.setId(1);
        //assert
        assertEquals(startDate, episode.getStartDate());
        assertEquals(id, episode.getId());
    }

}