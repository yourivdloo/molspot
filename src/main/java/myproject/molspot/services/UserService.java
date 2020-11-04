package myproject.molspot.services;

import myproject.molspot.models.User;
import myproject.molspot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private int getIterableSize(Iterable<User> users){
        int size = 0;

        for(User user : users){
            size++;
        }

        return size;
    }

    public @ResponseBody ResponseEntity<Object> getAllUsers() {

        Iterable<User> iUser= userRepository.findAll();
        if (getIterableSize(iUser) > 0){
            return new ResponseEntity<>(iUser, HttpStatus.FOUND);
        }
        return new ResponseEntity<>("There were no users in the database", HttpStatus.NOT_FOUND);
    }

    public @ResponseBody ResponseEntity<Object> getUserById(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>("Please provide a valid user ID", HttpStatus.NOT_FOUND);
        }
    }

    public @ResponseBody ResponseEntity<Object> deleteUser(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            userRepository.delete(user.get());
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User with id "+id+" does not exist", HttpStatus.NOT_FOUND);
        }
    }

    public @ResponseBody ResponseEntity<Object> createUser(String username, String emailAddress, String password) {
        if (!username.equals("") && !emailAddress.equals("") && !password.equals("")){
            User user = new User(username, emailAddress, password);
            User createdUser = userRepository.save(user);
            return new ResponseEntity<>(createdUser,HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Please enter all the fields", HttpStatus.CONFLICT);
        }
    }

    public @ResponseBody ResponseEntity<Object> updateUser(Integer id, String username, String emailAddress, String password) {
        Optional<User> optUser = userRepository.findById(id);
        if (optUser.isPresent()){
            User user = optUser.get();
            if (username != null && !username.isEmpty()) { user.setUsername(username); }
            if (emailAddress != null && !emailAddress.isEmpty()) { user.setEmailAddress(emailAddress);}
            if (password != null && !password.isEmpty()) { user.hashPassword(password);}
            User updatedUser = userRepository.save(user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else{
            return new ResponseEntity<>("User with id "+id+" does not exist", HttpStatus.NOT_FOUND);
        }

    }

}
