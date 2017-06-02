package me.lancer.nodiseases.ui.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

import org.polaric.colorful.Colorful;

import java.lang.reflect.Field;

import me.lancer.nodiseases.R;

/**
 * Created by HuangFangzhi on 2016/12/15.
 */

public class mApp extends Application {

    public static Typeface TypeFace;
    private boolean isPicture, isFirst;

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.spf_user), Context.MODE_PRIVATE);
        boolean night = sharedPreferences.getBoolean(mParams.ISNIGHT, false);
//        if (night) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//        } else {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        }
        Colorful.defaults()
                .primaryColor(Colorful.ThemeColor.BLUE)
                .accentColor(Colorful.ThemeColor.BLUE)
                .translucent(false)
                .dark(night);
        Colorful.init(this);
        TypeFace = Typeface.createFromAsset(getAssets(), "fonts/MaterialIcons_Regular.ttf");
        try {
            Field field = Typeface.class.getDeclaredField("SERIF");
            field.setAccessible(true);
            field.set(null, TypeFace);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        setPicture(sharedPreferences.getBoolean("is_picture", true));
        setFirst(sharedPreferences.getBoolean("is_first", true));
    }

    public boolean isPicture() {
        return isPicture;
    }

    public void setPicture(boolean picture) {
        isPicture = picture;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }
}
