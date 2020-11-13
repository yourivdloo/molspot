package myproject.molspot.services;

import myproject.molspot.models.User;
import myproject.molspot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Iterable<User> getAllUsers() {

        return userRepository.findAll();
    }

    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    public @ResponseBody User deleteUser(User user) {
        userRepository.delete(user);
        return user;
    }

    public @ResponseBody User saveUser(User user) {
        return userRepository.save(user);
    }
}
