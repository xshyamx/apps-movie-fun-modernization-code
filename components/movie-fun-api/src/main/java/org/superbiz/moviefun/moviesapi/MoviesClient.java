package org.superbiz.moviefun.moviesapi;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import java.util.List;

public class MoviesClient {
    private final String moviesUrl;
    private final RestOperations restOperations;

    public MoviesClient(String moviesUrl, RestOperations restOperations) {
        this.moviesUrl = moviesUrl;
        this.restOperations = restOperations;
    }

    public void addMovie(MovieInfo movie) {
    }

    public void deleteMovieId(Long aLong) {
    }

    public int countAll() {
        return 0;
    }

    public int count(String field, String key) {
        return 0;
    }

    public List<MovieInfo> findAll(int start, int pageSize) {
        return null;
    }

    public List<MovieInfo> findRange(String field, String key, int start, int pageSize) {
        return null;
    }
}
