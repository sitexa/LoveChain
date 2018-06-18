package com.sitexa.lovechain.utils;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.sitexa.lovechain.R;

/**
 * Created by sitexa on 2018/6/19.
 */

public class ThemeUtil {
    public ThemeUtil() {
    }

    public static void setTheme(@NonNull Activity activity) {
        if (Utils.getSpUtils().getString("loginmode") != null) {
            boolean isLight = Utils.getSpUtils().getString("loginmode").equals("phone");
            activity.setTheme(isLight ? R.style.ThemeLight : R.style.ThemeDark);
        }
    }

    public static void reCreate(@NonNull final Activity activity) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.recreate();
            }
        });

    }
}

