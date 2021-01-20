package myproject.molspot.controllers;

import myproject.molspot.models.Candidate;
import myproject.molspot.models.Episode;
import myproject.molspot.models.Suspicion;
import myproject.molspot.models.User;
import myproject.molspot.services.CandidateService;
import myproject.molspot.services.EpisodeService;
import myproject.molspot.services.SuspicionService;
import myproject.molspot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/suspicions")
public class SuspicionController {

    @Autowired
    private SuspicionService suspicionService;
    @Autowired
    private UserService userService;
    @Autowired
    private CandidateService candidateService;
    @Autowired
    private EpisodeService episodeService;

    @Context
    private UriInfo uriInfo;


    @GetMapping("/{userId}")
    public @ResponseBody
    ResponseEntity<Object> getSuspicionsByUser(@PathVariable int userId) {
        Iterable<Suspicion> iSuspicion = suspicionService.getSuspicionsByUser(userId);
        return new ResponseEntity<>(iSuspicion, HttpStatus.OK);
    }

    @PostMapping("/new")
    public @ResponseBody
    ResponseEntity<Object> createSuspicion(@RequestParam int userId, @RequestParam int candidateId, @RequestParam(required = false) Integer amount) {
        User user = userService.getUserById(userId);
        Candidate candidate = candidateService.getCandidateById(candidateId);
        Episode episode = episodeService.getCurrentEpisode();
        int sAmount = 0;
        if (amount != null){
            sAmount = amount;
        }
        Suspicion suspicion = new Suspicion(user, candidate, episode, sAmount);
        Suspicion result = suspicionService.saveSuspicion(suspicion);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public @ResponseBody
    ResponseEntity<Object> deleteSuspicion(@PathVariable int id) {
        Suspicion result = suspicionService.deleteSuspicion(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public @ResponseBody
    ResponseEntity<Object> updateSuspicion(@PathVariable int id, @RequestParam int amount){
        Suspicion suspicion = suspicionService.getSuspicionById(id);
        suspicion.setAmount(amount);
        Suspicion result = suspicionService.saveSuspicion(suspicion);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
