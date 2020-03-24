package nl.avans.kinoplex.presentation.adapters;



import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.domain.DomainObject;
import nl.avans.kinoplex.domain.MovieList;
import nl.avans.kinoplex.presentation.activities.ManageListsActivity;
import nl.avans.kinoplex.presentation.viewholders.MainListViewHolder;


/**
 * The type Main list adapter.
 */
public class MainListAdapter extends AbstractAdapter<MainListViewHolder> {

  private Context context;

  private DrawerMenuUpdateListener listener;

  private Map<DomainObject, MainMovieAdapter> adapterMap;

  /**
   * The interface Drawer menu update listener.
   */
  public interface DrawerMenuUpdateListener {
    /**
     * Add lists to navigation.
     */
    void addListsToNavigation();
  }

  /**
   * Sets listener.
   *
   * @param listener the listener
   */
  public void setListener(DrawerMenuUpdateListener listener) {
    this.listener = listener;
  }

  /**
   * Instantiates a new Main list adapter.
   *
   * @param dataSet the data set
   * @param context the context
   */
  public MainListAdapter(List<DomainObject> dataSet, Context context) {

    super(dataSet);
    this.context = context;
  }

  /**
   * Sets adapter map.
   *
   * @param map the map
   */
  public void setAdapterMap(Map<DomainObject, MainMovieAdapter> map) {
    this.adapterMap = map;
  }

  @NonNull
  @Override
  public MainListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    Context context = viewGroup.getContext();
    int layoutIdForMovieList = R.layout.movie_list;
    LayoutInflater inflater = LayoutInflater.from(context);

    View view = inflater.inflate(layoutIdForMovieList, viewGroup, false);

    return new MainListViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull MainListViewHolder mainListViewHolder, int i) {
    MovieList list = (MovieList) getDataSet().get(i);
    MainMovieAdapter adapter = adapterMap.get(list);

    mainListViewHolder.bind(list,adapter);

  }

  @Override
  public int getItemCount() {
    return getDataSet().size();
  }

  @Override
  public void updateDataSet(List<DomainObject> dataSet) {
    super.updateDataSet(dataSet);

    listener.addListsToNavigation();
  }
}
