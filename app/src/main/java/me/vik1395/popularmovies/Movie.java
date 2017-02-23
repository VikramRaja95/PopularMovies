package me.vik1395.popularmovies;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vik1395 on 2/21/2017.
 */

public class Movie implements Parcelable{

    private String title;
    private String poster;
    private String overview;
    private String user_rating;
    private String release_date;

    public Movie() {}

    public Movie(String title, String poster, String overview, String user_rating, String release_date) {
        this.title = title;
        this.poster = poster;
        this.overview = overview;
        this.user_rating = user_rating;
        this.release_date = release_date;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getUserRating() {
        return user_rating;
    }

    public void setUserRating(String user_rating) {
        this.user_rating = user_rating;
    }

    public String getReleaseDate() {
        return release_date;
    }

    public void setReleaseDate(String release_date) {
        this.release_date = release_date;
    }


    protected Movie(Parcel in) {
        title = in.readString();
        poster = in.readString();
        overview = in.readString();
        user_rating = in.readString();
        release_date = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(poster);
        parcel.writeString(overview);
        parcel.writeString(user_rating);
        parcel.writeString(release_date);
    }
}
