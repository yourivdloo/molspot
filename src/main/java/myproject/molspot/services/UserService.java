package myproject.molspot.services;

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
        if (iUsers.iterator().hasNext()){
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
            throw new NotFoundException("User with id "+id+" does not exist");
        }
    }

    public Boolean deleteUser(int id) {
        User user = getUserById(id);
            userRepository.delete(user);
            return true;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
