package myproject.molspot.services;

import myproject.molspot.exceptions.BadRequestException;
import myproject.molspot.exceptions.NotFoundException;
import myproject.molspot.models.User;
import myproject.molspot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Iterable<User> getAllUsers() {
        Iterable<User> iUsers = userRepository.findAll();
        if (iUsers.iterator().hasNext()) {
            return iUsers;
        } else {
            throw new NotFoundException("There were no users in the database");
        }
    }

    public User getUserById(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new NotFoundException("User with id " + id + " does not exist");
        }
    }

    public User deleteUser(int id) {
        User user = getUserById(id);
        userRepository.delete(user);
        return user;
    }

    public User createUser(User user) {
        if (!userRepository.findByUsername(user.getUsername()).isPresent()) {
            if (!userRepository.findByEmailAddress(user.getEmailAddress()).isPresent()) {
                return userRepository.save(user);
            } else {
                throw new BadRequestException("There has already been created an account with this email address");
            }
        } else {
            throw new BadRequestException("There has already been created an account with this username");
        }
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }
}
