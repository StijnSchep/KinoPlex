package nl.avans.kinoplex.presentation.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.business.DialogBuilder;
import nl.avans.kinoplex.business.PosterPicker;
import nl.avans.kinoplex.data.dataaccessobjects.FirestoreListDao;
import nl.avans.kinoplex.data.factories.DataMigration;
import nl.avans.kinoplex.domain.Constants;
import nl.avans.kinoplex.presentation.adapters.ListManagerAdapter;

/**
 * @author Stijn Schep
 * Activity that shows a list of the current user's MovieLists and allows the user to manage them
 */
public class ManageListsActivity extends Activity implements View.OnClickListener {
    private Button returnButton;
    private Button addListButton;

    private RecyclerView manageListsRecyclerview;
    private ListManagerAdapter adapter;

    public static boolean datahasChanged = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_lists);

        datahasChanged = false;

        ImageView background = findViewById(R.id.iv_background_poster);
        Glide.with(this).load(PosterPicker.getRandomPosterID()).into(background);


        returnButton = findViewById(R.id.btn_manage_list_return);
        addListButton = findViewById(R.id.btn_manage_list_add);
        returnButton.setOnClickListener(this);
        addListButton.setOnClickListener(this);

        adapter = new ListManagerAdapter(new ArrayList<>(), this);
        manageListsRecyclerview = findViewById(R.id.rv_manage_lists);
        manageListsRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        manageListsRecyclerview.setAdapter(adapter);

        ((FirestoreListDao) DataMigration.getFactory().getListDao()).readCollectionsForCurrentUserToAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_manage_list_return:
                finish();
                break;

            case R.id.btn_manage_list_add:
                String title = getResources().getString(R.string.enterTitle);
                DialogBuilder.simpleInputBuilder(this, title, DialogBuilder.Input.SINGLE_EDITTEXT, adapter);

                break;
        }
    }
}
