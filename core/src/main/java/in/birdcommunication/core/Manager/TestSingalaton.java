package in.birdcommunication.core.Manager;

public class TestSingalaton {
    private static volatile TestSingalaton sSoleInstance;

    //private constructor.
    private TestSingalaton(){

        //Prevent form the reflection api.
        if (sSoleInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static TestSingalaton getInstance() {
        if (sSoleInstance == null) { //if there is no instance available... create new one
            synchronized (TestSingalaton.class) {
                if (sSoleInstance == null) sSoleInstance = new TestSingalaton();
            }
        }

        return sSoleInstance;
    }
}
