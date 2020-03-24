package nl.avans.kinoplex.data.dataaccessobjects;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.concurrent.ExecutionException;


import nl.avans.kinoplex.business.JsonUtils;
import nl.avans.kinoplex.domain.Constants;


public class TMDbTrailerDao {

    private static String movieId;

    public TMDbTrailerDao(String movieId) {
        this.movieId = movieId;
    }

    public String GetTrailer(){
        try {
            return new LoadTrailerMovie().execute(movieId).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
            return  null;
    }

    private static class LoadTrailerMovie extends AsyncTask<String, Void, String> {


        private final String id = movieId;

        @Override
        protected String doInBackground(String... strings) {
            final String URL = Constants.Trailer_API_URL.replace("{movie_id}",id);
            String Url = null;
            Log.d("DOINBACKGROUND", "URLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL_--------------------->  " + URL);
            try {
                Uri uri = Uri.parse(URL).buildUpon().appendQueryParameter("api_key", Constants.API_KEY).build();
                JSONObject result = JsonUtils.getJSONObjectFromUrl(uri);
                Log.d("DOINBACKGROUNDDDDDDDDDDD", "JSONOBJECT ----------------------------------------------------------------------------------> " + result.toString());
                JSONArray results = result.getJSONArray("results");

                for (int i = 0; i < results.length(); i++) {
                    JSONObject trailerObject = results.getJSONObject(i);
                    String site = trailerObject.getString("site");
                    Log.d("DOINBACKGROUND", "RESULT TRAILEROBJECT SITE --------------------------------> " + site);
                    if (site.equals("YouTube")){
                        Url = trailerObject.getString("key");
                        return Url;
                    }

                }
                return null;

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}

