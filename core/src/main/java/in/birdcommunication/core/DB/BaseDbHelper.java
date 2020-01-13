package in.birdcommunication.core.DB;


import java.util.List;


import in.birdcommunication.core.BirdCore;
import in.birdcommunication.core.Models.DaoSession;


public abstract class BaseDbHelper<T> {
    private Class<T> aClass;
    public String TAG = getClass().getName();
    public BaseDbHelper(Class<T> aClass){
        this.aClass = aClass;
    }


    public abstract void addItem(T item);
    public abstract  boolean checkIfExists(T item);
    public  abstract List<T> getAllItems();


    public abstract void removeItem(T item);

    public abstract void addItems(List<T> items);

    public DaoSession getDaoSession(){
        return BirdCore.getInstance().getDaoSession();
    }

}

