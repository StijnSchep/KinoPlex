package nl.avans.kinoplex.presentation.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.domain.Constants;
import nl.avans.kinoplex.domain.DomainObject;
import nl.avans.kinoplex.presentation.viewholders.MainMovieViewHolder;

/**
 * The type Main movie adapter.
 */
public class MainMovieAdapter extends AbstractAdapter<MainMovieViewHolder> {
  private Context context;

  /**
   * Instantiates a new Main movie adapter.
   *
   * @param dataSet the data set
   */
  public MainMovieAdapter(List<DomainObject> dataSet) {
    super(dataSet);

    Log.d(Constants.MOVIEADAPT_TAG, "Created a MainMovieAdapter! Data size: " + dataSet.size());

  }


  @NonNull
  @Override
  public MainMovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    Log.d(Constants.MOVIEADAPT_TAG, "Creating a new MainMovieViewHolder...");
    context = viewGroup.getContext();

//    int layoutID = R.layout.main_movie_item;
    int layoutID = R.layout.main_movie_item;
    LayoutInflater inflater = LayoutInflater.from(context);

    View view = inflater.inflate(layoutID, viewGroup, false);

    return new MainMovieViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull MainMovieViewHolder movieViewHolder, int i) {
    Log.d(Constants.MOVIEADAPT_TAG, "Binding a MainMovieViewHolder...");
    DomainObject object = getDataSet().get(i);
    movieViewHolder.bind(object);
  }

  @Override
  public int getItemCount() {
    return getDataSet().size();
  }

}
