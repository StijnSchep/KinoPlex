package nl.avans.kinoplex.data.dataaccessobjects;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

/**
 * The interface for Dao objects.
 *
 * @param <T> the type parameter
 */
public interface DaoObject<T> {

  /**
   * @author Guus Lieben
   * Create a remote object based on the local state
   *
   * @param t The object to write
   * @return the success state
   */
  boolean create(T t);

  /**
   * @author Guus Lieben
   * Read a remote object into a RecyclerView Adapter
   *
   * @param adapter The adapter to write to
   */
  void readIntoAdapter(RecyclerView.Adapter adapter);

  /**
   * @author Guus Lieben
   * Read a remote object into an Intent
   *
   * @param intent the intent to write to
   * @param context the context of the Intent
   * @param id the identifier of the object
   */
  void readIntoIntent(Intent intent, Context context, Object id);

  /**
   * @author Guus Lieben
   * Read all remote objects into an adapter
   *
   * @param adapter the adapter to write to
   */
  void readAll(RecyclerView.Adapter adapter);

  /**
   * @author Guus Lieben
   * Update a remote object with a given local state
   *
   * @param t local state of an object
   * @return the success state
   */
  boolean update(T t);

  /**
   * @author Guus Lieben
   * Delete a remote object based on a local state
   *
   * @param t the local state of the object to delete
   * @return the success state
   */
  boolean delete(T t);
}
