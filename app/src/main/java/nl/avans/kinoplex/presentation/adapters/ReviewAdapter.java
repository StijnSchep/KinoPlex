package nl.avans.kinoplex.presentation.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.business.FirestoreUtils;
import nl.avans.kinoplex.domain.Constants;
import nl.avans.kinoplex.domain.DomainObject;
import nl.avans.kinoplex.domain.FireReview;
import nl.avans.kinoplex.domain.TMDbReview;
import nl.avans.kinoplex.presentation.viewholders.ReviewViewHolder;

/**
 * The type Review adapter.
 */
public class ReviewAdapter extends AbstractAdapter<ReviewViewHolder> {

    /**
     * Instantiates a new Review adapter.
     *
     * @param dataSet the data set
     */
    public ReviewAdapter(List<DomainObject> dataSet) {
        super(dataSet);
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View listRow = inflater.inflate(R.layout.reviewapp_recyclelist_row, viewGroup, false);
        return new ReviewViewHolder(listRow);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder reviewViewHolder, int i) {
        Object review = getDataSet().get(i);
        if (review instanceof TMDbReview) {
            TMDbReview tmDbReview = (TMDbReview) review;
            reviewViewHolder.getReviewUser().setText(tmDbReview.getAuthor());
            reviewViewHolder.getReviewContent().setText(Html.fromHtml(tmDbReview.getContent(), Html.FROM_HTML_MODE_COMPACT));
        } else if (review instanceof FireReview) {
            FireReview fireReview = (FireReview) review;
            reviewViewHolder.getReviewUser().setText("Anonymous");
            FirestoreUtils.getInstance().collection(Constants.COL_USERS).document(fireReview.getUserId()).get().addOnSuccessListener(documentSnapshot -> {
                reviewViewHolder.getReviewUser().setText(documentSnapshot.getString("fullname"));
            });
            reviewViewHolder.getReviewContent().setText(Html.fromHtml(fireReview.getContent(), Html.FROM_HTML_MODE_COMPACT));
        }
    }

    @Override
    public int getItemCount() {
        return getDataSet().size();
    }
}
