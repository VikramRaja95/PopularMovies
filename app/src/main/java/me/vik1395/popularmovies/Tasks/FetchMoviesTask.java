package me.vik1395.popularmovies.tasks;

import android.content.Context;
import android.os.AsyncTask;

import java.net.URL;
import java.util.ArrayList;

import me.vik1395.popularmovies.Movie;
import me.vik1395.popularmovies.utils.NetworkUtils;
import me.vik1395.popularmovies.utils.TmdbJsonUtils;

/**
 * Created by Vik1395 on 2/23/2017.
 */

public class FetchMoviesTask extends AsyncTask<String, Void, ArrayList<Movie>> {

    private Context context;
    private AsyncTaskCompleteListener<ArrayList<Movie>> listener;

    public FetchMoviesTask(Context context, AsyncTaskCompleteListener<ArrayList<Movie>> listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Movie> doInBackground(String... params) {

        if (params.length == 0) {
            return null;
        }

        String sort = params[0];
        URL weatherRequestUrl = NetworkUtils.buildUrl(sort);

        try {
            String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl);
            ArrayList<Movie> MoviesData = TmdbJsonUtils.getMoviesDataFromJson(jsonMovieResponse);

            return MoviesData;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movieData) {
        super.onPostExecute(movieData);
        listener.onTaskComplete(movieData);
    }
}