package com.lnu.http.jp222vqa1.model.pregenerators;

import com.lnu.http.jp222vqa1.model.algorithms.Euclidean;
import com.lnu.http.jp222vqa1.model.models.Movie;
import com.lnu.http.jp222vqa1.model.models.Rating;
import com.lnu.http.jp222vqa1.model.models.User;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ItemBasedGenerator {
    private HashMap<String, Movie> movies = new HashMap<String, Movie>();
    private HashMap<Movie, HashMap<Movie, Float>> movieSims = new HashMap<Movie, HashMap<Movie, Float>>();
    private HashMap<Integer, User> users;

    public ItemBasedGenerator(HashMap<Integer, User> users) {
        this.users = users;
    }

    public void printTable() throws IOException {
        getRatingsTable();
        mapMoviesWithSim();

        String csvPreItemBase = "itemColl.csv";
        CSVWriter csvOutput = new CSVWriter(new FileWriter(csvPreItemBase), ';', CSVWriter.NO_QUOTE_CHARACTER);
        String[] myHeader = new String[2];
        myHeader[0] = "Movie";
        myHeader[1] = "Simularities";
        csvOutput.writeNext(myHeader);

        for (Map.Entry<Movie, HashMap<Movie, Float>> e : movieSims.entrySet()) {
            String[] row = new String[2];
            row[0] = e.getKey().getName().replace("\"", "");
            row[1] = "";

            for (Map.Entry<Movie, Float> d : e.getValue().entrySet()) {
                row[1] += d.getKey().getName().replace("\"", "") + ":" + d.getValue() + "/,";
            }

            csvOutput.writeNext(row);
        }

        csvOutput.close();
    }

    private void getRatingsTable() {
        String csvMovies = "ratings.csv";
        CSVReader movieReader = null;
        try {
            movieReader = new CSVReader(new FileReader(csvMovies), ';', '\'', 1);
            String[] movieRow;

            while ((movieRow = movieReader.readNext()) != null) {
                String movieName = movieRow[1];
                Movie m = movies.get(movieName);
                User u = users.get(Integer.parseInt(movieRow[0]));

                if (m != null) {
                    Rating r = new Rating(m, Float.parseFloat(movieRow[2]), Integer.parseInt(movieRow[0]));
                    m.addRating(r);
                } else {
                    m = new Movie(movieName);
                    Rating r = new Rating(m, Float.parseFloat(movieRow[2]), Integer.parseInt(movieRow[0]));
                    m.addRating(r);
                    movies.put(movieName, m);
                }

                if (u != null) {
                    u.addRatedMovie(new Rating(m, Float.parseFloat(movieRow[2])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mapMoviesWithSim() {
        for (Map.Entry<String, Movie> a : movies.entrySet()) {
            Movie A = a.getValue();
            movieSims.put(A, new HashMap<Movie, Float>());

            for (Map.Entry<String, Movie> b : movies.entrySet()) {
                Movie B = b.getValue();
                if (!A.equals(B)) {
                    movieSims.get(A).put(B, Euclidean.movieEuclidean(A, B));
                }

            }

        }
    }

}
