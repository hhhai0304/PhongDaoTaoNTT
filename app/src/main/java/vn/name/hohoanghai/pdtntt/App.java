package vn.name.hohoanghai.pdtntt;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private volatile static App app;

    public static App getInstance() {
        if (app == null) {
            synchronized (App.class) {
                if (app == null) {
                    app = new App();
                }
            }
        }
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }
}