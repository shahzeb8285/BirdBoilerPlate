package in.birdcommunication.core.DB;

import java.util.List;


import in.birdcommunication.core.Models.User;
import in.birdcommunication.core.Models.UserDao;

public class UserDbHelper extends BaseDbHelper<User> {
    private static volatile UserDbHelper sSoleInstance;

    //private constructor.
    private UserDbHelper(){
        super(User.class);
        //Prevent form the reflection api.
        if (sSoleInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static UserDbHelper getInstance() {
        if (sSoleInstance == null) { //if there is no instance available... create new one
            synchronized (UserDbHelper.class) {
                if (sSoleInstance == null) sSoleInstance = new UserDbHelper();
            }
        }

        return sSoleInstance;
    }

    @Override
    public void addItem(User item) {
        if(checkIfExists(item)){
            getDao().update(item);
        }else{
            getDao().insert(item);
        }
    }

    @Override
    public boolean checkIfExists(User item) {
        User userItem = getOneItemByUID(item.getUid());
        return  userItem != null;
    }

    @Override
    public List<User> getAllItems() {
        return getDao().loadAll();
    }

    @Override
    public void removeItem(User item) {
        getDao().delete(item);
    }

    @Override
    public void addItems(List<User> items) {
        getDao().insertInTx(items);
    }



    public User getOneItemByPhone(String phone){
        List<User> list = getDao().queryBuilder()
                .where(UserDao.Properties.Phone.eq(phone)).build().list();
        if(list.isEmpty()){
            return null;
        }else{
            return  list.get(0);
        }

    }

    public User getOneItemByUID(String uid){
        List<User> list = getDao().queryBuilder()
                .where(UserDao.Properties.Uid.eq(uid)).build().list();
        if(list.isEmpty()){
            return null;
        }else{
            return  list.get(0);
        }

    }


    private UserDao getDao(){
        return getDaoSession().getUserDao();
    }
}
