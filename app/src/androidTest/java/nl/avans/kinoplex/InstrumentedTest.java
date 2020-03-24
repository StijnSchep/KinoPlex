package nl.avans.kinoplex;

import android.net.Uri;
import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import nl.avans.kinoplex.business.FirestoreUtils;
import nl.avans.kinoplex.business.JsonUtils;
import nl.avans.kinoplex.domain.Constants;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class InstrumentedTest {

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
                .addOnSuccessListener(InstrumentedTest::onSuccess)
                .addOnFailureListener(aVoid -> fail("Failed to write to Firestore"));
    }

    @Test
    public void jsonIsParsedProperly() throws JSONException {
        String sampleJsonUrl = "https://avans.studyhost.nl/JavaDoc%20Kinoplex/units/mock_json.json";
        Uri sampleJsonUri = Uri.parse(sampleJsonUrl);
        JSONObject result = JsonUtils.getJSONObjectFromUrl(sampleJsonUri);

        JSONArray resultJSONArray = result.getJSONArray("results");
        assertEquals(10, resultJSONArray.length());
        if (resultJSONArray.length() != 10) fail();

        for (int i = 0; i < 10; i++) {
            JSONObject object = resultJSONArray.getJSONObject(i);
            assertTrue(parseJsonObject(object));
        }
    }

    private boolean parseJsonObject(JSONObject object) {
        try {
            object.getString("_id");
            object.getInt("index");
            object.getString("guid");
            return true;
        } catch (JSONException ex) {
            return false;
        }
    }

    @After
    public void cleanUp() {
        db.collection(Constants.COL_GENRES).document("-10").delete();
    }
}
