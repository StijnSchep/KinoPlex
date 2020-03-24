package nl.avans.kinoplex.data.dataaccessobjects;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import nl.avans.kinoplex.business.FirestoreUtils;
import nl.avans.kinoplex.business.JsonUtils;
import nl.avans.kinoplex.domain.Constants;
import nl.avans.kinoplex.domain.TMDbReview;

/** The type Tm db review dao. */
public class TMDbReviewDao implements DaoObject {

    private int movieId;

    /**
     * Instantiates a new Tm db review dao.
     *
     * @param movieId the movie id
     */
    public TMDbReviewDao(int movieId) {
        this.movieId = movieId;
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
        throw new UnsupportedOperationException();
    }

    /**
     * Gets a list of reviews for the movie defined in the constructor.
     *
     * @return the list of reviews
     */
    public List<TMDbReview> getList() {
        try {
            return new LoadReviewsForMovie().execute(movieId).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * @author Guus Lieben
     * Async task to load reviews for a given movieId
     */
    private static class LoadReviewsForMovie extends AsyncTask<Integer, Void, List<TMDbReview>> {

        @Override
        protected List<TMDbReview> doInBackground(Integer... voids) {
            final String URL = Constants.REVIEW_API_URL.replace("{movie_id}", String.valueOf(voids[0]));
            try {
                Uri uri = Uri.parse(URL).buildUpon().appendQueryParameter("api_key", Constants.API_KEY).build();
                JSONObject result = JsonUtils.getJSONObjectFromUrl(uri);
                JSONArray results = result.getJSONArray("results");
                List<TMDbReview> reviews = new ArrayList<>();
                for (int i = 0; i < results.length(); i++) {
                    JSONObject review = results.getJSONObject(i);
                    String author = review.getString("author");
                    String content = review.getString("content");
                    String id = review.getString("id");
                    String movie_id = String.valueOf(voids[0]);
                    TMDbReview tmDbReview = new TMDbReview(id, author, content);
                    Map<String, Object> map = tmDbReview.storeToMap();
                    map.put("movie_id", movie_id);
                    FirestoreUtils.getInstance().collection(Constants.COL_REVIEWS).document(id).set(map);
                    reviews.add(tmDbReview);
                }
                return reviews;
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
            return new ArrayList<>();
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