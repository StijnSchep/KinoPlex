package nl.avans.kinoplex.business;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.Pair;

import nl.avans.kinoplex.data.dataaccessobjects.FirestoreUserDao;
import nl.avans.kinoplex.domain.Constants;
import nl.avans.kinoplex.presentation.activities.LoginActivity;

/** Utilities to save login credentials in SharedPreferences. **/
public class LoginManager {

  /**
   * @author Stijn Schep
   * Save login credentials to SharedPreferences.
   *
   * @param context the context from which the method is called
   * @param credentials the credentials to save in SharedPreferences {username, hashed password}
   */
  public static void saveLoginCredentials(Context context, Pair<String, String> credentials) {
        Log.d(Constants.LOGINMANGER_TAG, "Checking context...");

        if (context == null) return;

        if (credentials == null) return;

        if (credentials.first == null || credentials.second == null) return;

        if (credentials.first.equals("") || credentials.second.equals("")) return;

        Log.d(Constants.LOGINMANGER_TAG, "Saving the credentials of the user...");

        Constants.editor.putString(Constants.PREF_USERNAME, credentials.first);
        Constants.editor.putString(Constants.PREF_HASHEDPASS, FirestoreUserDao.md5(credentials.second));
        Constants.editor.putBoolean(Constants.PREF_AUTOLOGIN, true);

        Constants.editor.apply();
    }

  /**
   * @author Stijn Schep
   * Gets login credentials from SharedPreferences.
   *
   * @param context the context from which the method is called
   * @return The credentials of the user (username, hashed password) if auto-login was previously selected
   */
  public static Pair<String, String> getLoginCredentials(Context context) {
        if (context == null) {
            return null;
        }

        Log.d(Constants.LOGINMANGER_TAG, "Fetching the credentials of the user...");

        Log.d(Constants.LOGINMANGER_TAG, "Value of AUTOLOGIN: " +
                Constants.pref.getBoolean(Constants.PREF_AUTOLOGIN, false));

        if (Constants.pref.getBoolean(Constants.PREF_AUTOLOGIN, false)) {
            String username = Constants.pref.getString(Constants.PREF_USERNAME, null);
            String hashedPassword = Constants.pref.getString(Constants.PREF_HASHEDPASS, null);

            Log.d(Constants.LOGINMANGER_TAG, "Returning the credentials of the user...");
            return new Pair<>(username, hashedPassword);
        }

        Log.d(Constants.LOGINMANGER_TAG, "Could not start user login");
        return null;
    }

  /**
   * @author Stijn Schep
   * Remove login information from SharedPreferences and send the user to LoginActivity
   *
   * @param activity the activity from which the method is called
   */
  public static void Logout(Activity activity) {
      Log.d(Constants.LOGINMANGER_TAG, "User wants to log out");

        Constants.editor.remove(Constants.PREF_USERNAME);
        Constants.editor.remove(Constants.PREF_HASHEDPASS);
        Constants.editor.remove(Constants.PREF_AUTOLOGIN);

        //Leave on commit to avoid problems with Async
        Constants.editor.commit();

        Intent loginIntent = new Intent(activity, LoginActivity.class);
        activity.finish();

        activity.startActivity(loginIntent);
    }
}
