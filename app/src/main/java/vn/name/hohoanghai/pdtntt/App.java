package vn.name.hohoanghai.pdtntt;

import android.app.Application;

import timber.log.Timber;
import vn.name.hohoanghai.utils.ReleaseTree;

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
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new ReleaseTree());
        }
    }
}