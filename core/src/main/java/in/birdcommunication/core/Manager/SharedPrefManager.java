package in.birdcommunication.core.Manager;

import android.content.Context;
import android.content.SharedPreferences;


import in.birdcommunication.core.Utils.Constants;

import static in.birdcommunication.core.Utils.Constants.GENDER;
import static in.birdcommunication.core.Utils.Constants.SETTINGS_NAME;
import static in.birdcommunication.core.Utils.Constants.USER_NAME;

public class SharedPrefManager {
    private static volatile SharedPrefManager sSoleInstance;
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;




    //private constructor.
    private SharedPrefManager(Context context){
        if (sSoleInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
//        mPref =new SecurePreferences(context,Constant.ADMINPHONE, SETTINGS_NAME);

        mPref = context.getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE);

    }

    public static SharedPrefManager getInstance(Context context) {
        if (sSoleInstance == null) { //if there is no instance available... create new one
            synchronized (SharedPrefManager.class) {
                if (sSoleInstance == null) sSoleInstance = new SharedPrefManager(context.getApplicationContext());
            }
        }
        return sSoleInstance;
    }

    public void setName(String name){
        doEdit();
        mEditor.putString(USER_NAME, name.trim());
        doCommit();
    }
    public String getName(){
        return mPref.getString(USER_NAME, "");
    }

    public void setGender(int gender){
        doEdit();
        mEditor.putInt(GENDER, gender);
        doCommit();
    }
    public int getGender(){
        return mPref.getInt(GENDER, -1);

    }


    private void doEdit() {
        if (mEditor == null) {
            mEditor = mPref.edit();
        }
    }

    private void doCommit() {
        if (mEditor != null) {
            mEditor.commit();
            mEditor = null;
        }
    }




    public void setItem(String key, String value){
        doEdit();
        mEditor.putString(key, value);
        doCommit();
    }

    public void setItem(String key, int value){
        doEdit();
        mEditor.putInt(key, value);
        doCommit();
    }









    public int getUserType(){
       return mPref.getInt(Constants.USER_TYPE, -1);

    }


    public String getUid(){
        return mPref.getString(Constants.UID, "");

    }

    public void setUid(String uid){
        doEdit();
        mEditor.putString(Constants.UID, uid);
        doCommit();

    }

    public boolean isAccountLoaded(){
        return mPref.getBoolean(Constants.IS_ACCOUNT_LOADED, false);

    }


    public void setAccountLoaded(boolean isLoaded){
        doEdit();
        mEditor.putBoolean(Constants.IS_ACCOUNT_LOADED, isLoaded);
        doCommit();
    }

    public void setUserType(int userType){
        doEdit();
        mEditor.putInt(Constants.USER_TYPE, userType);
        doCommit();
    }


    public void setToken(String s) {
        doEdit();
        mEditor.putString(Constants.PUSH_TOKEN, s);
        doCommit();
    }

    public String getToken(){
        return mPref.getString(Constants.PUSH_TOKEN, "");

    }

    public boolean isNeedToUpdateToken(){
        return mPref.getBoolean(Constants.SHOULD_UPDATE_TOKEN, true);
    }


    public void setNeedToUpdateToken(boolean needUpdate){
        doEdit();
        mEditor.putBoolean(Constants.SHOULD_UPDATE_TOKEN, needUpdate);
        doCommit();
    }


    public String getPhone() {
        return mPref.getString(Constants.PHONE_NUMBER, "");
    }

    public void setPhoneNumber(String phone){
        doEdit();
        mEditor.putString(Constants.PHONE_NUMBER, phone);
        doCommit();

    }


    public void  clearAllData(){
        doEdit();
        mEditor.clear();
        doCommit();
    }




}

