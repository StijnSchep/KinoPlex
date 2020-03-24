package nl.avans.kinoplex.business;

import android.net.Uri;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import nl.avans.kinoplex.domain.Constants;

/**
 * Basic utilities for JSON tasks
 */
public class JsonUtils {

  /**
   * @author Guus Lieben
   * Gets a JSONObject from a given Uri
   *
   * @param uri the uri
   * @return the json object from url
   */
  public static JSONObject getJSONObjectFromUrl(Uri uri) {
    URLConnection connection;
    BufferedReader reader = null;
    try {
      Log.d(
          Constants.JSONUTILS_TAG,
          String.format("Successfully parsed Uri to : %s", uri.toString()));

      URL url = new URL(uri.toString());
      connection = url.openConnection();
      Log.d(Constants.JSONUTILS_TAG, "Successfully opened connection to API");

      reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

      StringBuilder stringBuilder = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) stringBuilder.append(line);

      return new JSONObject(stringBuilder.toString());
    } catch (IOException | JSONException e) {
      Log.e(Constants.JSONUTILS_TAG, e.getMessage());
      for (StackTraceElement el : e.getStackTrace()) Log.e(Constants.JSONUTILS_TAG, el.toString());
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          Log.e(Constants.JSONUTILS_TAG, e.getMessage());
          for (StackTraceElement el : e.getStackTrace())
            Log.e(Constants.JSONUTILS_TAG, el.toString());
        }
      }
    }
    return new JSONObject(); // Never return null, please
  }
}
