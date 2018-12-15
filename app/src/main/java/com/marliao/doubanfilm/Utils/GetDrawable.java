package com.marliao.doubanfilm.Utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.marliao.doubanfilm.engine.MyApplication;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class GetDrawable {

    private static URL url;
    private static Drawable drawable;

    public static Drawable getdrawable(final String path, final Context context) {
        new Thread() {
            @Override
            public void run() {
                try {
                    url = new URL(path);
                    InputStream inputStream = url.openStream();
                    drawable = Drawable.createFromResourceStream(context.getResources(), null, inputStream, "src", null);
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                super.run();
            }
        }.start();
        try {
            new Thread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return drawable;
    }

}
