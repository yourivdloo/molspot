package myproject.molspot.controllers;
import myproject.molspot.models.User;
import myproject.molspot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.ws.rs.core.*;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Context
    private UriInfo uriInfo;

    @GetMapping("")
    public @ResponseBody ResponseEntity<Object> getAllUsers() {
        Iterable<User> iUsers = userService.getAllUsers();
        if (iUsers != null) {
            return new ResponseEntity<>(iUsers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("There were no users in the database", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<Object> getUserById(@PathVariable int id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Please provide a valid user ID", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public @ResponseBody ResponseEntity<Object> deleteUser(@PathVariable int id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            User deletedUser = userService.deleteUser(user.get());
            return new ResponseEntity<>(deletedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User with id " + id + " does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/new")
    public @ResponseBody ResponseEntity<Object> createUser(@RequestParam String username, @RequestParam String emailAddress, @RequestParam String password) {
        if (!username.equals("") && !emailAddress.equals("") && !password.equals("")) {
            User user = new User(username, emailAddress, password);
            User createdUser = userService.saveUser(user);
            return new ResponseEntity<>(createdUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Please enter all the fields", HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{id}")
    public @ResponseBody ResponseEntity<Object> updateUser(@PathVariable Integer id, @RequestParam(required = false) String username, @RequestParam(required = false) String emailAddress, @RequestParam(required = false) String password) {
        Optional<User> optUser = userService.getUserById(id);
        if (optUser.isPresent()) {
            User user = optUser.get();
            if (username != null && !username.isEmpty()) {
                user.setUsername(username);
            }
            if (emailAddress != null && !emailAddress.isEmpty()) {
                user.setEmailAddress(emailAddress);
            }
            if (password != null && !password.isEmpty()) {
                user.hashPassword(password);
            }
            User updatedUser = userService.saveUser(user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User with id: " + id + ". Doesn't exist", HttpStatus.NOT_FOUND);
        }
    }
}
