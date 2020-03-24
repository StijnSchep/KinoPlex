package nl.avans.kinoplex.data.dataaccessobjects;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

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

import nl.avans.kinoplex.business.JsonUtils;
import nl.avans.kinoplex.data.factories.DataMigration;
import nl.avans.kinoplex.domain.Constants;
import nl.avans.kinoplex.domain.DomainObject;
import nl.avans.kinoplex.domain.Movie;
import nl.avans.kinoplex.domain.MovieList;

/** The type Tm db list dao. */
public class TMDbListDao implements DaoObject, TMDbDaoObject {

    @Override
    public boolean create(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void readIntoAdapter(RecyclerView.Adapter adapter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void readIntoIntent(Intent intent, Context context, Object id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void readAll(RecyclerView.Adapter adapter) {
//      Uri movieUri = Uri.parse("").buildUpon().appendQueryParameter();
    }

    @Override
    public boolean update(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public DomainObject readCollectionToAdapter(String identifier, int page, RecyclerView.Adapter adapter) throws ExecutionException, InterruptedException {
        ReadFromTMDb task = new ReadFromTMDb();
        task.execute(identifier, page, adapter);
        return task.get();
    }

    /**
     * @author Guus Lieben
     * Reads API remote collections into the default app remote
     */
    private class ReadFromTMDb extends AsyncTask<Object, Void, DomainObject> {

        @Override
        protected DomainObject doInBackground(Object... pairs) {
            String identifier = (String) pairs[0];
            int page = (int) pairs[1];

            Uri listUri = Uri.parse(Constants.MOVIE_API_URL + identifier.replace("!", ""))
                    .buildUpon()
                    .appendQueryParameter("api_key", Constants.API_KEY)
                    .appendQueryParameter("page", String.valueOf(page))
                    .build();

            try {
                JSONObject result = JsonUtils.getJSONObjectFromUrl(listUri);

                MovieList list = new MovieList(identifier.substring(0, 1).toUpperCase() + identifier.substring(1), "-1");
                if (!identifier.startsWith("!")) identifier = '!' + identifier;
                list.setDbId(identifier);

                JSONArray movies = result.getJSONArray("results");

                for (int i = 0; i < movies.length(); i++) {
                    JSONObject movieObject = (JSONObject) movies.get(i);
                    String title = movieObject.getString("title");
                    int id = movieObject.getInt("id");
                    int runtime = 0;
                    String posterPath = Constants.IMAGE_URL + movieObject.getString("poster_path");
                    String tag = "";
                    String language = "";
                    String overview = movieObject.getString("overview");
                    @SuppressLint("SimpleDateFormat")
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String value = movieObject.getString("release_date");
                    Date releaseDate = dateFormat.parse(value);
                    //noinspection ConstantConditions
                    boolean adult = movieObject.getBoolean("adult");
                    JSONArray genres = movieObject.getJSONArray("genre_ids");
                    Double rating = movieObject.getDouble("vote_average");

                    List<String> genreList = new ArrayList<>();
                    if (genres.length() != 0)
                        for (int j = 0; j < genres.length(); j++) {
                            String genre = genres.getString(j);
                            genreList.add(genre);
                        }

                    Movie movie = new Movie(title, id, runtime, posterPath, adult, genreList, tag, language, overview, releaseDate);
                    movie.setRating(rating);

                    DataMigration.getFactory().getMovieDao(id).create(movie);
                    ((FirestoreMovieDao) DataMigration.getFactory().getMovieDao(id)).readIntoList(list, null);
                    DataMigration.getFactory().getMovieDao(id).readIntoAdapter((RecyclerView.Adapter) pairs[2]);
                }

                return list;
            } catch (JSONException | ParseException ex) {
                ex.printStackTrace();
            }

            return null;
        }
    }
}
