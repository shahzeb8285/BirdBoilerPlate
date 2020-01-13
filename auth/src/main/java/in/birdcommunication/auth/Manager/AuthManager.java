package in.birdcommunication.auth.Manager;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import in.birdcommunication.core.BirdCore;
import in.birdcommunication.core.DB.UserDbHelper;
import in.birdcommunication.core.Manager.DialogManager;
import in.birdcommunication.core.Models.DaoMaster;
import in.birdcommunication.core.Models.DaoSession;
import in.birdcommunication.core.Models.User;
import in.birdcommunication.core.Utils.MySQLiteOpenHelper;

public class AuthManager {
    private static volatile AuthManager sSoleInstance;
    public String TAG = getClass().getName();
    private VerificationCallBack verificationCallBack;
    private String mVerificationId;
    private  PhoneAuthProvider.ForceResendingToken mResendToken;
    private FirebaseAuth mAuth;
    private Activity activity;
    private  PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private AuthManager(){
        if (sSoleInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }



    public static AuthManager getInstance() {
        if (sSoleInstance == null) { //if there is no instance available... create new one
            synchronized (AuthManager.class) {
                if (sSoleInstance == null) sSoleInstance = new AuthManager();
            }
        }

        return sSoleInstance;
    }

    public boolean isLoggedIn(){
        if(FirebaseAuth.getInstance().getCurrentUser() !=null){
            String phone = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
            if(phone == null){
                return false;
            }
            User user = UserDbHelper.getInstance().getOneItemByPhone(phone);
            if(user != null){
                return true;
            }
        }
        return false;
    }
    public void resendOTP(String phoneNumber) {
        DialogManager.getInstance().makeProgressDialoge(activity,"Resending OTP...");
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                activity,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                mResendToken);             // ForceResendingToken from callbacks

    }

    public void sendVerificationCode(String mobile, Activity activity, VerificationCallBack callBack) {
        Log.i(TAG, "sendVerificationCode: ");
        DialogManager.getInstance().makeProgressDialoge(activity,"Sending OTP");
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                DialogManager.getInstance().hideProgressDialoge();
                String code = phoneAuthCredential.getSmsCode();
                if (code != null) {
//                verifyOTP(code);
                    if(verificationCallBack !=null){
                        verificationCallBack.onCodeReceived(code);
                    }
                }
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                DialogManager.getInstance().hideProgressDialoge();
                if(verificationCallBack !=null){
                    verificationCallBack.onFailed(e.getMessage());
                }

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                DialogManager.getInstance().hideProgressDialoge();
                if(verificationCallBack !=null){
                    verificationCallBack.onCodeSent();
                }
                mVerificationId = s;
                mResendToken = forceResendingToken;
            }
        };

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
        this.activity =activity;
        this.verificationCallBack = callBack;
    }


    public void verifyOTP(String otp) {
        DialogManager.getInstance().makeProgressDialoge(activity,"Verifying OTP...");
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        //verification successful we will start the profile activity
                        if(verificationCallBack !=null){
                            verificationCallBack.onVerificationDone();
                        }

                    } else {

                        //verification unsuccessful.. display an error message

                        String message = "Somthing is wrong, we will fix it soon...";

                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            message = "Invalid OTP entered";
                        }

                        if(verificationCallBack !=null){
                            verificationCallBack.onFailed(message);
                        }

                    }
                });
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
        BirdCore.getInstance().deleteAllData();
    }

    public String getName() {
        return "Shahzeb";

    }


    public interface VerificationCallBack{
        void onCodeSent();
        void onFailed(String error);
        void onCodeReceived(String code);
        void onVerificationDone();
    }

}
