package nl.avans.kinoplex.presentation.viewholders;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.data.dataaccessobjects.TMDbListDao;
import nl.avans.kinoplex.data.factories.DataMigration;
import nl.avans.kinoplex.domain.Constants;
import nl.avans.kinoplex.domain.DomainObject;
import nl.avans.kinoplex.domain.Movie;
import nl.avans.kinoplex.domain.MovieList;
import nl.avans.kinoplex.presentation.activities.ListActivity;
import nl.avans.kinoplex.presentation.adapters.AbstractAdapter;
import nl.avans.kinoplex.presentation.adapters.MainMovieAdapter;

/**
 * The type Main list view holder.
 */
public class MainListViewHolder extends AbstractViewHolder {
    private final TextView listTitle;
    private final Button seeAllBtn;
    private RecyclerView movieListRecylerview;
    private Context context;

    /**
     * Instantiates a new Main list view holder.
     *
     * @param itemView the item view
     */
    public MainListViewHolder(@NonNull View itemView) {
        super(itemView);

        context = itemView.getContext();

        listTitle = itemView.findViewById(R.id.tv_list_title);
        seeAllBtn = itemView.findViewById(R.id.see_list_btn);

        movieListRecylerview = itemView.findViewById(R.id.recyclerview_movie_list);
    }

    /**
     * Bind.
     *
     * @param movieList the movie list
     */
    public void bind(DomainObject movieList) {
        bind(movieList, null);
    }

    /**
     * Bind.
     * @authors Stijn Schep, Lars Akkermans
     * @param movieList the movie list
     * @param adapter   the adapter
     */
    public void bind(DomainObject movieList, MainMovieAdapter adapter) {
        Log.d(Constants.MAINLISTVH_TAG, "Binding MainListViewHolder for list " + ((MovieList) movieList).getName());

        List<Movie> movies = ((MovieList) movieList).getMovieList();
        List<DomainObject> domainMovies = new ArrayList<>(movies);

        String name = ((MovieList) movieList).getName();
        boolean tmdblist = false;
        if (name.equalsIgnoreCase("!Now_playing")) {
            listTitle.setText(context.getResources().getString(R.string.now_playing));
            tmdblist = true;
        } else if (name.equalsIgnoreCase("!Popular")) {
            listTitle.setText(context.getResources().getString(R.string.Popular));
            tmdblist = true;
        } else if (name.equalsIgnoreCase("!Top_rated")) {
            listTitle.setText(context.getResources().getString(R.string.top_rated));
            tmdblist = true;
        } else if (name.equalsIgnoreCase("!Upcoming")) {
            listTitle.setText(context.getString(R.string.upcoming));
            tmdblist = true;
        } else {
            listTitle.setText(name);
        }

        AbstractAdapter<MainMovieViewHolder> movieAdapter;
        if (adapter == null) {
            Log.d(Constants.MAINLISTVH_TAG, "Using a new adapter for list" + name);
            movieAdapter = new MainMovieAdapter(domainMovies);
        } else {
            Log.d(Constants.MAINLISTVH_TAG, "Using the given adapter for list " + name);
            movieAdapter = adapter;
        }

        movieListRecylerview.setLayoutManager(
                new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        movieListRecylerview.setAdapter(movieAdapter);

        if (tmdblist) {
            Log.d(Constants.MAINLISTVH_TAG, "TMDB List collection for " + name);
            try {
                ((TMDbListDao) DataMigration.getTMDbFactory().getListDao()).readCollectionToAdapter(name.toLowerCase(), 1, movieAdapter);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            Log.d(Constants.MAINLISTVH_TAG, "Fire List collection for " + name);
        }


        // set see all btn on click listener to open list activity with the Domainobject movieList as parameter
        seeAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listIntent = new Intent(context, ListActivity.class);
                String jsonString = new Gson().toJson(movieList);
                listIntent.putExtra(Constants.INTENT_EXTRA_MOVIELIST, jsonString);
                context.startActivity(listIntent);
            }
        });
    }

}
