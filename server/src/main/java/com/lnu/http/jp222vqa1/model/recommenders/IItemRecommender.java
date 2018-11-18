package com.lnu.http.jp222vqa1.model.recommenders;

import com.lnu.http.jp222vqa1.model.models.Movie;
import com.lnu.http.jp222vqa1.model.models.User;

import java.util.List;

public interface IItemRecommender {

    List<Movie> getRecommendedMovies(User A);

    default float simAlgorithm(Movie A, Movie B) {
        return 0;
    }
}
