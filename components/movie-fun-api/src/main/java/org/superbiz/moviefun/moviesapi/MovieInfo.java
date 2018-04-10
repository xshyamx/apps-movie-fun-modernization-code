package org.superbiz.moviefun.moviesapi;

public class MovieInfo {
    private final String title;
    private final String director;
    private final String genre;
    private final int rating;
    private final int year;

    public MovieInfo(String title, String director, String genre, int rating, int year) {
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.rating = rating;
        this.year = year;
    }
}
