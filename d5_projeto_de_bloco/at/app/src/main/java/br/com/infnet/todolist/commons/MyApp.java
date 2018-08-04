package br.com.infnet.todolist.commons;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Magno on 25/11/2017.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
