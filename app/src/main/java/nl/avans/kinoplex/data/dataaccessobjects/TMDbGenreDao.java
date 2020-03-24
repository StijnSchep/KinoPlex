package nl.avans.kinoplex.data.dataaccessobjects;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import nl.avans.kinoplex.business.FirestoreUtils;
import nl.avans.kinoplex.business.JsonUtils;
import nl.avans.kinoplex.domain.Constants;
import nl.avans.kinoplex.domain.DomainObject;

/** The type Tm db genre dao. */
public class TMDbGenreDao implements TMDbDaoObject {

    @Override
    public DomainObject readCollectionToAdapter(String identifier, int page, RecyclerView.Adapter adapter) throws ExecutionException, InterruptedException {
        throw new UnsupportedOperationException();
    }

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
        new ReadGenres().execute();
    }

    /**
     * @author Guus Lieben
     * Reads all remote genres into the local Constants
     */
    private static class ReadGenres extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Uri uri = Uri.parse(Constants.GENRE_API_URL).buildUpon().appendQueryParameter("api_key", Constants.API_KEY).build();
            try {
                JSONObject result = JsonUtils.getJSONObjectFromUrl(uri);
                JSONArray genres = result.getJSONArray("genres");

                for (int i = 0; i < genres.length(); i++) {
                    JSONObject genre = genres.getJSONObject(i);
                    Constants.GENRES.put(genre.getInt("id"), genre.getString("name"));
                    HashMap<String, String> value = new HashMap<String, String>() {{
                        put("name", genre.getString("name"));
                    }};
                    FirestoreUtils.getInstance().collection(Constants.COL_GENRES).document(String.valueOf(genre.getInt("id"))).set(value);
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
            return null;
        }
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
