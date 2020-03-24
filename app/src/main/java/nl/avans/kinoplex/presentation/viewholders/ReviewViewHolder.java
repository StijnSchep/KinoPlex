package nl.avans.kinoplex.presentation.viewholders;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.presentation.views.ExpandableTextView;

import static nl.avans.kinoplex.domain.Constants.MAINMOVIEVH_TAG;

public class ReviewViewHolder extends AbstractViewHolder {

    private TextView reviewUser;
    private ExpandableTextView reviewContent;

    public ReviewViewHolder(@NonNull View itemView) {
        super(itemView);

        Log.d(MAINMOVIEVH_TAG, "ReviewViewHolder was created");

        reviewUser = itemView.findViewById(R.id.review_id);
        reviewContent = itemView.findViewById(R.id.review_content);
    }

    public TextView getReviewUser() {
        return reviewUser;
    }

    public TextView getReviewContent() {
        return reviewContent;
    }
}
