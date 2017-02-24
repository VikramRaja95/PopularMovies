package me.vik1395.popularmovies.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Vik1395 on 2/21/2017.
 */

public final class NetworkUtils {

    private static final String TMDB_BASE_URL = "https://api.themoviedb.org/3/movie/";

    // TODO Set you personal TMDB API Key here
    private static final String key = "";

    final static String API_KEY = "api_key";

    public static URL buildUrl(String sort) {
        Uri builtUri = Uri.parse(TMDB_BASE_URL + sort).buildUpon()
                .appendQueryParameter(API_KEY, key)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}