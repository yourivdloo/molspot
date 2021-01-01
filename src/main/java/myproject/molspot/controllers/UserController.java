package myproject.molspot.controllers;

import myproject.molspot.exceptions.BadRequestException;
import myproject.molspot.models.User;
import myproject.molspot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.*;
import java.util.Base64;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Context
    private UriInfo uriInfo;

    @GetMapping("")
    public @ResponseBody
    ResponseEntity<Object> getAllUsers() {
        Iterable<User> iUsers = userService.getAllUsers();
        return new ResponseEntity<>(iUsers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public @ResponseBody
    ResponseEntity<Object> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/username")
    public @ResponseBody
    ResponseEntity<Object> getUserByUsername(@RequestParam String username) {
        User user = userService.getUserByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public @ResponseBody
    ResponseEntity<Object> deleteUser(@PathVariable int id) {
        User result = userService.deleteUser(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/new")
    public @ResponseBody
    ResponseEntity<Object> createUser(
//            @RequestParam String username, @RequestParam String emailAddress, @RequestParam String password
            @RequestParam String encodedCredentials
    ) {
        String credentials = new String(Base64.getDecoder().decode(encodedCredentials.getBytes()));
        final StringTokenizer tokenizer = new StringTokenizer(credentials, ":");
        try {
            final String username = tokenizer.nextToken();
            final String password = tokenizer.nextToken();
            final String emailAddress = tokenizer.nextToken();

            User user = new User(username, emailAddress, password);
            User result = userService.createUser(user);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (NoSuchElementException n) {
            throw new BadRequestException("Please enter all the fields");
        }


    }

    @PutMapping("/{id}")
    public @ResponseBody
    ResponseEntity<Object> updateUser(@PathVariable Integer id, @RequestParam(required = false) String username, @RequestParam(required = false) String emailAddress, @RequestParam(required = false) String password, @RequestParam(required = false) int points) {
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
        if (points > 0) {
            user.setPoints(points);
        }
        User result = userService.updateUser(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
