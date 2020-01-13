package in.birdcommunication.auth;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import in.birdcommunication.auth.Components.PinView;
import in.birdcommunication.auth.Manager.AuthManager;
import in.birdcommunication.core.Manager.ToastManager;
import in.birdcommunication.core.UI.Base.BaseActivity;

public class RegisterActivity extends BaseActivity implements View.OnClickListener,
        AuthManager.VerificationCallBack {

    private TextView phoneTv,verifyButton;
    private String phoneNumber;
    private Dialog otpDialog;
    private CountDownTimer countDownTimer;
    private boolean isCountDownFinished;
    private PinView pinView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//        getWindow().getDecorView().setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window w = getWindow();
//            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }


        makeFullScreenActivity();
        ImageView headerImg = findViewById(R.id.headerImg);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            headerImg.getClipToOutline();
        }
        initViews();
    }



    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.verify){
            handleVerifyClick();
        }
    }

    private void handleVerifyClick() {
        String error = validate();
        if(error == null){
            phoneNumber = "+91"+phoneTv.getText().toString();
            AuthManager.getInstance().sendVerificationCode(phoneNumber,this,this);
        }else {
            ToastManager.getInstance().showErrorToast(error);
        }
    }

    private String validate() {
        if(phoneTv.getText().toString().isEmpty() ||
                phoneTv.getText().toString().length()<10){
            return "Please Enter Valid Phone Number!";
        }
        return null;
    }

    @Override
    public void onCodeSent() {
        ToastManager.getInstance().showSuccessToast("OTP sent to your phone number!",true);
        showOtpDialoge();

    }


    private void showOtpDialoge(){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.otp_verification_dialoge, null);
        dialogBuilder.setView(dialogView);

        dialogView.findViewById(R.id.back).setOnClickListener(view -> hideOTPDialog());
        TextView otp_subtitle = dialogView.findViewById(R.id.dialog_subtitle);
        otp_subtitle.setText(String.format(
                getResources().getString(R.string.otp_dialoge_subtitle),phoneTv.getText().toString()));
        TextView resendTitle = dialogView.findViewById(R.id.resend_code);
        pinView = dialogView.findViewById(R.id.pinView);
        pinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() == 6){
                    AuthManager.getInstance().verifyOTP(pinView.getText().toString());
                }
            }
        });
        resendTitle.setOnClickListener(this);
        isCountDownFinished = false;
        countDownTimer =new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                int remainingSec = (int) (millisUntilFinished/1000);
                resendTitle.setText("("+remainingSec+")"+"Resend");
            }

            public void onFinish() {
                resendTitle.setText("Resend");
                isCountDownFinished = true;
            }

        }.start();
        otpDialog = dialogBuilder.create();
        otpDialog.show();
        Window window = otpDialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        window.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

    }
    private void hideOTPDialog() {
        if(otpDialog != null){
            otpDialog.cancel();
            otpDialog.dismiss();
            otpDialog = null;
        }
        if(countDownTimer !=null){
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    @Override
    public void onFailed(String error) {
        ToastManager.getInstance().showErrorToast(error);
    }

    @Override
    public void onCodeReceived(String code) {
    }

    @Override
    public void onVerificationDone() {
        ToastManager.getInstance().showSuccessToast("Phone Number Verified!",true);

    }

    @Override
    public void initViews() {
        phoneTv = findViewById(R.id.phone);
        findViewById(R.id.verify).setOnClickListener(this);
        phoneTv.requestFocus();
    }
}
