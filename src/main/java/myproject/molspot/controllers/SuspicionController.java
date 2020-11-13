package myproject.molspot.controllers;
import myproject.molspot.services.SuspicionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/suspicions")
public class SuspicionController {

    @Autowired
    private SuspicionService suspicionService;

    @Context
    private UriInfo uriInfo;


    @GetMapping("/{userId}")
    public @ResponseBody ResponseEntity<Object> getSuspicionsByUser(@PathVariable int userId) {
        return suspicionService.getSuspicionsByUser(userId);
    }

    @PostMapping("/new")
    public @ResponseBody ResponseEntity<Object> createSuspicion(@RequestParam int userId, @RequestParam int candidateId, @RequestParam int episodeId, @RequestParam int amount){
        return suspicionService.createSuspicion(userId, candidateId, episodeId, amount);
    }

}
