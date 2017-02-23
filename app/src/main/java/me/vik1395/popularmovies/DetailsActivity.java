package me.vik1395.popularmovies;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.iv_details_poster) ImageView mPoster;
    @BindView(R.id.tv_details_title) TextView mTitle;
    @BindView(R.id.tv_details_date) TextView mDate;
    @BindView(R.id.tv_details_rating) TextView mRating;
    @BindView(R.id.tv_details_overview) TextView mOverview;
    @BindView(R.id.tv_error) TextView mErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        mOverview.setMovementMethod(new ScrollingMovementMethod());

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            Movie movie = intentThatStartedThisActivity.getParcelableExtra("Movie");
            if (movie != null) {
                mErrorMessage.setVisibility(View.INVISIBLE);
                Picasso.with(this).load(movie.getPoster()).into(mPoster);
                mTitle.setText(movie.getTitle());
                mDate.setText(getString(R.string.display_released) + movie.getReleaseDate());
                mRating.setText(getString(R.string.display_rating) + movie.getUserRating() + getString(R.string.display_max_rating));
                mOverview.setText(movie.getOverview());
            }
            else {
                mErrorMessage.setVisibility(View.VISIBLE);
            }
        }
    }
}
