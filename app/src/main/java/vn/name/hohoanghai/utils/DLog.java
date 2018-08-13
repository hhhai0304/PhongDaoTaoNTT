package vn.name.hohoanghai.utils;

import timber.log.Timber;
import vn.name.hohoanghai.pdtntt.BuildConfig;

public class DLog {
    public static void i(String message) {
        if (BuildConfig.DEBUG) {
            Timber.i(message);
        }
    }

    public static void d(String message) {
        if (BuildConfig.DEBUG) {
            Timber.d(message);
        }
    }

    public static void e(String message) {
        if (BuildConfig.DEBUG) {
            Timber.e(message);
        }
    }
}