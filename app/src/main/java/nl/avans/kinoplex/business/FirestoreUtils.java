package nl.avans.kinoplex.business;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import nl.avans.kinoplex.domain.Constants;

/** Basic utilities for Firestore tasks */
public class FirestoreUtils {

  /**
   * @author Guus Lieben
   *
   * @return The default instance of Firestore, generated with clean settings
   */
  public static FirebaseFirestore getInstance() {
    Log.d(Constants.FIRESTOREUTILS_TAG, "New instance of Firestore requested");
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseFirestoreSettings settings =
        new FirebaseFirestoreSettings.Builder().setTimestampsInSnapshotsEnabled(true).build();
    firestore.setFirestoreSettings(settings);
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    return db;
  }
}
