package nl.avans.kinoplex.data.factories;

import android.util.Pair;

import nl.avans.kinoplex.data.dataaccessobjects.DaoObject;
import nl.avans.kinoplex.data.dataaccessobjects.TMDbTrailerDao;
import nl.avans.kinoplex.domain.Movie;
import nl.avans.kinoplex.domain.MovieList;
import nl.avans.kinoplex.domain.Review;

/** The interface Dao factory. */
public interface DaoFactory {
  /**
   * @author Guus Lieben
   * Gets a clean review dao.
   *
   * @param movieId the movie id
   * @return the review dao
   */
  DaoObject<Review> getReviewDao(int movieId);

  /**
   * @author Guus Lieben
   * Gets a clean movie dao.
   *
   * @param movieId the movie id
   * @return the movie dao
   */
  DaoObject<Movie> getMovieDao(int movieId);

  /**
   * @author Guus Lieben
   * Gets a clean movie dao.
   *
   * @return the movie dao
   */
  DaoObject<Movie> getMovieDao();

  /**
   * @author Guus Lieben
   * Gets a clean list dao.
   *
   * @return the list dao
   */
  DaoObject<MovieList> getListDao();


  /**
   * @author Guus Lieben
   * Gets a clean Trailer dao.
   *
   * @return the Trailer dao
   */
  TMDbTrailerDao getTrailerDao(String movieId);

  /**
   * @author Guus Lieben
   * Gets a clean user dao.
   *
   * @return the user dao
   */
  DaoObject<Pair> getUserDao();
}
