package end.multicalculator;

import android.app.Application;
import android.content.Context;

/**
 * call MyApplication.getAppContext() to get the application context statically
 */
public class MyApplication extends Application {

    private static Context context;

    public void onCreate(){
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}