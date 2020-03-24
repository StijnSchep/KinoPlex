package nl.avans.kinoplex.business;

import android.content.Context;
import android.util.Log;

import nl.avans.kinoplex.R;

/** Utilities around checking and changing the names of lists **/
public class CustomListChecker {

  /**
   * @author Stijn Schep
   * Is custom list boolean.
   *
   * @param name the name of the list to check
   * @return whether the given name is a non-standard list
   */
  public static boolean isCustomList(String name) {
        Log.d(CustomListChecker.class.getCanonicalName(), "Checking if list " + name + " is a custom list");

        if (name.equalsIgnoreCase("!Now_playing")) {
            return false;
        } else if (name.equalsIgnoreCase("!Popular")) {
            return false;
        } else if (name.equalsIgnoreCase("!Top_rated")) {
            return false;
        } else if (name.equalsIgnoreCase("!Upcoming")) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * @author Stijn Schep
     * Change the correct title of the given name
     *
     * @param name the list's name that needs to be checked
     * @param context the context from which the method is called
     * @return the proper name if the given name corresponds to a default list
     */
    public static String returnCorrectTitle(String name, Context context) {
        if (name.equalsIgnoreCase("!Now_playing")) {
            return context.getResources().getString(R.string.now_playing);
        } else if (name.equalsIgnoreCase("!Popular")) {
            return context.getResources().getString(R.string.Popular);
        } else if (name.equalsIgnoreCase("!Top_rated")) {
            return context.getResources().getString(R.string.top_rated);
        } else if (name.equalsIgnoreCase("!Upcoming")) {
            return context.getResources().getString(R.string.upcoming);
        } else {
            return name;
        }
    }
}
