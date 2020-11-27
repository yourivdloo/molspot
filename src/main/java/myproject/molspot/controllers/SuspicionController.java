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
    public @ResponseBody ResponseEntity<Object> getSuspicionsByUser(@PathVariable int userId) {
        Iterable<Suspicion> iSuspicion = suspicionService.getSuspicionsByUser(userId);
        return new ResponseEntity<>(iSuspicion, HttpStatus.OK);
    }

    @PostMapping("/new")
    public @ResponseBody ResponseEntity<Object> createSuspicion(@RequestParam int userId, @RequestParam int candidateId, @RequestParam int episodeId, @RequestParam int amount){
        User user = userService.getUserById(userId);
        Candidate candidate = candidateService.getCandidateById(candidateId);
        Episode episode = episodeService.getEpisodeById(episodeId);
        Suspicion suspicion = new Suspicion(user, candidate, episode, amount);
        Suspicion result = suspicionService.saveSuspicion(suspicion);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
