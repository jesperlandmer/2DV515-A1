package com.lnu.http.jp222vqa1.model.algorithms;

import com.lnu.http.jp222vqa1.model.models.Movie;
import com.lnu.http.jp222vqa1.model.models.Rating;
import com.lnu.http.jp222vqa1.model.models.User;
import com.lnu.http.jp222vqa1.model.recommenders.UserRecommender;

import java.util.HashMap;
import java.util.HashSet;

public class Pearson extends UserRecommender {
    public Pearson(HashMap<Integer, User> users, HashSet<Movie> movies) {
        super(users, movies);
    }

    @Override
    public float simAlgorithm(User A, User B) {
        // Init variables
        float sum1 = 0;
        float sum2 = 0;
        float sum1sq = 0;
        float sum2sq = 0;
        float pSum = 0;

        // Counter for number of matching movies
        int n = 0;

        for (Rating rA : A.getRatedMovies()) {
            for (Rating rB : B.getRatedMovies()) {
                if (rA.getMovie().equals(rB.getMovie())) {
                    sum1 += rA.getScore();
                    sum2 += rB.getScore();
                    sum1sq += Math.pow(rA.getScore(), 2);
                    sum2sq += Math.pow(rB.getScore(), 2);
                    pSum += (rA.getScore() * rB.getScore());
                    n += 1;
                }
            }
        }

        if (n == 0) {
            // No ratings in common
            return 0;
        }
        float num = pSum - (sum1 * sum2 / n);
        float den = (float) Math.sqrt((Math.pow((sum1sq - sum1), 2) / n) * (Math.pow(sum2sq - sum2, 2) / n));
        return num / den;

    }
}
