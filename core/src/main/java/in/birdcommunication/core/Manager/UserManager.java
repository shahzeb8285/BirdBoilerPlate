package in.birdcommunication.core.Manager;

public class UserManager {
    private static volatile UserManager sSoleInstance;

    //private constructor.
    private UserManager(){

        //Prevent form the reflection api.
        if (sSoleInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static UserManager getInstance() {
        if (sSoleInstance == null) { //if there is no instance available... create new one
            synchronized (UserManager.class) {
                if (sSoleInstance == null) sSoleInstance = new UserManager();
            }
        }

        return sSoleInstance;
    }

    public void updateToken(){
//        String uid = SharedPrefManager.getInstance(Application.getContext()).getUid();
//        String token = SharedPrefManager.getInstance(ClassManager.getContext()).getToken();
//        if(!uid.isEmpty() &&  AuthManager.getInstance().isLoggedIn()){
//
//            if(token.isEmpty()){
//                FirebaseInstanceId.getInstance().getInstanceId()
//                        .addOnCompleteListener(task -> {
//                            if (task.isSuccessful()) {
//                                String token1 = task.getResult().getToken();
//                                SharedPrefManager.getInstance(ClassManager.getContext()).setToken(token1);
//
//                                String url = Constant.API_URL + "updateToken/"+uid+"/"+token1;
//                                ApiManager.getInstance(ClassManager.getContext()).makeGetReq(url,
//                                        new ApiManager.Response() {
//                                            @Override
//                                            public void onSuccess(JSONArray data) {
//                                                SharedPrefManager.getInstance(ClassManager.getContext()).setNeedToUpdateToken(false);
//                                            }
//
//                                            @Override
//                                            public void onNetworkError() {
//
//                                            }
//
//                                            @Override
//                                            public void onError(String error, int code) {
//
//                                            }
//                                        });
//
//
//                            }
//                        });
//
//            }else{
//
//                String url = Constant.API_URL + "updateToken/"+uid+"/"+token;
//
//                ApiManager.getInstance(ClassManager.getContext()).makeGetReq(url,  new ApiManager.Response() {
//                    @Override
//                    public void onSuccess(JSONArray data) {
//                        SharedPrefManager.getInstance(ClassManager.getContext()).setNeedToUpdateToken(false);
//                    }
//
//                    @Override
//                    public void onNetworkError() {
//
//                    }
//
//                    @Override
//                    public void onError(String error, int code) {
//
//                    }
//                });
//            }
//
//
//        }
    }

}
