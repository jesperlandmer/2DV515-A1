package com.lnu.http.jp222vqa1.model.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;

public class Movie implements Comparable<Movie> {
    private String name;
    private float similarity = 0;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Rating> ratings;

    public Movie(String name) {
        this.name = name;
        this.ratings = new HashSet<Rating>();
    }

    public String getName() { return this.name; }

    public void addToWeightedScore(float n) {
        similarity += n;
    }

    public float getWeightedScore() {
        return similarity;
    }

    public Set<Rating> getRatings() { return this.ratings; }

    public void addRating(Rating rating) {
        if (rating.getMovie().equals(this)) {
            this.ratings.add(rating);
        }
    }

    /**
     * TODO is this really necessary?
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 0;
        for(char s : this.name.toCharArray()) {
            hash += Character.getNumericValue(s);
        }

        return hash;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Movie) {
            return compareTo((Movie)other) == 0;
        }
        return false;
    }

    @Override
    public int compareTo(Movie m) {
        return this.name.compareToIgnoreCase(m.name);
    }
}
