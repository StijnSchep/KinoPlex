package nl.avans.kinoplex.presentation.viewholders;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.data.factories.DataMigration;
import nl.avans.kinoplex.domain.Movie;
import nl.avans.kinoplex.presentation.activities.DetailActivity;

import static nl.avans.kinoplex.domain.Constants.MAINMOVIEVH_TAG;


public class MovieViewHolder extends AbstractViewHolder implements View.OnClickListener {

    private ImageView moviePoster;
    private TextView movieTitle;

    private Movie movie;

    /** @author Romano Keereweer
     * Instantiates a new Movie view holder.
     *
     * @param itemView the item view
     */
    public MovieViewHolder(@NonNull View itemView) {
        super(itemView);
        moviePoster = itemView.findViewById(R.id.image_view_movie_poster);
        movieTitle = itemView.findViewById(R.id.movie_title);
        itemView.setOnClickListener(this);
    }

    /** @author Romano Keereweer
     * Gets movie poster.
     *
     * @return the movie poster
     */
    public ImageView getMoviePoster() {
        return moviePoster;
    }

    /** @author Romano Keereweer
     * Gets movie title.
     *
     * @return the movie title
     */
    public TextView getMovieTitle() {
        return movieTitle;
    }

    /** @author Romano Keereweer
     * Sets movie.
     *
     * @param movie the movie
     */
    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @Override
    public void onClick(View v) {
        Log.d(MAINMOVIEVH_TAG, "User clicked on MainMovieViewHolder");

        Intent detailIntent = new Intent(v.getContext(), DetailActivity.class);
        DataMigration.getFactory().getMovieDao().readIntoIntent(detailIntent, v.getContext(), movie.getId());
    }
}
