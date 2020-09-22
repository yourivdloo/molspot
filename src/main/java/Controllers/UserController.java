package Controllers;

import Models.User;
import Repositories.FakeDataStore;
import Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
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

    @GetMapping("/{id}") //GET at http://localhost:XXXX/users/3
    public @ResponseBody User getUserPath(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            return null;
        }
    }

    @GET //GET at http://localhost:XXXX/users?
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }


    @DeleteMapping("/{id}") //DELETE at http://localhost:XXXX/users/3
    public @ResponseBody String deleteUser(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            userRepository.delete(user.get());
            return "User["+ user +"] deleted";
        } else {
            return "User with id "+ id +" doesn't exist";
        }
    }

    @PostMapping("/new") //POST at http://localhost:XXXX/users/
    public @ResponseBody User createUser(@RequestParam String username, @RequestParam String emailAddress, @RequestParam String password) {
        if (username.equals("") && emailAddress.equals("") && password.equals("")){
            User user = new User(username, emailAddress, password);

            return userRepository.save(user);
        } else {
            return null;
        }
    }

    @PutMapping("/{id}") //PUT at http://localhost:XXXX/users/
    public @ResponseBody User updateUser(@PathVariable Integer id, @RequestParam String username, @RequestParam String emailAddress, @RequestParam String password) {
        Optional<User> optUser = userRepository.findById(id);
        if (optUser.isPresent()){
            User user = optUser.get();
            if (username.equals("") && emailAddress.equals("") && password.equals("")){
                user.setUsername(username);
                user.setEmailAddress(emailAddress);
                user.setPassword(password);
                return userRepository.save(user);
            } else {
                return null;
            }
        } else{
            return null;
        }

    }
}
