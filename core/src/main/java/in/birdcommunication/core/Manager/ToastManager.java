package in.birdcommunication.core.Manager;

import android.content.Context;

import es.dmoral.toasty.Toasty;
import in.birdcommunication.core.BirdCore;

public class ToastManager {


    private static volatile ToastManager sSoleInstance;

    //private constructor.
    private ToastManager(){

        //Prevent form the reflection api.
        if (sSoleInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static ToastManager getInstance() {
        if (sSoleInstance == null) { //if there is no instance available... create new one
            synchronized (ToastManager.class) {
                if (sSoleInstance == null) sSoleInstance = new ToastManager();
            }
        }

        return sSoleInstance;
    }


   public void showErrorToast(String msg){
       int duration = Toasty.LENGTH_LONG;
       Toasty.error(BirdCore.getInstance().getContext(),msg,duration).show();
   }
   public void showInfoToast(String msg, boolean isLong){
       int duration = Toasty.LENGTH_SHORT;
       if(isLong){
           duration = Toasty.LENGTH_LONG;
       }
       Toasty.info(BirdCore.getInstance().getContext(),msg,duration).show();

    }
    public void showWarningToast(String msg, boolean isLong){

        int duration = Toasty.LENGTH_SHORT;
        if(isLong){
            duration = Toasty.LENGTH_LONG;
        }
        Toasty.warning(BirdCore.getInstance().getContext(),msg,duration).show();
    }
    public void showSuccessToast(String msg, boolean isLong){
        int duration = Toasty.LENGTH_SHORT;
        if(isLong){
            duration = Toasty.LENGTH_LONG;
        }
        Toasty.success(BirdCore.getInstance().getContext(),msg,duration).show();
    }
    public void showNormalToast(Context context, String msg, boolean isLong){
        int duration = Toasty.LENGTH_SHORT;
        if(isLong){
            duration = Toasty.LENGTH_LONG;
        }
        Toasty.normal(BirdCore.getInstance().getContext(),msg,duration).show();
    }


}
