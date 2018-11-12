package com.lnu.http.jp222vqa1.model.models;

import java.util.HashSet;
import java.util.Set;

public class User implements Comparable<User> {

    private int id;
    private String name;
    private Set<Rating> ratings;

    public User(String name) {
        this.name = name;
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
        this.ratings = new HashSet<Rating>();
    }

    public int getId() { return this.id; }

    public String getName() { return this.name; }

    public void addRatedMovie(Rating ratedMovie) {
        this.ratings.add(ratedMovie);
    }

    public Set<Rating> getRatedMovies() { return this.ratings; }

    public boolean hasRatedMovie(Movie m) {
        for (Rating r : this.ratings) {
            if (r.getMovie().equals(m)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof User) {
            return compareTo((User)other) == 0;
        }
        return false;
    }

    @Override
    public int compareTo(User u) {
        return this.name.compareToIgnoreCase(u.name);
    }
}
