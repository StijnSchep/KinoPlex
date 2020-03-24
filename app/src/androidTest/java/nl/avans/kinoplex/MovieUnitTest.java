package nl.avans.kinoplex;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Test;
import org.junit.runner.RunWith;

import nl.avans.kinoplex.business.FirestoreUtils;
import nl.avans.kinoplex.domain.Constants;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MovieUnitTest {
    @Test
    public void checkIfMovieTitleIsSameAsMovieFromDb() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        FirebaseFirestore db = FirestoreUtils.getInstance();

        db.collection(Constants.COL_MOVIES).document("12477").get().addOnSuccessListener(documentSnapshot -> {
            String movieTitle = documentSnapshot.getString("title");
            assertEquals("Grave of the Fireflies", movieTitle);
        });
    }
}