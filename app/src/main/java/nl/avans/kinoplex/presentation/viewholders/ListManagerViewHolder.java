package nl.avans.kinoplex.presentation.viewholders;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.business.DialogBuilder;
import nl.avans.kinoplex.data.factories.DataMigration;
import nl.avans.kinoplex.domain.Constants;
import nl.avans.kinoplex.domain.DomainObject;

import nl.avans.kinoplex.domain.MovieList;
import nl.avans.kinoplex.presentation.activities.ManageListsActivity;
import nl.avans.kinoplex.presentation.adapters.AbstractAdapter;
import nl.avans.kinoplex.presentation.adapters.ListManagerAdapter;

/**
 * @author Stijn Schep
 * ViewHolder for the ListManagerAdapter
 */
public class ListManagerViewHolder extends AbstractViewHolder implements View.OnClickListener {

    private TextView m_ListTitleTextView;

    private Button m_EditButton;
    private Button m_DeleteButton;

    private MovieList list;

    private Activity activity;
    private RecyclerView.Adapter adapter;

    public ListManagerViewHolder(@NonNull View itemView) {
        super(itemView);

        m_ListTitleTextView = itemView.findViewById(R.id.list_manage_title);

        m_EditButton = itemView.findViewById(R.id.list_manage_edit);
        m_DeleteButton = itemView.findViewById(R.id.list_manage_delete);
        m_EditButton.setOnClickListener(this);
        m_DeleteButton.setOnClickListener(this);
    }

    public void bind(DomainObject object) {
        Log.d(Constants.LISTMANAGERVH_TAG, "Binding movie to list, object: " + object);

        MovieList list = (MovieList) object;
        this.list = list;

        m_ListTitleTextView.setText(list.getName());
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setAdapter(RecyclerView.Adapter adapter) {this.adapter = adapter;}



    @Override
    public void onClick(View v) {
        String title;
        switch (v.getId()) {
            case R.id.list_manage_edit:
                Log.d(Constants.LISTMANAGERVH_TAG, "User wants to edit the title of list " + list.getName());
                title = v.getResources().getString(R.string.enterTitle);
                DialogBuilder.simpleListEditDialog(activity, title,
                        DialogBuilder.Input.PREFILLED_EDITTEXT, list, adapter);

                break;

            case R.id.list_manage_delete:
                Log.d(Constants.LISTMANAGERVH_TAG, "User wants to delete list  " + list.getName());
                title = v.getResources().getString(R.string.deleteList);

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle(title);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataMigration.getFactory().getListDao().delete(list);
                        ((AbstractAdapter) adapter).deleteFromDataSet(list);

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
                break;


        }
    }
}
