package io.movies.cgisuvi.Controller;

import io.movies.cgisuvi.models.CatalogItem;
import io.movies.cgisuvi.repository.MovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:3000/")
public class MovieController {
    //A basic controller to fetch the movies.

    @Autowired
    private MovieRepo movieRepo;

    @GetMapping("/movies")
    public List<CatalogItem> fetchMovies() {
        return movieRepo.findAll();
    }

}