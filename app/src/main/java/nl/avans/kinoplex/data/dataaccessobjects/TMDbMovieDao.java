package nl.avans.kinoplex.data.dataaccessobjects;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import nl.avans.kinoplex.business.FirestoreUtils;
import nl.avans.kinoplex.business.JsonUtils;
import nl.avans.kinoplex.business.JsonUtilsTask;
import nl.avans.kinoplex.data.factories.DataMigration;
import nl.avans.kinoplex.domain.Constants;
import nl.avans.kinoplex.domain.Movie;
import nl.avans.kinoplex.domain.MovieList;

/** The type Tm db movie dao. */
public class TMDbMovieDao implements DaoObject {

  private int movieId;

  /**
   * @author Guus Lieben Instantiates a new Tm db movie dao.
   * @param movieId the movie id
   */
  public TMDbMovieDao(int movieId) {
    this.movieId = movieId;
  }

  /** @author Guus Lieben Instantiates a new Tm db movie dao. */
  public TMDbMovieDao() {}

  @Override
  public boolean create(Object o) {
    throw new UnsupportedOperationException();
  }

  /**
   * @author Guus Lieben Read movies into a remote collection and list
   * @param movieId the movie id to write
   * @param movieList the local state of the MovieList to write to
   */
  public void readIntoFirebase(int movieId, MovieList movieList) {
    new ReadToFirebase().execute(movieId, movieList);
  }

  private static class ReadToFirebase extends AsyncTask<Object, Void, Void> {

    @Override
    protected Void doInBackground(Object... integers) {
      int movieId = (int) integers[0];
      Uri uri =
          Uri.parse(Constants.MOVIE_API_URL + String.valueOf(movieId))
              .buildUpon()
              .appendQueryParameter("api_key", Constants.API_KEY)
              .build();
      try {
        JSONObject movieObject = JsonUtils.getJSONObjectFromUrl(uri);
        String title = movieObject.getString("original_title");
        int id = movieObject.getInt("id");
        String posterPath = Constants.IMAGE_URL + movieObject.getString("poster_path");
        String tag = movieObject.getString("tagline");
        String language = movieObject.getString("original_language");
        String overview = movieObject.getString("overview");
        @SuppressLint("SimpleDateFormat")
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String value = movieObject.getString("release_date");
        Date releaseDate = dateFormat.parse(value);
        //noinspection ConstantConditions
        boolean adult = movieObject.getBoolean("adult");
        JSONArray genres = movieObject.getJSONArray("genres");
        Double rating = movieObject.getDouble("vote_average");
        int runtime = movieObject.getInt("runtime");
        List<String> genreList = new ArrayList<>();
        if (genres.length() != 0)
          for (int j = 0; j < genres.length(); j++) {
            JSONObject genre = genres.getJSONObject(j);
            genreList.add(String.valueOf(genre.getInt("id")));
          }

        Movie movie =
            new Movie(
                title,
                id,
                runtime,
                posterPath,
                adult,
                genreList,
                tag,
                language,
                overview,
                releaseDate);
        movie.setRating(rating);
        if (integers[1] != null) {
          FirestoreUtils.getInstance()
              .collection(Constants.COL_MOVIES)
              .document(movie.getId())
              .set(movie.storeToMap())
              .addOnSuccessListener(
                  aVoid -> {
                    final Handler handler = new Handler();
                    handler.postDelayed(
                        () ->
                            ((FirestoreMovieDao) DataMigration.getFactory().getMovieDao(movieId))
                                .readIntoList((MovieList) integers[1], null),
                        250);
                  });
        }
      } catch (JSONException | ParseException ex) {
        ex.printStackTrace();
      }
      return null;
    }
  }

  @Override
  public void readIntoAdapter(RecyclerView.Adapter adapter) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void readIntoIntent(Intent intent, Context context, Object id) {
    if (id instanceof Movie) {
      Uri uri =
          Uri.parse(Constants.MOVIE_API_URL + ((Movie) id).getId())
              .buildUpon()
              .appendQueryParameter("api_key", Constants.API_KEY)
              .appendQueryParameter("language", "en-US")
              .build();
      try {
        JSONObject result = new JsonUtilsTask().execute(uri).get();
        String language = result.getString("original_language");
        String tagline = result.getString("tagline");
        Object runtimeObj = result.get("runtime");
        int runtime = 0;
        if (runtimeObj != null) runtime = (int) runtimeObj;
        JSONArray genres = result.getJSONArray("genres");
        double rating = result.getDouble("vote_average");
        List<String> genreIds = new ArrayList<>();

        for (int i = 0; i < genres.length(); i++) {
          JSONObject genre = (JSONObject) genres.get(i);
          genreIds.add(String.valueOf(genre.get("id")));
        }
        Movie movie = (Movie) id;
        movie.setLanguage(language);
        movie.setTag(tagline);
        movie.setRuntime(runtime);
        movie.setGenres(genreIds);
        movie.setRating(rating);

        DataMigration.getFactory().getMovieDao().create(movie);

        String movieJson = new Gson().toJson(movie);
        if (intent != null) {
          intent.putExtra("movieJson", movieJson);

          context.startActivity(intent);
        }

      } catch (JSONException ex) {
        ex.printStackTrace();
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (ExecutionException e) {
        e.printStackTrace();
      }
    } else {
      throw new UnsupportedOperationException();
    }
  }

  @Override
  public void readAll(RecyclerView.Adapter adapter) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean update(Object o) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean delete(Object o) {
    throw new UnsupportedOperationException();
  }
}
