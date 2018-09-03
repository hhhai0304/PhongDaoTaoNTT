package vn.name.hohoanghai.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SettingUtils {
    private static SettingUtils settingUtils;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private SettingUtils(Context context) {
        preferences = context.getSharedPreferences("settingUtils", MODE_PRIVATE);
    }

    public static SettingUtils getInstance(Context context) {
        if (settingUtils == null) {
            settingUtils = new SettingUtils(context);
        }
        return settingUtils;
    }

    @SuppressLint("CommitPrefEdits")
    private void startEdit() {
        if (editor == null) {
            editor = preferences.edit();
        }
    }

    private void endEdit() {
        editor.apply();
        editor = null;
    }

    public void clear(String... keys) {
        startEdit();
        for (String key : keys) {
           editor.remove(key);
        }
        endEdit();
    }

    public void put(String key, Object value) {
        startEdit();
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (int) value);
        } else if (value instanceof Double || value instanceof Float) {
            editor.putFloat(key, (float) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (boolean) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (long) value);
        }
        endEdit();
    }

    public String getString(String key) {
        return preferences.getString(key, null);
    }

    public int getInt(String key) {
        return preferences.getInt(key, -1);
    }

    public double getDouble(String key) {
        return (double) preferences.getFloat(key, (float) -1.0);
    }

    public boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    public long getLong(String key) {
        return preferences.getLong(key, -1);
    }
}