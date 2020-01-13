package in.birdcommunication.core.UI.Base;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import in.birdcommunication.core.Manager.DialogManager;

public abstract class
BaseActivity extends AppCompatActivity implements View.OnClickListener {


    public abstract void initViews();


    private void checkRoute(){

    }




    public void makeFullScreenActivity(){
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }



    public Drawable getIcon(@DrawableRes int drawbleId){
        return getResources().getDrawable(drawbleId);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        DialogManager.getInstance().hideProgressDialoge();
    }

    public void makeProgressDialog(String msg){
        DialogManager.getInstance().makeProgressDialoge(this,msg);

    }

    public void hideProgressDialog(){
        DialogManager.getInstance().hideProgressDialoge();

    }


}
