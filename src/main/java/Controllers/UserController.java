package Controllers;

import Models.User;
import Repositories.FakeDataStore;
import org.springframework.web.bind.annotation.*;


import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Context
    private UriInfo uriInfo;
    // this has to be static because the service is stateless:
    private final FakeDataStore fakeDataStore = FakeDataStore.getInstance();

    @GetMapping("/hello") //GET at http://localhost:XXXX/users/hello
    public @ResponseBody String sayHello() {
        String msg = "Hello, your resource works!";
        return msg;
    }

    @GetMapping("/{id}") //GET at http://localhost:XXXX/users/3
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserPath(@PathVariable int id) {
        // UsersRepository usersRepository = RepositoryFactory.getUsersRepository();
        User user = fakeDataStore.getUser(id);//studentsRepository.get(stNr);
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Please provide a valid user id.").build();
        } else {
            return Response.ok(user).build();
        }
    }

    @GET //GET at http://localhost:XXXX/users?
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        List<User> users;
        users = fakeDataStore.getAllUsers();
        GenericEntity<List<User>> entity = new GenericEntity<>(users) {  };
        return Response.ok(entity).build();
    }


    @DELETE //DELETE at http://localhost:XXXX/users/3
    @Path("{id}")
    public Response deleteUser(@PathParam("id") int id) {
        fakeDataStore.deleteUser(id);
        // Idempotent method. Always return the same response (even if the resource has already been deleted before).
        return Response.noContent().build();
    }

    @POST //POST at http://localhost:XXXX/users/
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(User user) {
        if (!fakeDataStore.addUser(user)){
            String entity =  "User with id " + user.getId() + " already exists.";
            return Response.status(Response.Status.CONFLICT).entity(entity).build();
        } else {
            String url = uriInfo.getAbsolutePath() + "/" + user.getId(); // url of the created user
            URI uri = URI.create(url);
            return Response.created(uri).build();
        }
    }

    @PUT //PUT at http://localhost:XXXX/users/
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response updateUser(User user) {
        // Idempotent method. Always update (even if the resource has already been updated before).
        if (fakeDataStore.updateUser(user)) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Please provide a valid user id.").build();
        }
    }


    @PUT //PUT at http://localhost:XXXX/users/{id}
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Path("{id}")
    public Response updateUser(@PathParam("id") int id,  @FormParam("username") String username, @FormParam("password") String password, @FormParam("emailAddress") String emailAddress) {
        User user = fakeDataStore.getUser(id);
        if (user == null){
            return Response.status(Response.Status.NOT_FOUND).entity("Please provide a valid user id.").build();
        }

        user.setUsername(username);
        user.setEmailAddress(emailAddress);
        user.setPassword(password);
        return Response.noContent().build();
    }

}
