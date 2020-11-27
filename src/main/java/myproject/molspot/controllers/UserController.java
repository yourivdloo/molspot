package myproject.molspot.controllers;

import myproject.molspot.exceptions.BadRequestException;
import myproject.molspot.models.User;
import myproject.molspot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.*;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true" )
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
        return new ResponseEntity<>(iUsers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<Object> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public @ResponseBody ResponseEntity<Object> deleteUser(@PathVariable int id) {
        Boolean result = userService.deleteUser(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PostMapping("/new")
    public @ResponseBody ResponseEntity<Object> createUser(@RequestParam String username, @RequestParam String emailAddress, @RequestParam String password) {
        if (!username.equals("") && !emailAddress.equals("") && !password.equals("")) {
            User user = new User(username, emailAddress, password);
            User result = userService.saveUser(user);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            throw new BadRequestException("Please enter all the fields");
        }
    }

    @PutMapping("/{id}")
    public @ResponseBody ResponseEntity<Object> updateUser(@PathVariable Integer id, @RequestParam(required = false) String username, @RequestParam(required = false) String emailAddress, @RequestParam(required = false) String password) {
        User user = userService.getUserById(id);
            if (username != null && !username.isEmpty()) {
                user.setUsername(username);
            }
            if (emailAddress != null && !emailAddress.isEmpty()) {
                user.setEmailAddress(emailAddress);
            }
            if (password != null && !password.isEmpty()) {
                user.setPassword(password);
            }
            User result = userService.saveUser(user);
            return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
