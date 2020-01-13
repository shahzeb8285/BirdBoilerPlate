package in.birdcommunication.core.Manager;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.Objects;
import in.birdcommunication.core.R;

public class DialogManager {
    private static volatile DialogManager sSoleInstance;
    private Dialog mLoadingDialog;
    //private constructor.
    private DialogManager(){

        //Prevent form the reflection api.
        if (sSoleInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static DialogManager getInstance() {
        if (sSoleInstance == null) { //if there is no instance available... create new one
            synchronized (DialogManager.class) {
                if (sSoleInstance == null) sSoleInstance = new DialogManager();
            }
        }

        return sSoleInstance;
    }

    public AlertDialog.Builder showOptionDialog(Context context, String title, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton("Continue", null);
        builder.setNegativeButton("Cancel", null);
        return builder;

    }

    public void makeProgressDialoge(Context context, String msg){
        mLoadingDialog = new Dialog(context);
        mLoadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mLoadingDialog.setContentView(R.layout.progress_with_message);
        TextView textView = Objects.requireNonNull(mLoadingDialog.getWindow()).findViewById(R.id.title);
        if(textView !=null){
            textView.setText(msg);
        }
        if(mLoadingDialog.getWindow()!=null){
            mLoadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        mLoadingDialog.setCancelable(false);
        mLoadingDialog.show();
    }
    public void hideProgressDialoge(){
        if (mLoadingDialog != null){
            mLoadingDialog.cancel();
            mLoadingDialog.dismiss();
        }

        mLoadingDialog = null;
    }
}
