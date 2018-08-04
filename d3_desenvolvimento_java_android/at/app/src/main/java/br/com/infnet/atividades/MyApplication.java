package br.com.infnet.atividades;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Magno on 20/11/2017.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
