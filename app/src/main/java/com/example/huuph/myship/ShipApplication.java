package com.example.huuph.myship;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class ShipApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}
