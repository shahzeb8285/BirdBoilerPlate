package in.birdcommunication.core;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.google.firebase.FirebaseApp;

import in.birdcommunication.core.Models.DaoMaster;
import in.birdcommunication.core.Models.DaoSession;
import in.birdcommunication.core.Utils.MySQLiteOpenHelper;

public class BirdCore {
    private static volatile BirdCore sSoleInstance;
    public String TAG = getClass().getName();
    private Context context;
    private DaoSession mDaoSession;
    private BirdCore(){
        if (sSoleInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }
    public void build(Context context){
        this.context = context;
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(context, "data.db",
                null);

        mDaoSession = new DaoMaster(helper.getWritableDatabase()).newSession();

//        if(AuthManager.getInstance().isLoggedIn()){
//            if(!SharedPrefManager.getInstance(this).isAccountLoaded()){
//                Log.i("989797", "onCreate:76 ");
//            }else if(SharedPrefManager.getInstance(this).isNeedToUpdateToken()){
//                UserManager.getInstance().updateToken();
//            }
//        }

        try {
            ProviderInstaller.installIfNeeded(context);

        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }

    }
    public static BirdCore getInstance() {
        if (sSoleInstance == null) { //if there is no instance available... create new one
            synchronized (BirdCore.class) {
                if (sSoleInstance == null) sSoleInstance = new BirdCore();
            }
        }

        return sSoleInstance;
    }
    public  Context getContext() {
        return context;
    }
    public  DaoSession getDaoSession() {
        return mDaoSession;
    }


    public void deleteAllData() {
    }
}
