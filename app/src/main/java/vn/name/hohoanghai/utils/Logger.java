package vn.name.hohoanghai.utils;

import timber.log.Timber;

public class Logger {
    public static void d(Throwable e) {
        Timber.d(e);
    }

    public static void d(String log) {
        Timber.d(log);
    }

    public static void d(String tag, String log) {
        Timber.d("TAG: " + tag + "\nLOG: " + log);
    }

    public static void d(String tag, String log, Throwable e) {
        Timber.d(e, "TAG: " + tag + "\nLOG: " + log);
    }


    public static void e(Throwable e) {
        Timber.e(e);
    }

    public static void e(String log) {
        Timber.e(log);
    }

    public static void e(String tag, String log) {
        Timber.e("TAG: " + tag + "\nLOG: " + log);
    }

    public static void e(String tag, String log, Throwable e) {
        Timber.e(e, "TAG: " + tag + "\nLOG: " + log);
    }

    public static void i(Throwable e) {
        Timber.i(e);
    }

    public static void i(String log) {
        Timber.i(log);
    }

    public static void i(String tag, String log) {
        Timber.i("TAG: " + tag + "\nLOG: " + log);
    }

    public static void i(String tag, String log, Throwable e) {
        Timber.i(e, "TAG: " + tag + "\nLOG: " + log);
    }
}