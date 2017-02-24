package me.vik1395.popularmovies.tasks;

/**
 * Created by Vik1395 on 2/23/2017.
 */

public interface AsyncTaskCompleteListener<T> {

    public void onTaskComplete(T result);
}
