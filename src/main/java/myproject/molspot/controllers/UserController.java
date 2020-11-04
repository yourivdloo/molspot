package myproject.molspot.controllers;
import myproject.molspot.models.User;
import myproject.molspot.repositories.UserRepository;
import myproject.molspot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.ws.rs.core.*;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Context
    private UriInfo uriInfo;
    // this has to be static because the service is stateless:
    //private final FakeDataStore fakeDataStore = FakeDataStore.getInstance();

    @GetMapping("") //GET at http://localhost:XXXX/users?
    public @ResponseBody ResponseEntity<Object> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}") //GET at http://localhost:XXXX/users/3
    public @ResponseBody ResponseEntity<Object> getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}") //DELETE at http://localhost:XXXX/users/3
    public @ResponseBody ResponseEntity<Object> deleteUser(@PathVariable int id) {
        return userService.deleteUser(id);
    }

    @PostMapping("/new") //POST at http://localhost:XXXX/users/
    public @ResponseBody ResponseEntity<Object> createUser(@RequestParam String username, @RequestParam String emailAddress, @RequestParam String password) {
        return userService.createUser(username, emailAddress, password);
    }

    @PutMapping("/{id}") //PUT at http://localhost:XXXX/users/
    public @ResponseBody ResponseEntity<Object> updateUser(@PathVariable Integer id, @RequestParam(required = false) String username, @RequestParam(required = false) String emailAddress, @RequestParam(required = false) String password) {
        return userService.updateUser(id, username, emailAddress, password);
    }
}
