package myproject.molspot.controllers;

import myproject.molspot.exceptions.BadRequestException;
import myproject.molspot.models.Episode;
import myproject.molspot.services.EpisodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.time.LocalDateTime;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/episodes")
public class EpisodeController {

    @Autowired
    private EpisodeService episodeService;

    @Context
    private UriInfo uriInfo;

    @GetMapping("")
    public @ResponseBody
    ResponseEntity<Object> getAllEpisodes() {
        Iterable<Episode> iEpisode = episodeService.getAllEpisodes();
        return new ResponseEntity<>(iEpisode, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public @ResponseBody
    ResponseEntity<Object> getEpisodeById(@PathVariable int id) {
        Episode episode = episodeService.getEpisodeById(id);
        return new ResponseEntity<>(episode, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public @ResponseBody
    ResponseEntity<Object> deleteEpisode(@PathVariable int id) {
        Episode result = episodeService.deleteEpisode(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/new")
    public @ResponseBody
    ResponseEntity<Object> createEpisode(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate) {
        if (startDate != null) {
            Episode episode = new Episode(startDate);
            Episode result = episodeService.saveEpisode(episode);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            throw new BadRequestException("Please enter all the fields");
        }
    }

    @PutMapping("/{id}")
    public @ResponseBody
    ResponseEntity<Object> updateEpisode(@PathVariable Integer id, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate, @RequestParam(required = false) Boolean hasEnded) {
        Episode episode = episodeService.getEpisodeById(id);
        if (startDate != null) {
            episode.setStartDate(startDate);
        }
        if (hasEnded != null){
            episode.setHasEnded(hasEnded);
            if(hasEnded) {
                episodeService.endEpisode(episode);
            }
        }
        Episode result = episodeService.saveEpisode(episode);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
