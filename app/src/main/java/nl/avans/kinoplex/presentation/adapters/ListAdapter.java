package nl.avans.kinoplex.presentation.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.business.DialogBuilder;
import nl.avans.kinoplex.domain.Constants;
import nl.avans.kinoplex.domain.DomainObject;
import nl.avans.kinoplex.domain.Movie;
import nl.avans.kinoplex.presentation.viewholders.MovieViewHolder;

import static nl.avans.kinoplex.presentation.adapters.SearchAdapter.getYear;

/**
 * The type List adapter.
 */
public class ListAdapter extends AbstractAdapter<MovieViewHolder> {
  private Context context;
  private List<DomainObject> list;
  private List<DomainObject> listFull;

  /**
   * Instantiates a new List adapter.
   * @author Lars Akkermans
   * @param dataSet the data set
   */
  public ListAdapter(List<DomainObject> dataSet) {
    super(dataSet);
    listFull = new ArrayList<>(dataSet);
  }

  @NonNull
  @Override
  public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

    context = viewGroup.getContext();

    int layoutID = R.layout.movie_row;
    LayoutInflater inflater = LayoutInflater.from(context);

    View view = inflater.inflate(layoutID, viewGroup, false);
    list = new ArrayList<>(getDataSet());
    return new MovieViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull MovieViewHolder viewHolder, int i) {
    Movie movie = (Movie) getDataSet().get(i);
    ImageView imageView = viewHolder.itemView.findViewById(R.id.image_view_movie_poster);
    TextView releaseYear = viewHolder.itemView.findViewById(R.id.movie_year);
    TextView genre = viewHolder.itemView.findViewById(R.id.movie_genre);
    RatingBar ratingBar = viewHolder.itemView.findViewById(R.id.movie_rating);
    Glide.with(viewHolder.itemView.getContext())
        .load(movie.getPosterPath())
        .into(imageView); // sets the poster of the current movie in the recyclerview

    List<String> genres = new ArrayList<>();
    for ( String s : movie.getGenres() ) {
      String g = Constants.GENRES.get(Integer.parseInt(s));
      genres.add(g);
    }
    String stringGenres = String.join(", ", genres);
    genre.setText(stringGenres);

    viewHolder.setMovie(movie);
    viewHolder
        .getMovieTitle()
        .setText(movie.getTitle()); // sets the title of the movie in the recyclerview
    releaseYear.setText(
        String.valueOf(
            getYear(
                movie.getReleaseDate()))); // sets the releaseyear of the movie in the recyclerview
    if (movie.getRating() != null)
      ratingBar.setRating(movie.getRating().floatValue() / 2); // sets rating of the movie
    else ratingBar.setVisibility(View.INVISIBLE);
  }

  @Override
  public int getItemCount() {
    return getDataSet().size();
  }

  /**
   * Gets filter.
   * @author Lars Akkermans
   * checks which filter to return, else null
   * @param filterType the filter type
   * @return the filter
   */
  public Filter getFilter(DialogBuilder.FilterType filterType) {
    switch (filterType) {
      case YEAR_FILTER:
        return movieFilterYear;
      case GENRE_FILTER:
        return movieFilterGenre;
      default:
        return null;
    }
  }

  /**
   * @author Lars Akkermans
   * year date filter
   */
  private Filter movieFilterYear =
          new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
              List<DomainObject> filteredList = new ArrayList<>();


              if (constraint == null || constraint.length() == 0) { // checks if the query is empty, if so, all data will be shown
                filteredList.addAll(listFull);
              } else {
                String filterPattern = constraint.toString().toLowerCase().trim(); // otherwise the chosen filter will be applied

                for (DomainObject movie : listFull) {
                  if (Objects.requireNonNull(((Movie) movie).getReleaseyear())
                          .toLowerCase()
                          .contains(filterPattern)) {
                    filteredList.add(movie);
                  }
                }
              }
              FilterResults results = new FilterResults();
              results.values = filteredList;

              return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
              if(list == null) {
                return;
              }

                list.clear();
              list.addAll((List) filterResults.values);
              updateDataSet((List<DomainObject>) filterResults.values); // update the data with the filteredresults
              notifyDataSetChanged();
            }
          };

  /**
   * @author Lars Akkermans
   * genre filter
   */
  private Filter movieFilterGenre =
          new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
              List<DomainObject> filteredList = new ArrayList<>();


              if (constraint == null || constraint.length() == 0) { // checks if the query is empty, if so, all data will be shown
                filteredList.addAll(listFull);
              } else {
                String filterPattern = constraint.toString().toLowerCase().trim(); // otherwise the chosen filter will be applied

                for (DomainObject movie : listFull) {
                  if (Objects.requireNonNull(checkFilterPatternInList(filterPattern, ((Movie) movie).getGenres()))) {
                    filteredList.add(movie);
                  }
                }
              }
              FilterResults results = new FilterResults();
              results.values = filteredList;

              return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if(list == null) {
                    return;
                }

              list.clear();
              list.addAll((List) results.values);
              updateDataSet((List<DomainObject>) results.values); // update the data with the filteredresults
              notifyDataSetChanged();
            }
          };

  /**
   * @author Lars Akkermans
   * checks if filterpattern (query) is in the genres list
   * @param filterPattern the filter pattern
   * @param list the genres list
   * @return boolean
   */
  private boolean checkFilterPatternInList(String filterPattern, List<String> list) {
    for ( String s : list ) {
      String genre = Constants.GENRES.get(Integer.parseInt(s));
      if ( genre.toLowerCase().contains(filterPattern.toLowerCase()) ) {
        return true;
      }
    }
    return false;
  }
}

