package com.velasquez.crud_firebasev1;

import com.google.firebase.database.FirebaseDatabase;

/**
 * The MiFirebaseApp class is a subclass of the android.app.Application class in Android.
 * Its purpose is to enable persistent storage for the Firebase Realtime Database in the Android application.
 */
public class MiFirebaseApp extends android.app.Application{

    /**
     * The onCreate() method is automatically called by the Android system when the application is launched.
     * This method does not take any direct input.
     */
    @Override
    public void onCreate() {
        // Call the superclass's onCreate() method
        super.onCreate();

        // Enable persistent storage for the Firebase Realtime Database
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
