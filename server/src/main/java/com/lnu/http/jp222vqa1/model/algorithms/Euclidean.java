package com.lnu.http.jp222vqa1.model.algorithms;

import com.lnu.http.jp222vqa1.model.models.Movie;
import com.lnu.http.jp222vqa1.model.models.Rating;
import com.lnu.http.jp222vqa1.model.models.User;
import com.lnu.http.jp222vqa1.model.recommenders.UserRecommender;

import java.util.HashMap;
import java.util.HashSet;

public class Euclidean extends UserRecommender {

    public Euclidean(HashMap<Integer, User> users, HashSet<Movie> movies) {
        super(users, movies);
    }

    @Override
    public float simAlgorithm(User A, User B) {
        // Init variables
        float sim = 0;
        // Counter for number of matching movies
        int n = 0;

        for (Rating rA : A.getRatedMovies()) {
            for (Rating rB : B.getRatedMovies()) {
                if (rA.getMovie().equals(rB.getMovie())) {
                    sim += Math.pow((rA.getScore() - rB.getScore()), 2);
                    n += 1;
                }
            }
        }

        if (n == 0) {
            // No ratings in common
            return 0;
        }
        return 1 / (1 + sim);

    }

    public static float movieEuclidean(Movie A, Movie B) {
        // Init variables
        float sim = 0;
        // Counter for number of matching movies
        int n = 0;

        for (Rating rA : A.getRatings()) {
            for (Rating rB : B.getRatings()) {
                if (rA.getId() == rB.getId()) {
                    sim += Math.pow((rA.getScore() - rB.getScore()), 2);
                    n += 1;
                }
            }
        }

        if (n == 0) {
            // No ratings in common
            return 0;
        }
        return 1 / (1 + sim);
    }
}
