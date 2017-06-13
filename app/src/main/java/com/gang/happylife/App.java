package com.gang.happylife;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2017/6/5.
 */

public class App extends Application {
    private static App app;

    public static Context getAppContext() {
        return app;
    }
}
