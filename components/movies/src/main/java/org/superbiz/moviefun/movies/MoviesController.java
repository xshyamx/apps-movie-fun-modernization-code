package org.superbiz.moviefun.movies;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MoviesController {

    private final MoviesRepository repository;

    public MoviesController(MoviesRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public void addMovie(Movie movie) {
        repository.addMovie(movie);
    }

    @DeleteMapping("/{movieId}")
    public void deleteMovie(@PathVariable Long id) {
        repository.deleteMovieId(id);
    }

    @GetMapping("/count")
    public int count(
            @RequestParam(value = "field", required = false) String field,
            @RequestParam(value = "key", required = false) String key) {
        if (field != null && key != null) {
            return repository.count(field, key);
        } else {
            return repository.countAll();
        }
    }

    @GetMapping
    public List<Movie> findRange(
            @RequestParam(value = "field", required = false) String field,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "start", required = false)  Integer start,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (field != null && key != null) {
            return repository.findRange(field, key, start, pageSize);
        } else if (start != null && pageSize != null) {
            return repository.findAll(start, pageSize);
        } else {
            return repository.getMovies();
        }
    }
}
