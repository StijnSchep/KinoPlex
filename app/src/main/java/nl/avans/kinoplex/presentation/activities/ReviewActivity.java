package nl.avans.kinoplex.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.gson.Gson;

import java.util.ArrayList;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.data.factories.DataMigration;
import nl.avans.kinoplex.domain.Constants;
import nl.avans.kinoplex.domain.Movie;
import nl.avans.kinoplex.presentation.adapters.ReviewAdapter;

public class ReviewActivity extends AppCompatActivity {

    private ReviewAdapter adapter;
    private RecyclerView reviewRecyclerView;
    private Movie movie;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_recyclelist);

        String movieJson = getIntent().getStringExtra("movieJson");
        movie = new Gson().fromJson(movieJson, Movie.class);

        Toolbar toolbar = findViewById(R.id.review_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Reviews for '" + movie.getTitle() + '\'');
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        reviewRecyclerView = findViewById(R.id.reviewlist_recyclerview);
        adapter = new ReviewAdapter(new ArrayList<>());

        DataMigration.getFactory()
                .getReviewDao(Integer.parseInt(movie.getId()))
                .readIntoAdapter(adapter);

        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_review, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_item_add_review:
                Intent reviewActivity = new Intent(this, AddReviewActivity.class);
                String movieJSon = new Gson().toJson(movie);
                reviewActivity.putExtra(Constants.MOVIE_TAG, movieJSon);
                startActivity(reviewActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
