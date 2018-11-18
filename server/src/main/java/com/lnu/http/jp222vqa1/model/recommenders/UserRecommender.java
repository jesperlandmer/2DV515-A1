package com.lnu.http.jp222vqa1.model.recommenders;

import com.lnu.http.jp222vqa1.model.models.Movie;
import com.lnu.http.jp222vqa1.model.models.Rating;
import com.lnu.http.jp222vqa1.model.models.User;

import java.util.*;

import static java.util.Comparator.comparing;

/**
 * TODO Perhaps we shouldn't have to split Item & User recommender and use generic type instead
 */
public abstract class UserRecommender implements IUserRecommender {

    private HashMap<Integer, User> users;
    private HashSet<Movie> movies;

    public UserRecommender(HashMap<Integer, User> users, HashSet<Movie> movies) {
        this.users = users;
        this.movies = movies;
    }

    public List<Movie> getRecommendedMovies(User A) {
        HashSet<Movie> temp = calcWeightedScore(A);
        List<Movie> recommendedMovies = new ArrayList<Movie>();

        for (Movie m : temp) {
            if (!A.hasRatedMovie(m)) {
                recommendedMovies.add(m);
            }
        }

        // Sort list highest recommendation to lowest
        Collections.sort(recommendedMovies, comparing(Movie::getWeightedScore).reversed());
        return recommendedMovies;
    }

    private HashSet<Movie> calcWeightedScore(User A) {
        HashSet<Movie> temp = (HashSet)movies.clone();
        float sim = 0;

        for (Map.Entry<Integer, User> e : this.users.entrySet()) {
            User B = e.getValue();

            if (!B.equals(A)) {
                sim = simAlgorithm(A, B);

                for (Movie m : temp) {
                    float sumSim = getSumSim(A, m);

                    for (Rating rM : B.getRatedMovies()) {
                        if (rM.getMovie().equals(m)) {
                            float s = ((rM.getScore() * sim) / sumSim);
                            m.addToWeightedScore(s);
                        }
                    }
                }
            }
        }

        return temp;
    }

    private float getSumSim(User A, Movie m) {
        float sumSim = 0;

        for (Map.Entry<Integer, User> e : this.users.entrySet()) {
            User B = e.getValue();

            if (!B.equals(A) && B.hasRatedMovie(m)) {
                sumSim += simAlgorithm(A, B);
            }
        }

        return sumSim;
    }
}
