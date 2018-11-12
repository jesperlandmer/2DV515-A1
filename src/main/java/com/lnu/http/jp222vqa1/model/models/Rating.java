package com.lnu.http.jp222vqa1.model.models;

public class Rating {
    private Movie movie;
    private float score;
    private int id;

    public Rating(Movie movie, float score) {
        this.movie = movie;
        this.score = score;
    }

    public Rating(Movie movie, float score, int id) {
        this.movie = movie;
        this.score = score;
        this.id = id;
    }

    public float getScore() {
        return score;
    }

    public Movie getMovie() {
        return movie;
    }

    public int getId() {
        return id;
    }
}
