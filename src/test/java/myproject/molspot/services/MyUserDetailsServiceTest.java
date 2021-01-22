package myproject.molspot.services;

import myproject.molspot.exceptions.BadRequestException;
import myproject.molspot.models.MyUserDetails;
import myproject.molspot.models.User;
import myproject.molspot.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class MyUserDetailsServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    MyUserDetailsService myUserDetailsService;

    @Test
    void loadUserByUsername(){
        String username = "testusername";
        String password = "Testpass123";
        int id = 4;
        User user = new User(username, "testmail@gmail.com", password);
        user.setId(4);
        user.setRoles("USER");

        MyUserDetails details = new MyUserDetails(user);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        //Act
        UserDetails actual = myUserDetailsService.loadUserByUsername(username);
        //Assert
        assertEquals(username, actual.getUsername());
        assertEquals(user.getPassword(), actual.getPassword());
    }

    @Test
    void loadUserByUsername_Username_Not_Found(){
        String username = "testusername";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        Assertions.assertThrows(UsernameNotFoundException.class, () ->{
            UserDetails actual = myUserDetailsService.loadUserByUsername(username);
        });
    }

}