package elaborato_ingegneriaSW.utils;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FirebaseConnection {

    private final static String keyFilename = "elaborato-ingegneria-firebase-adminsdk-d4slt-8e45d61e58.json";
    private static Firestore conn;

    private FirebaseConnection() {}

    public static Firestore getConnection() {
        if (conn == null) {
            try {
                InputStream serviceAccount = new FileInputStream(keyFilename);
                GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(credentials)
                        .build();
                FirebaseApp.initializeApp(options);

                conn = FirestoreClient.getFirestore();
            } catch (IOException e) {
                System.err.println("Firebase connection error: " + e.getMessage());
                System.exit(1);
            }
        }

        return conn;
    }
}
