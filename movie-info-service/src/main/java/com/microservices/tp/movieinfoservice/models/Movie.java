package com.microservices.tp.movieinfoservice.models;

public class Movie {
    private String movieId;
    private String name;
    private String title;
    private String overview;

    // Default constructor & parameterized constructor

    public Movie() {
    }

    public Movie(String movieId, String name, String title, String overview) {
        this.movieId = movieId;
        this.name = name;
        this.title = title;
        this.overview = overview;
    }

    // Getters & Setteres of attributs

    public String getMovieId() {
        return this.movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return this.overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }


}
