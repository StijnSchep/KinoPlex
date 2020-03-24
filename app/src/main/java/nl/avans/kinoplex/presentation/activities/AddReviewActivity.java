package nl.avans.kinoplex.presentation.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.data.factories.DataMigration;
import nl.avans.kinoplex.domain.FireReview;
import nl.avans.kinoplex.domain.Constants;
import nl.avans.kinoplex.domain.Movie;
import nl.avans.kinoplex.domain.Review;

/**
 * The type Add review activity.
 * @author Lars Akkermans
 */
public class AddReviewActivity extends AppCompatActivity {
    private RatingBar ratingBar;
    private EditText contextView;
    private Button addReviewBtn;
    private Movie movie;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        ratingBar = findViewById(R.id.rating_bar_add_review);
        contextView = findViewById(R.id.edit_text_add_review);
        addReviewBtn = findViewById(R.id.btn_add_review);
        imageView = findViewById(R.id.iv_add_review_screen);

        if (getIntent().getExtras() == null) {
            return;
        }
        String json = getIntent().getStringExtra(Constants.MOVIE_TAG);
        movie = new Gson().fromJson(json, Movie.class);
        String movieId = movie.getId();



        Toolbar toolbar = findViewById(R.id.add_review_toolbar);
        setSupportActionBar(toolbar);
        String toolbarTitle = getString(R.string.addReviewForMovie) + movie.getTitle();
        getSupportActionBar().setTitle(toolbarTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Glide.with(toolbar)
                .load(movie.getPosterPath())
                .into(imageView);

        addReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(Constants.ADDREVIEWACT_TAG, "User wants to add the Review for the movie " + movieId);
                String userId = Constants.pref.getString("userId", "-1");
                String content = contextView.getText().toString();
                float ratingValue = ratingBar.getRating() * 2;

                Review review = new FireReview(null, userId, content, Math.round(ratingValue), movieId);
                DataMigration.getFactory().getReviewDao(Integer.parseInt(movieId)).create(review);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}