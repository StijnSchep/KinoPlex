package nl.avans.kinoplex.data.dataaccessobjects;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import nl.avans.kinoplex.business.FirestoreUtils;
import nl.avans.kinoplex.data.factories.DataMigration;
import nl.avans.kinoplex.domain.FireReview;
import nl.avans.kinoplex.domain.Constants;
import nl.avans.kinoplex.domain.DomainObject;
import nl.avans.kinoplex.domain.Movie;
import nl.avans.kinoplex.domain.Review;
import nl.avans.kinoplex.domain.TMDbReview;
import nl.avans.kinoplex.presentation.activities.DetailActivity;
import nl.avans.kinoplex.presentation.adapters.AbstractAdapter;

/** The type Firestore review dao. */
public class FirestoreReviewDao implements DaoObject<Review> {

    private int movieId;
    private FirebaseFirestore db;

  /**
   * @author Guus Lieben
   * Instantiates a new Firestore review dao.
   *
   * @param movieId the movie id
   */
  public FirestoreReviewDao(int movieId) {
        this.db = FirestoreUtils.getInstance();
        this.movieId = movieId;
    }

    @Override
    public boolean create(Review review) {
        Log.d(Constants.FIRESTOREREVIEWDAO_TAG, "Attempting to create review on Firestore");
        String id = "$NE";
        if (review instanceof FireReview) {
            Log.d(Constants.FIRESTOREREVIEWDAO_TAG, "Instance of FireReview detected, checking ID");
            if (((FireReview) review).getId() == null) {
                Log.d(Constants.FIRESTOREREVIEWDAO_TAG, "ID was null, requesting auto-id from Firestore");
                id = db.collection(Constants.COL_REVIEWS).document().getId();
            } else id = ((FireReview) review).getId();
        }
        Log.d(Constants.FIRESTOREREVIEWDAO_TAG, "Writing to ID : " + id);
        ((FireReview) review).setId(id);
        if (!id.equals("$NE"))
            db.collection(Constants.COL_REVIEWS).document(id).set(((DomainObject) review).storeToMap());
        return true;
    }

    @Override
    public void readIntoAdapter(RecyclerView.Adapter adapter) {
        Log.d(Constants.FIRESTOREREVIEWDAO_TAG, "Attempting to read from Firestore");
        db.collection(Constants.COL_REVIEWS)
                .get()
                .addOnSuccessListener(
                        documentSnapshot -> {
                            String mId = String.valueOf(movieId);
                            List<Object> references = new ArrayList<>();
                            for (DocumentSnapshot snapshot : documentSnapshot.getDocuments()) {
                                Log.d(Constants.FIRESTOREREVIEWDAO_TAG, "Checking document snapshot : " + snapshot.getData());
                                if (Objects.requireNonNull(snapshot.getString("movie_id")).equals(mId))
                                    references.add(snapshot);
                            }
                            Log.d(Constants.FIRESTOREREVIEWDAO_TAG, "Writing references to adapter");
                            writeReferencesToAdapter(references, adapter);
                        });
    }

    /**
     * Writes a list of Document References into the given adapter
     *
     * @author Guus Lieben
     * @param referenceList the List with Document References
     * @param adapter the adapter to write to
     */
    private void writeReferencesToAdapter(List<Object> referenceList, RecyclerView.Adapter adapter) {
        for (Object reviewReference : referenceList) {
            Log.d(Constants.FIRESTOREREVIEWDAO_TAG, "Attempting to write reference to adapter : " + reviewReference.toString());
            db.collection(Constants.COL_REVIEWS)
                    .document(String.valueOf(((DocumentSnapshot) reviewReference).getId()))
                    .get()
                    .addOnSuccessListener(
                            documentSnapshot -> {
                                Log.d(Constants.FIRESTOREREVIEWDAO_TAG, "Reference data was : " + documentSnapshot.getData());
                                if (documentSnapshot.getString("user_id") != null) { // App review
                                    Log.d(Constants.FIRESTOREREVIEWDAO_TAG, "Detected FireReview");
                                    String id = documentSnapshot.getId();
                                    String author = documentSnapshot.getString("user_id");
                                    String content = documentSnapshot.getString("content");
                                    long rating = (long) documentSnapshot.get("rating");
                                    String reviewMovieID = documentSnapshot.getString("movie_id");
                                    int intRating = Math.round(rating);
                                    FireReview fireReview = new FireReview(id, author, content, intRating, reviewMovieID);
                                    ((AbstractAdapter) adapter).addToDataSet(fireReview);

                                } else { // API Review
                                    Log.d(Constants.FIRESTOREREVIEWDAO_TAG, "Detected TMDb Review");
                                    String id = documentSnapshot.getId();
                                    String author = documentSnapshot.getString("author");
                                    String content = documentSnapshot.getString("content");
                                    TMDbReview tmDbReview = new TMDbReview(id, author, content);
                                    ((AbstractAdapter) adapter).addToDataSet(tmDbReview);
                                }
                            });
        }
    }

