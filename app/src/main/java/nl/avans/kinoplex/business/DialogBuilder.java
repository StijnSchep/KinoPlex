package nl.avans.kinoplex.business;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.List;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.data.dataaccessobjects.FirestoreListDao;
import nl.avans.kinoplex.data.factories.DataMigration;
import nl.avans.kinoplex.domain.Constants;
import nl.avans.kinoplex.domain.MovieList;
import nl.avans.kinoplex.presentation.activities.ListActivity;
import nl.avans.kinoplex.presentation.activities.ManageListsActivity;
import nl.avans.kinoplex.presentation.adapters.ListManagerAdapter;

/**
 * The type Dialog builder.
 */
public class DialogBuilder {

    /**
     * The enum Input.
     * @author Stijn Schep
     */
    public enum Input {
        /**
         * Single edittext input.
         */
        SINGLE_EDITTEXT,

        /**
         * Prefilled edittext input.
         */
        PREFILLED_EDITTEXT,

        /**
         * Edittext with label input.
         */
        EDITTEXT_WITH_LABEL
  }

    /**
     * The enum Filter type.
     * @author Lars Akkermans
     */
    public enum FilterType {
        /**
         * Year filter filter type.
         */
        YEAR_FILTER,
        /**
         * Genre filter filter type.
         */
        GENRE_FILTER
    }

    /**
     * @author Stijn Schep
     *
     * @param type
     * @param activity
     * @param input
     * @return
     */
    private static View getView(Input type, Activity activity, String input) {
        LayoutInflater inflater = activity.getLayoutInflater();
        switch (type) {
            case SINGLE_EDITTEXT:
                return inflater.inflate(R.layout.dialog_single_edittext, null);

            case PREFILLED_EDITTEXT:
                View view = inflater.inflate(R.layout.dialog_single_edittext, null);
                ((EditText) view.findViewById(R.id.dialog_single_input)).setText(input);

                return view;

            case EDITTEXT_WITH_LABEL:
                return inflater.inflate(R.layout.dialog_list_filter_layout, null);

            default:
                    final EditText default_input = new EditText(activity);
                    default_input.setPadding(10, 0, 10 ,0);
                    return default_input;

        }
    }

    /**
     * Simple input builder.
     * @author Stijn Schep
     *
     * @param activity the activity
     * @param title    the title
     * @param type     the type
     * @param adapter  the adapter
     */
    public static void simpleInputBuilder(
      Activity activity, String title, Input type, RecyclerView.Adapter adapter) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle(title);

        builder.setView(getView(type, activity, null));

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText edit = ((AlertDialog) dialog).findViewById(R.id.dialog_single_input);
                String input = edit.getText().toString();

                String userId = Constants.pref.getString("userId", "-1");
                MovieList newList = new MovieList(input, userId);

                ((FirestoreListDao) DataMigration.getFactory().getListDao())
                        .createListForUser(newList);

                ((ListManagerAdapter) adapter).addToDataSet(newList);

                ManageListsActivity.datahasChanged = true;
                dialog.dismiss();
            }
        });

        String cancel = activity.getResources().getString(R.string.cancel);
        builder.setNegativeButton(cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    /**
     * Simple list edit dialog.
     * @author Stijn Schep
     *
     * @param activity the activity
     * @param title    the title
     * @param type     the type
     * @param list     the list
     * @param adapter  the adapter
     */
    public static void simpleListEditDialog(
      Activity activity, String title, Input type, MovieList list, RecyclerView.Adapter adapter) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle(title);

        builder.setView(getView(type, activity, list.getName()));


        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText edit = ((AlertDialog) dialog).findViewById(R.id.dialog_single_input);
                String input = edit.getText().toString();

                list.setName(input);

                DataMigration.getFactory().getListDao().update(list);

                adapter.notifyDataSetChanged();

                ManageListsActivity.datahasChanged = true;
                dialog.dismiss();
            }
        });

        String cancel = activity.getResources().getString(R.string.cancel);
        builder.setNegativeButton(cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    /**
     * Create filter dialog.
     * @author Lars Akkermans
     * @param activity   the activity
     * @param yearQuery  the year query
     * @param genreQuery the genre query
     */
    public static void createFilterDialog(AppCompatActivity activity, String yearQuery, String genreQuery) {
        Filter yearFilter = ((ListActivity) activity).getFilter(FilterType.YEAR_FILTER);
        Filter genreFilter = ((ListActivity) activity).getFilter(FilterType.GENRE_FILTER);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.filter_title);

        View filterView = getView(Input.EDITTEXT_WITH_LABEL, activity, null);
        builder.setView(filterView);
        ((TextView) filterView.findViewById(R.id.dialog_year_label)).setText(filterView.getResources().getString(R.string.movieYear));
        SearchView editTextYear = filterView.findViewById(R.id.dialog_year_input);
        ((TextView) filterView.findViewById(R.id.dialog_genre_label)).setText(filterView.getResources().getString(R.string.movieGenre));
        SearchView editTextGenre = filterView.findViewById(R.id.dialog_genre_input);

        // set up the query listeners
        editTextYear.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                yearFilter.filter(newText);
                ((ListActivity) activity).setYearFilterQuery(newText);
                return false;
            }
        });

        editTextGenre.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                genreFilter.filter(newText);
                ((ListActivity) activity).setGenreFilterQuery(newText);
                return false;
            }
        });

        editTextYear.setQuery(yearQuery, false);
        editTextGenre.setQuery(genreQuery, false);


        Log.d("DIALOGBUILDERRRR", "YEAR QUERYYYYYY __> " + yearQuery + " , GENRE QUERYYYYYYYYY___> " + genreQuery);

        // set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String yearValue = editTextYear.getQuery().toString();
                String genre = editTextGenre.getQuery().toString();
                if ( yearValue.isEmpty() && genre.isEmpty()) {
                    yearFilter.filter(yearValue);
                    genreFilter.filter(genre);
                }
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
