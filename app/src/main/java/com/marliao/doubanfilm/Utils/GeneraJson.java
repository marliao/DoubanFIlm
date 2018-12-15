package com.marliao.doubanfilm.Utils;

import android.net.Uri;

public class GeneraJson {
    public static String generahttp(String city) {
        String path = "https://api.douban.com/v2/movie/in_theaters?apikey=0b2bdeda43b5688921839c8ecb20399b" +
                "&city="+Uri.encode(city) +"&start=0&count=100&client=somemessage&udid=dddddddddddddddddddddd";
        return path;
    }
}
