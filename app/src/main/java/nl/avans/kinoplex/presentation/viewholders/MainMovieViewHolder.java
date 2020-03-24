package nl.avans.kinoplex.presentation.viewholders;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.data.factories.DataMigration;
import nl.avans.kinoplex.domain.DomainObject;
import nl.avans.kinoplex.domain.Movie;
import nl.avans.kinoplex.presentation.activities.DetailActivity;

import static nl.avans.kinoplex.domain.Constants.MAINMOVIEVH_TAG;

/**
 * The type Main movie view holder.
 */
public class MainMovieViewHolder extends AbstractViewHolder implements View.OnClickListener {
    
    /*
        ViewHolder for a movie in MainActivity
        The ViewHolder holds the poster, the title and the star rating
        When the user clicks on this ViewHolder, the movie's ID will be passed to DetailActivity
     */

    private ImageView moviePoster;
    private TextView movieTitle;
    private RatingBar movieRating;

    /**
     * The Context.
     */
    Context context;

    private Movie movie;

    /**
     * Instantiates a new Main movie view holder.
     *
     * @param itemView the item view
     */
    public MainMovieViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        this.context = itemView.getContext();
        Log.d(MAINMOVIEVH_TAG, "MainMovieViewHolder was created");

        moviePoster = itemView.findViewById(R.id.iv_main_movie_poster);
        movieTitle = itemView.findViewById(R.id.tv_main_movie_title);
        movieRating = itemView.findViewById(R.id.tb_main_movie_rating);
    }

    /**
     * Gets movie poster.
     *
     * @return the movie poster
     */
    public ImageView getMoviePoster() {
        return moviePoster;
    }

    /**
     * Gets movie title.
     *
     * @return the movie title
     */
    public TextView getMovieTitle() {
        return movieTitle;
    }

    /**
     * Bind.
     *
     * @param obj the obj
     */
    public void bind(DomainObject obj) {
        movie = (Movie) obj;
        System.out.println(movie.getTitle());
        movieTitle.setText(movie.getTitle());

        movieRating.setRating(movie.getRating().floatValue() / 2);
        Glide.with(movieTitle)
                .load(movie.getPosterPath())
                .into(moviePoster);
    }

    @Override
    public void onClick(View v) {
        Log.d(MAINMOVIEVH_TAG, "User clicked on MainMovieViewHolder");

        Intent detailIntent = new Intent(context, DetailActivity.class);
        DataMigration.getFactory().getMovieDao().readIntoIntent(detailIntent, context, movie.getId());

    }
}
