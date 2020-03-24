package nl.avans.kinoplex;

import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import nl.avans.kinoplex.business.FirestoreUtils;
import nl.avans.kinoplex.domain.Constants;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class FirestoreUnitTest {

    private FirebaseFirestore db;

    private static void onSuccess(Void aVoid) {
        return;
    }

    @Before
    public void getInstance() {
        db = FirestoreUtils.getInstance();
    }

    @Test
    public void firestoreReadingPasses() {
        db.collection(Constants.COL_MOVIES).document("12477").get()
                .addOnSuccessListener(documentSnapshot -> {
                    String movieTitle = documentSnapshot.getString("title");
                    assertEquals("Grave of the Fireflies", movieTitle);
                }).addOnFailureListener(aVoid -> fail());
    }

    @Test
    public void firestoreWritingPasses() {
        Map<String, String> data = new HashMap<>();
        data.put("name", "unitTest");
        db.collection(Constants.COL_GENRES).document("-10").set(data)
                .addOnSuccessListener(FirestoreUnitTest::onSuccess)
                .addOnFailureListener(aVoid -> fail("Failed to write to Firestore"));
    }

    @After
    public void cleanUp() {
        db.collection(Constants.COL_GENRES).document("-10").delete();
    }
}