    @Override
    public void readIntoIntent(Intent intent, Context context, Object id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void readAll(RecyclerView.Adapter adapter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean update(Review review) {
        Log.d(Constants.FIRESTOREREVIEWDAO_TAG, "Attempting to write to Firestore");
        db.collection(Constants.COL_REVIEWS)
                .document(((DomainObject) review).getId())
                .set(((DomainObject) review).storeToMap())
                .addOnSuccessListener(
                        aVoid -> Log.d(Constants.FIRESTOREREVIEWDAO_TAG, "Successfully updated review"));
        return true;
    }

    @Override
    public boolean delete(Review review) {
        Log.d(Constants.FIRESTOREREVIEWDAO_TAG, "Attempting to delete from Firestore");
        db.collection(Constants.COL_REVIEWS).document(((DomainObject) review).getId()).delete();
        return true;
    }

  /**
   * @author Guus Lieben
   * Gets the list of reviews for a given movie
   *
   * @param movie the movie to collect reviews for
   * @param detailActivity the instance of the Detail Activity carrying the movie
   */
  public void getList(Movie movie, DetailActivity detailActivity) {
        Log.d(Constants.FIRESTOREREVIEWDAO_TAG, "Attempting to read list of reviews for movie");
        db.collection(Constants.COL_REVIEWS)
                .get()
                .addOnSuccessListener(
                        snapshots -> {
                            movie.setReviews(new ArrayList<>());
                            movie.setFireReviews(new ArrayList<>());

                            movie.setReviews(
                                    ((TMDbReviewDao)
                                            DataMigration.getTMDbFactory()
                                                    .getReviewDao(Integer.parseInt(movie.getId())))
                                            .getList());


                            for (DocumentSnapshot documentSnapshot : snapshots.getDocuments()) {
                                Log.d(Constants.FIRESTOREREVIEWDAO_TAG, "Checking snapshot : " + documentSnapshot.getData());
                                if (Objects.requireNonNull(documentSnapshot.getString("movie_id"))
                                        .equalsIgnoreCase(String.valueOf(movieId))) {
                                    Log.d(Constants.FIRESTOREREVIEWDAO_TAG, "Found matching snapshot, constructing");
                                    if (documentSnapshot.contains("user_id")) { // App review
                                        String id = documentSnapshot.getId();
                                        String author = documentSnapshot.getString("user_id");
                                        String content = documentSnapshot.getString("content");
                                        long rating = (long) documentSnapshot.get("rating");
                                        String reviewMovieID = documentSnapshot.getString("movie_id");
                                        int intRating = Math.round(rating);
                                        FireReview fireReview = new FireReview(id, author, content, intRating, reviewMovieID);
                                        Log.d(Constants.FIRESTOREREVIEWDAO_TAG, "Constructed, adding to movie");
                                        movie.addAppReview(fireReview);
                                    }
                                }
                            }

                            Log.d(Constants.FIRESTOREREVIEWDAO_TAG, "TMDb Reviews : " + movie.getReviews());
                            Log.d(Constants.FIRESTOREREVIEWDAO_TAG, "Fire Reviews : " + movie.getFireReviews());

                            int reviewAmount = movie.getReviews().size() + movie.getFireReviews().size();

                            Log.d(Constants.FIRESTOREREVIEWDAO_TAG, "Updating amount to : " + String.valueOf(reviewAmount));
                            detailActivity.setReviewText(String.valueOf(reviewAmount));
                        });
    }
}
