package nl.avans.kinoplex.data.factories;

import android.util.Pair;

import nl.avans.kinoplex.data.dataaccessobjects.DaoObject;
import nl.avans.kinoplex.data.dataaccessobjects.TMDbGenreDao;
import nl.avans.kinoplex.data.dataaccessobjects.TMDbListDao;
import nl.avans.kinoplex.data.dataaccessobjects.TMDbMovieDao;
import nl.avans.kinoplex.data.dataaccessobjects.TMDbReviewDao;
import nl.avans.kinoplex.data.dataaccessobjects.TMDbTrailerDao;
import nl.avans.kinoplex.domain.Movie;
import nl.avans.kinoplex.domain.MovieList;
import nl.avans.kinoplex.domain.Review;

/** The type Tm db dao factory. */
public class TMDbDaoFactory implements DaoFactory {

    @Override
    public DaoObject<Review> getReviewDao(int movieId) {
        return new TMDbReviewDao(movieId);
    }

    @Override
    public DaoObject<Movie> getMovieDao(int movieId) {
        return new TMDbMovieDao(movieId);
    }

    @Override
    public DaoObject<Movie> getMovieDao() {
        return new TMDbMovieDao();
    }

    @Override
    public DaoObject<MovieList> getListDao() {
        return new TMDbListDao();
    }


    @Override
    public TMDbTrailerDao getTrailerDao(String movieId) {
        return new TMDbTrailerDao(movieId);
    }


    /**
   * @author Guus Lieben
   * Gets a clean genre dao.
   *
   * @return the genre dao
   */
  public DaoObject<Void> getGenreDao() {
        return new TMDbGenreDao();
    }

    @Override
    public DaoObject<Pair> getUserDao() {
        throw new UnsupportedOperationException();
    }
}
