package me.vik1395.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.vik1395.popularmovies.Tasks.AsyncTaskCompleteListener;
import me.vik1395.popularmovies.Tasks.FetchMoviesTask;

public class MainActivity extends AppCompatActivity implements MovieAdapter.PosterItemClickHandler{

    final String sort_popular = "popular";
    final String sort_top_rated = "top_rated";
    final String sort_upcoming = "upcoming";

    @BindView(R.id.rv_movies) RecyclerView posterHolder;
    @BindView(R.id.pb_loading_indicator) ProgressBar mLoadingIndicator;
    @BindView(R.id.tv_error) TextView mErrorMessage;

    MovieAdapter mAdapter;
    static ArrayList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        GridLayoutManager glm = new GridLayoutManager(this, calculateNoOfColumns(this));
        posterHolder.setLayoutManager(glm);
        posterHolder.setHasFixedSize(true);
        movies = new ArrayList<>();

        loadMovieData(sort_popular);
    }

    private void loadMovieData(String sort) {
        loadMovieDataView();
        if(isOnline()) {
            new FetchMoviesTask(this, new FetchMoviesTaskCompleteListener()).execute(sort);
        }
        else {
            showErrorMessage();
        }
    }

    private void loadMovieDataView() {
        mErrorMessage.setVisibility(View.INVISIBLE);
        posterHolder.setVisibility(View.INVISIBLE);
    }

    private void showErrorMessage() {
        posterHolder.setVisibility(View.INVISIBLE);
        mErrorMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPosterItemClick(View view, int pos) {
        Context context = this;
        Class destinationClass = DetailsActivity.class;
        Intent intentToStartDetailsActivity = new Intent(context, destinationClass);
        intentToStartDetailsActivity.putExtra("Movie", movies.get(pos));
        startActivity(intentToStartDetailsActivity);
    }

    public class FetchMoviesTaskCompleteListener implements AsyncTaskCompleteListener<ArrayList<Movie>> {

        @Override
        public void onTaskComplete(ArrayList<Movie> movieData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movieData != null) {
                posterHolder.setVisibility(View.VISIBLE);
                movies = movieData;
                mAdapter = new MovieAdapter(movieData.size(), MainActivity.this);
                posterHolder.setAdapter(mAdapter);
                mAdapter.setPosters(movieData);
            }
            else {
                showErrorMessage();
            }
        }
    }

    /*public class FetchMoviesTask extends AsyncTask<String, Void, Movie[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected Movie[] doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            String sort = params[0];
            URL weatherRequestUrl = NetworkUtils.buildUrl(sort);

            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl);
                Movie[] MoviesData = TmdbJsonUtils.getMoviesDataFromJson(MainActivity.this, jsonMovieResponse);

                return MoviesData;
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Movie[] movieData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movieData != null) {
                posterHolder.setVisibility(View.VISIBLE);
                movies = movieData;
                mAdapter = new MovieAdapter(movieData.length, MainActivity.this);
                posterHolder.setAdapter(mAdapter);
                mAdapter.setPosters(movieData);
            }
            else {
                showErrorMessage();
            }
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sort_popular) {
            loadMovieData(sort_popular);
            return true;
        }
        else if (id == R.id.action_sort_rating) {
            loadMovieData(sort_top_rated);
            return true;
        }
        else if (id == R.id.action_sort_upcoming) {
            loadMovieData(sort_upcoming);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        return noOfColumns;
    }
}
