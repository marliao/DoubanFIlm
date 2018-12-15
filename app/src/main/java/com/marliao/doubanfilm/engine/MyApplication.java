package com.marliao.doubanfilm.engine;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.marliao.doubanfilm.vo.Douban;

public class MyApplication extends Application {

    private static Douban douban;

    public static Douban getDouban() {
        return douban;
    }

    public static void setDouban(Douban douban) {
        MyApplication.douban = douban;
    }

    public static Context context;
    public static Toast toast;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
    }

    public static void showToast(String test) {
        toast.setText(test);
        toast.show();
    }

    public static Context getContext() {
        return context;
    }

}
