package in.birdcommunication.auth;

import android.content.Intent;

import com.google.firebase.FirebaseApp;

import in.birdcommunication.auth.Manager.AuthManager;
import in.birdcommunication.core.UI.Base.BaseActivity;

public abstract class RouteActivity extends BaseActivity {
    public static String TAG = "";


    public boolean isRouteNeed(){
        Intent intent = null;
//        if(!AuthManager.getInstance().isLoggedIn()){
//            intent = new Intent(this, RegisterActivity.class);
//            goToActivity(intent);
//            finish();
//        }

        return intent !=null;
    }

    public void goToActivity(Intent intent){
        startActivity(intent);
    }

}
