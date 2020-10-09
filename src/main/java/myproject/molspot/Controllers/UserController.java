package myproject.molspot.Controllers;

import myproject.molspot.Models.Candidate;
import myproject.molspot.Models.User;
import myproject.molspot.Repositories.FakeDataStore;
import myproject.molspot.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Context
    private UriInfo uriInfo;
    // this has to be static because the service is stateless:
    private final FakeDataStore fakeDataStore = FakeDataStore.getInstance();

    @GetMapping("/hello") //GET at http://localhost:XXXX/users/hello
    public @ResponseBody String sayHello() {
        return "Hello, your resource works!";
    }

    public int getIterableSize(Iterable<User> users){
        int size = 0;

        for(User user : users){
            size++;
        }

        return size;
    }

    @GetMapping("") //GET at http://localhost:XXXX/users?
    public @ResponseBody ResponseEntity<Iterable<User>> getAllUsers() {

        Iterable<User> iUser= userRepository.findAll();
        if (getIterableSize(iUser) > 0){
            return new ResponseEntity<Iterable<User>>(iUser, HttpStatus.FOUND);
        }
        return new ResponseEntity("There were no users in the database", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}") //GET at http://localhost:XXXX/users/3
    public @ResponseBody ResponseEntity<User> getUserPath(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return new ResponseEntity<User>(user.get(), HttpStatus.FOUND);
        } else {
            return new ResponseEntity("Please provide a valid user ID", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}") //DELETE at http://localhost:XXXX/users/3
    public @ResponseBody ResponseEntity deleteUser(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            userRepository.delete(user.get());
            return new ResponseEntity("User["+user+" successfully deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity("User with id "+id+" does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/new") //POST at http://localhost:XXXX/users/
    public @ResponseBody ResponseEntity createUser(@RequestParam String username, @RequestParam String emailAddress, @RequestParam String password) {
        if (!username.equals("") && !emailAddress.equals("") && !password.equals("")){
            User user = new User(username, emailAddress, password);
            User createdUser = userRepository.save(user);
            return new ResponseEntity(createdUser,HttpStatus.CREATED);
        } else {
            return new ResponseEntity("Please enter all the fields", HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{id}") //PUT at http://localhost:XXXX/users/
    public @ResponseBody ResponseEntity updateUser(@PathVariable Integer id, @RequestParam(required = false) String username, @RequestParam(required = false) String emailAddress, @RequestParam(required = false) String password) {
        Optional<User> optUser = userRepository.findById(id);
        if (optUser.isPresent()){
            User user = optUser.get();
            if (username != null && !username.isEmpty()) { user.setUsername(username); }
            if (emailAddress != null && !emailAddress.isEmpty()) { user.setEmailAddress(emailAddress);}
            if (password != null && !password.isEmpty()) { user.hashPassword(password);}
            User updatedUser = userRepository.save(user);
            return new ResponseEntity(updatedUser, HttpStatus.OK);
        } else{
            return new ResponseEntity("User with id "+id+" does not exist", HttpStatus.NOT_FOUND);
        }

    }
}
