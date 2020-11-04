package myproject.molspot.controllers;
import myproject.molspot.models.Candidate;
import myproject.molspot.models.Suspicion;
import myproject.molspot.repositories.SuspicionRepository;
import myproject.molspot.services.SuspicionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
@CrossOrigin
@RestController
@RequestMapping("/suspicions")
public class SuspicionController {

    @Autowired
    private SuspicionService suspicionService;

    @Context
    private UriInfo uriInfo;


    @GetMapping("/{userId}") //GET at http://localhost:XXXX/suspicions/{userId}
    public @ResponseBody ResponseEntity<Object> getSuspicionsByUser(@PathVariable int userId) {
        return suspicionService.getSuspicionsByUser(userId);
    }

    @PostMapping("/new") //POST at http://localhost:XXXX/suspicions/new
    public @ResponseBody ResponseEntity<Object> CreateSuspicion(@RequestParam int userId, @RequestParam int candidateId, @RequestParam int amount){
        return suspicionService.CreateSuspicion(userId, candidateId, amount);
    }

}
