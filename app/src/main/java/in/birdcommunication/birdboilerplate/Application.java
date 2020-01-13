package in.birdcommunication.birdboilerplate;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

import com.google.firebase.FirebaseApp;

import in.birdcommunication.core.BirdCore;

public class Application extends MultiDexApplication {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        BirdCore.getInstance().build(this);
        context = this;

    }

    public static Context getContext() {
        return context;
    }
}
