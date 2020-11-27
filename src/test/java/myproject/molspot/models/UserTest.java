package myproject.molspot.models;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void userCtor(){
        //arrange
        String username = "hans";
        String pass = "p@ssw0rd";
        String email = "hans@gmail.com";
        //act
        User user = new User(username, email, pass);
        //assert
        assertEquals(username, user.getUsername());
        assertEquals(email, user.getEmailAddress());
    }

}