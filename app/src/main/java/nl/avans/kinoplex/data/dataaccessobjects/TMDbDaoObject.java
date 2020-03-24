package nl.avans.kinoplex.data.dataaccessobjects;

import android.support.v7.widget.RecyclerView;

import java.util.concurrent.ExecutionException;

import nl.avans.kinoplex.domain.DomainObject;

/** The interface Tm db dao object. */
public interface TMDbDaoObject extends DaoObject {

  /**
   * @author Guus Lieben
   * Read a collection to the given adapter
   *
   * @param identifier the identifier of the collection
   * @param page the page of results to be parsed
   * @param adapter the adapter to write to
   * @return the collection generated from the results
   * @throws ExecutionException potential Exception thrown during runtime by Async tasks
   * @throws InterruptedException potential Exception thrown during runtime by Async tasks
   */
  DomainObject readCollectionToAdapter(String identifier, int page, RecyclerView.Adapter adapter)
      throws ExecutionException, InterruptedException;
}
