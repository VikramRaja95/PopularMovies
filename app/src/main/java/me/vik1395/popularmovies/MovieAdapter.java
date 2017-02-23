package me.vik1395.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by Vik1395 on 2/21/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.PosterViewHolder> {

    private int NumberItems;
    private PosterItemClickHandler mPosterClickListener;
    private ArrayList<Movie> movies;

    public MovieAdapter(int numberOfItems, PosterItemClickHandler ClickListener) {

        NumberItems = numberOfItems;
        mPosterClickListener = ClickListener;
    }

    @Override
    public PosterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int moviePosterItemId = R.layout.poster_item;
        LayoutInflater li = LayoutInflater.from(context);
        boolean attachImmediately = false;

        View view = li.inflate(moviePosterItemId, parent, attachImmediately);
        return new PosterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PosterViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return this.NumberItems;
    }

    public class PosterViewHolder extends RecyclerView.ViewHolder implements OnClickListener{

        ImageView PosterImageView;

        public PosterViewHolder(View itemView) {
            super(itemView);
            PosterImageView = (ImageView) itemView.findViewById(R.id.iv_poster);
            PosterImageView.setOnClickListener(this);
        }
        public void bind(int ListIndex)
        {

            Picasso.with(itemView.getContext()).load(movies.get(ListIndex).getPoster()).into(PosterImageView);
        }

        @Override
        public void onClick(View view)
        {
            if(mPosterClickListener != null)
            {
                mPosterClickListener.onPosterItemClick(view, getAdapterPosition());
            }
        }
    }

    public void setPosters(ArrayList<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public interface PosterItemClickHandler
    {
        void onPosterItemClick(View view, int pos);
    }
}
