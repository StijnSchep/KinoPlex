package nl.avans.kinoplex.presentation.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.business.CustomListChecker;
import nl.avans.kinoplex.domain.DomainObject;
import nl.avans.kinoplex.domain.MovieList;
import nl.avans.kinoplex.presentation.viewholders.ListManagerViewHolder;

/**
 * @author Stijn Schep
 * Adapter for the ManageListsActivity
 */
public class ListManagerAdapter extends AbstractAdapter<ListManagerViewHolder> {
    private Activity activity;

    public ListManagerAdapter(List<DomainObject> dataSet, Activity activity) {
        super(dataSet);

        this.activity = activity;
    }

    @NonNull
    @Override
    public ListManagerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutForListItem = R.layout.viewholder_list_manager;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutForListItem, viewGroup, false);

        return new ListManagerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListManagerViewHolder listManagerViewHolder, int i) {
        DomainObject movieList = getDataSet().get(i);

        listManagerViewHolder.setActivity(activity);
        listManagerViewHolder.setAdapter(this);

        listManagerViewHolder.bind(movieList);
    }

    @Override
    public void addToDataSet(DomainObject domainObject) {
        MovieList list = (MovieList) domainObject;

        if(CustomListChecker.isCustomList(list.getName())) {
            super.addToDataSet(domainObject);
        }
    }
}
