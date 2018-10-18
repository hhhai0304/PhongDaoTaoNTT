package vn.name.hohoanghai.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import timber.log.Timber;

public class ReleaseTree extends Timber.Tree {
    @Override
    protected void log(int priority, String tag, @NonNull String message, Throwable t) {
        if (priority == Log.ERROR || priority == Log.WARN) {
            Crashlytics.log(priority, tag, message);
        }
    }
}