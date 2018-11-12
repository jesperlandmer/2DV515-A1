package com.lnu.http.jp222vqa1.model.recommenders;

import com.lnu.http.jp222vqa1.model.models.Movie;
import com.lnu.http.jp222vqa1.model.models.Rating;
import com.lnu.http.jp222vqa1.model.models.User;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static java.util.Comparator.comparing;

/**
 * TODO Perhaps we shouldn't have to split Item & User recommender and use generic type instead
 */
public class ItemRecommender implements IItemRecommender {

    private String csvPreItemBase = "itemColl.csv";

    public List<Movie> getRecommendedMovies(User A) {
        HashMap<Movie, HashMap<Movie, Float>> movieSimTable = getItemBasedRec();
        List<Movie> recommendedMovies = new ArrayList<Movie>();

        for (Map.Entry<Movie, HashMap<Movie, Float>> e : movieSimTable.entrySet()) {
            Movie m = e.getKey();
            if (!A.hasRatedMovie(m)) {
                float wrSum = 0;
                float rSum = 0;

                for (Rating rA : A.getRatedMovies()) {
                    float rec = e.getValue().get(rA.getMovie());
                    float weightedRec = rA.getScore() * rec;

                    wrSum += weightedRec;
                    rSum += rec;
                }
                m.addToWeightedScore(wrSum / rSum);
                recommendedMovies.add(m);
            }
        }

        Collections.sort(recommendedMovies, comparing(Movie::getWeightedScore).reversed());
        return recommendedMovies;
    }

    /**
     * TODO: ugly solution for fetching csv data of item based coll. Refactor ItemBasedGenerator
     * @return
     */
    private HashMap<Movie, HashMap<Movie, Float>> getItemBasedRec() {
        HashMap<Movie, HashMap<Movie, Float>> temp = new HashMap<Movie, HashMap<Movie, Float>>();
        CSVReader itemCollReader = null;
        String[] itemRow;

        try {
            itemCollReader = new CSVReader(new FileReader(csvPreItemBase), ';', '\'', 1);

            while ((itemRow = itemCollReader.readNext()) != null) {
                HashMap<Movie, Float> simMovs = new HashMap<Movie, Float>();
                String[] items = itemRow[1].split("/,");

                for (int i = 0; i < items.length; i++) {
                    String[] movSim = items[i].split(":");
                    simMovs.put(new Movie(movSim[0]),Float.parseFloat(movSim[1]));
                }

                temp.put(new Movie(itemRow[0]), simMovs);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return temp;
    }
}
