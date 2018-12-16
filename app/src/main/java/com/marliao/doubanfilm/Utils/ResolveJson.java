package com.marliao.doubanfilm.Utils;

import android.text.TextUtils;
import android.util.Log;

import com.marliao.doubanfilm.vo.Detail;
import com.marliao.doubanfilm.vo.Douban;
import com.marliao.doubanfilm.vo.Subjects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResolveJson {
    public static Douban resolveJson(String jsonStr) throws JSONException {
        Douban douban = new Douban();
        JSONObject jsonObject = new JSONObject(jsonStr);
        int count = jsonObject.getInt("count");
        int total = jsonObject.getInt("total");

        if (count != 100 || total == 0) {
            douban = null;
            return douban;
        }
        douban.setCount(count);
        douban.setTotal(total);

        JSONArray jsonSubjects = jsonObject.getJSONArray("subjects");
        List<Subjects> subjectsList = new ArrayList<>();
        for (int i = 0; i < jsonSubjects.length(); i++) {
            JSONObject jsonSubjectsJSONObject = jsonSubjects.getJSONObject(i);
            Subjects subjects = new Subjects();

            JSONArray jsonGenres = jsonSubjectsJSONObject.getJSONArray("genres");
            List<String> genresList = new ArrayList<>();
            for (int j = 0; j < jsonGenres.length(); j++) {
                genresList.add(jsonGenres.getString(j));
            }
            subjects.setGenres(genresList);

            String title = jsonSubjectsJSONObject.getString("title");
            subjects.setTitle(title);

            JSONArray jsonCasts = jsonSubjectsJSONObject.getJSONArray("casts");
            List<Detail> detailCastsLists = new ArrayList<>();
            for (int j = 0; j < jsonCasts.length(); j++) {
                Detail detail = new Detail();

                /*JSONObject avatars = jsonCasts.getJSONObject(j).getJSONObject("avatars");
                detail.setSmall(avatars.getString("small"));*/
                //todo  主演照片没拿

                detail.setName_en(jsonCasts.getJSONObject(j).getString("name_en"));
                detail.setName(jsonCasts.getJSONObject(j).getString("name"));
                detail.setAlt(jsonCasts.getJSONObject(j).getString("alt"));

                detailCastsLists.add(detail);
            }
            subjects.setCasts(detailCastsLists);

            JSONArray durations = jsonSubjectsJSONObject.getJSONArray("durations");
            for (int j = 0; j < durations.length(); j++) {
                subjects.setDurations(durations.getString(j));
            }

            subjects.setCollect_count(jsonSubjectsJSONObject.getLong("collect_count"));
            subjects.setMainland_pubdate(jsonSubjectsJSONObject.getString("mainland_pubdate"));
            subjects.setOriginal_title(jsonSubjectsJSONObject.getString("original_title"));

            JSONArray jsonDirectors = jsonSubjectsJSONObject.getJSONArray("directors");
            List<Detail> detailDirectorsList = new ArrayList<>();
            for (int j = 0; j < jsonDirectors.length(); j++) {
                Detail detail = new Detail();
                JSONObject jsonObject1 = jsonDirectors.getJSONObject(j);
                /*String avatars = jsonObject1.getString("avatars");
                if (!TextUtils.isEmpty(avatars)) {
                    Log.i("=============" + i, avatars);
                    JSONObject jsonObject2 = new JSONObject(avatars);
                    detail.setSmall(jsonObject2.getString("small"));
                }*/
                //TODO 导演照片没拿

                detail.setName_en(jsonObject1.getString("name_en"));
                detail.setName(jsonObject1.getString("name"));
                detail.setAlt(jsonObject1.getString("alt"));

                detailDirectorsList.add(detail);
            }
            subjects.setDirectors(detailDirectorsList);

            JSONArray jsonPubdates = jsonSubjectsJSONObject.getJSONArray("pubdates");
            List<String> pubdatesList = new ArrayList<>();
            for (int j = 0; j < jsonPubdates.length(); j++) {
                pubdatesList.add(jsonPubdates.getString(j));
            }
            subjects.setPubdates(pubdatesList);

            String year = jsonSubjectsJSONObject.getString("year");
            subjects.setYear(year);

            JSONObject jsonImages = jsonSubjectsJSONObject.getJSONObject("images");
            List<String> imageslist = new ArrayList<>();
            imageslist.add(jsonImages.getString("small"));
            imageslist.add(jsonImages.getString("large"));
            imageslist.add(jsonImages.getString("medium"));
            subjects.setImages(imageslist);

            subjects.setAlt(jsonSubjectsJSONObject.getString("alt"));
            subjectsList.add(subjects);
        }
        douban.setSubjects(subjectsList);
        douban.setTitle(jsonObject.getString("title"));
        return douban;
    }

}
