package com.lnu.http.jp222vqa1.model;

import com.lnu.http.jp222vqa1.model.algorithms.Euclidean;
import com.lnu.http.jp222vqa1.model.algorithms.Pearson;
import com.lnu.http.jp222vqa1.model.models.Movie;
import com.lnu.http.jp222vqa1.model.models.Rating;
import com.lnu.http.jp222vqa1.model.models.User;
import com.lnu.http.jp222vqa1.model.pregenerators.ItemBasedGenerator;
import com.lnu.http.jp222vqa1.model.recommenders.ItemRecommender;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class RecommendationFactory {

    private String csvUsers = "users.csv";
    private String csvMovies = "ratings.csv";

    private HashMap<Integer, User> users = new HashMap<Integer, User>();
    private HashSet<Movie> movies = new HashSet<>();

    public RecommendationFactory() {
        try {
            this.convertCsv();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Movie> getEuclideanUserBasedRecommendation(String name) {
        User user = getUserByName(name);
        if (user != null) {
            return new Euclidean(users, movies).getRecommendedMovies(user);
        }
        return null;
    }

    public List<Movie> getPearsonUserBasedRecommendation(String name) {
        User user = getUserByName(name);
        if (user != null) {
            return new Pearson(users, movies).getRecommendedMovies(user);
        }
        return null;
    }

    public List<Movie> getItemBasedRecommendation(String name) {
        User user = getUserByName(name);
        if (user != null) {
            return new ItemRecommender().getRecommendedMovies(user);
        }
        return null;
    }

    public void pregenerateEuclideanItemCollab() {
        try {
            ItemBasedGenerator pregenerator = new ItemBasedGenerator(users);
            pregenerator.printTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * Convert pregenerators files for models
     */
    private void convertCsv() throws IOException {

        CSVReader userReader = new CSVReader(new FileReader(csvUsers), ';', '\'', 1);
        CSVReader movieReader = new CSVReader(new FileReader(csvMovies), ';', '\'', 1);;
        String[] userRow;
        String[] movieRow;

        while ((userRow = userReader.readNext()) != null) {
            movieReader = new CSVReader(new FileReader(csvMovies), ';', '\'', 1);
            User u = new User(Integer.parseInt(userRow[1]), userRow[0]);
            users.put(u.getId(), u);
        }

        while ((movieRow = movieReader.readNext()) != null) {
            Movie m = new Movie(movieRow[1].replace("\"", ""));
            User u = users.get(Integer.parseInt(movieRow[0]));
            movies.add(m);
            if (u != null) {
                u.addRatedMovie(new Rating(m, Float.parseFloat(movieRow[2])));
            }
        }
    }

    private User getUserByName(String name) {
        User user = null;
        for (Map.Entry<Integer, User> e : this.users.entrySet()) {
            if (e.getValue().getName().equals(name)) {
                user = e.getValue();
            }
        }
        return user;
    }


}
