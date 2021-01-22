package myproject.molspot.models;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MyUserDetailsTest {
    @Test
    void MyUserDetailsTestCtor() {
        //Arrange
        String username = "testuser95";
        User user = new User(username, "testuser@gmail.com", "p@ssw0rd");
        user.setRoles("USER");
        //Act
        MyUserDetails details = new MyUserDetails(user);
        //Assert
        assertEquals(username, details.getUsername());
        assertEquals(user.getPassword(), details.getPassword());
    }
}