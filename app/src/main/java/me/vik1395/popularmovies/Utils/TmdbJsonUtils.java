package me.vik1395.popularmovies.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import me.vik1395.popularmovies.Movie;

/**
 * Created by Vik1395 on 2/21/2017.
 */

public class TmdbJsonUtils {

    public static ArrayList<Movie> getMoviesDataFromJson(String moviesJsonStr)
            throws JSONException {

        final String TMDB_RESULT = "results";
        final String TMDB_TITLE = "original_title";
        final String TMDB_POSTER = "poster_path";
        final String TMDB_OVERVIEW = "overview";
        final String TMDB_RATING = "vote_average";
        final String TMDB_RELEASE = "release_date";
        final String poster_url = "https://image.tmdb.org/t/p/w500";

        ArrayList<Movie> parsedMoviesData;

        JSONObject moviesJson = new JSONObject(moviesJsonStr);

        JSONArray resultArray = moviesJson.getJSONArray(TMDB_RESULT);

        parsedMoviesData = new ArrayList<>();

        for (int i = 0; i < resultArray.length(); i++) {
            String title;
            String poster;
            String overview;
            String rating;
            String release;

            JSONObject movieData = resultArray.getJSONObject(i);

            title = movieData.getString(TMDB_TITLE);
            poster = poster_url + movieData.getString(TMDB_POSTER);
            overview = movieData.getString(TMDB_OVERVIEW);
            rating = movieData.getString(TMDB_RATING);
            release = movieData.getString(TMDB_RELEASE);

            Movie movie = new Movie(title, poster, overview, rating, release);
            parsedMoviesData.add(i, movie);
        }

        return parsedMoviesData;
    }
}
