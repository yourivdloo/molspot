package myproject.molspot.services;

import myproject.molspot.models.User;
import myproject.molspot.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    void getUserById() {
        //Arrange
        int id = 4;
        User user = new User("Molspotter", "Molspotter@gmail.com", "Testwachtwoord123");
        user.setId(4);

        when(userRepository.findById(4)).thenReturn(Optional.of(user));
        //Act
        User actual = userService.getUserById(id);
        //Assert
        assertEquals(user, actual);
    }
}
