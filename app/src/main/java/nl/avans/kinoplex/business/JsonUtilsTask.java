package nl.avans.kinoplex.business;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import nl.avans.kinoplex.domain.Constants;

/** Basic utilities for JSON tasks */
public class JsonUtilsTask extends AsyncTask<Uri, Void, JSONObject> {


    /**
     * @author Stijn Scheo
     * Runs the JsonUtils getJSONObjectFromUrl(Uri uri) method Asynchronously
     *
     * @param uris the Uri with which a connection should be built
     * @return the JSONObject fetched from the Uri
     */
    @Override
    protected JSONObject doInBackground(Uri... uris) {
        Uri uri = uris[0];

        return JsonUtils.getJSONObjectFromUrl(uri);
    }
}
