package com.marliao.doubanfilm.Utils;

import android.content.Context;
import android.content.UriMatcher;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import com.marliao.doubanfilm.Activity.MoveListActivity;
import com.marliao.doubanfilm.vo.Douban;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetDrawable {

    private static URL url;
    private static Drawable drawable;
    private static Bitmap bitmap;

    public static Drawable getdrawable(final String path, final Context context) {
        new Thread() {
            @Override
            public void run() {
                try {
                    url = new URL(path);
                    InputStream inputStream = url.openStream();
                    drawable = Drawable.createFromResourceStream(context.getResources(), null, inputStream, "src", null);
                    Drawable.createFromStream(inputStream, "");
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    Thread.sleep(200);
                } catch (Exception e) {
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

    public static Bitmap getBitmap(final String path, final Context context) {
        new Thread() {
            @Override
            public void run() {
                try {
                    File file = new File(context.getCacheDir(), Base64.encodeToString(path.getBytes(), Base64.DEFAULT));
                    if (file.exists() && file.length() > 0) {
                        bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    } else {
                        url = new URL(path);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setReadTimeout(5000);
                        if (connection.getResponseCode() == 200) {
                            InputStream inputStream = connection.getInputStream();
                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            int len = -1;
                            byte[] buffer = new byte[1024];
                            while ((len = inputStream.read(buffer)) != -1) {
                                fileOutputStream.write(buffer, 0, len);
                            }
                            fileOutputStream.close();
                            inputStream.close();
                            bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                super.run();
            }
        }.start();
        return bitmap;
    }

    public static void getPicture(final Douban douban, final Context context) {
        new Thread() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < douban.getSubjects().size(); i++) {
                        url = new URL(douban.getSubjects().get(i).getImages().get(0));
                        InputStream inputStream = url.openStream();
                        Drawable drawable = Drawable.createFromResourceStream(context.getResources(), null, inputStream, "src", null);
                        Drawable.createFromStream(inputStream, "");
                        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                        douban.getSubjects().get(i).setDrawable(drawable);
                    }
                } catch (Exception e) {
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
    }
}
