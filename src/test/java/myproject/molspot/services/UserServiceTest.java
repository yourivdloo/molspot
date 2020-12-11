package myproject.molspot.services;

import myproject.molspot.exceptions.BadRequestException;
import myproject.molspot.exceptions.NotFoundException;
import myproject.molspot.models.User;
import myproject.molspot.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
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

    @Test
    void getUserById_Not_found() {
        int id = 0;

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException .class, () ->{
            userService.getUserById(id);
        });
    }

    @Test
    void createUser(){
        //Arrange
        User user = new User("Molspotter", "Molspotter@gmail.com", "Testwachtwoord123");

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(userRepository.findByEmailAddress(user.getEmailAddress())).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);
        //Act
        User actual = userService.createUser(user);
        //Assert
        assertEquals(user, actual);
    }

    @Test
    void createUser_Username_exists(){
        User user = new User("Molspotter", "Molspotter@gmail.com", "Testwachtwoord123");
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(userRepository.findByEmailAddress(user.getEmailAddress())).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        Assertions.assertThrows(BadRequestException.class, () ->{
            userService.createUser(user);
        });
    }

    @Test
    void createUser_Email_exists(){
        User user = new User("Molspotter", "Molspotter@gmail.com", "Testwachtwoord123");
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(userRepository.findByEmailAddress(user.getEmailAddress())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        Assertions.assertThrows(BadRequestException.class, () ->{
            userService.createUser(user);
        });
    }

    @Test
    void getAllUsers(){
        //Arrange
        User u1 = new User("Molspotter", "Molspotter@gmail.com", "Testwachtwoord123");
        User u2 = new User("widm2020", "widm.2020@gmail.com", "12345");

        Iterable<User> users= new ArrayList<>(){{add(u1); add(u2);}};
        when(userRepository.findAll()).thenReturn(users);
        //Act
        Iterable<User> actual = userService.getAllUsers();
        //Assert
        assertEquals(users, actual);
    }

    @Test
    void deleteUser(){
        //Arrange
        User user = new User("Molspotter", "Molspotter@gmail.com", "Testwachtwoord123");
        int id = 1;
        user.setId(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        //Act
        User deleted = userService.deleteUser(id);
        //Assert
        assertEquals(user, deleted);
    }

    @Test
    void deleteUser_Not_found(){
        User user = new User("Molspotter", "Molspotter@gmail.com", "Testwachtwoord123");
        int id = 1;
        user.setId(id);
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException .class, () ->{
            userService.deleteUser(id);
        });
    }

    @Test
    void updateUser(){
        //Arrange
        User user = new User("Molspotter", "Molspotter@gmail.com", "Testwachtwoord123");
        when(userRepository.save(user)).thenReturn(user);
        //Act
        User actual = userService.updateUser(user);
        //Assert
        assertEquals(user, actual);
    }
}
