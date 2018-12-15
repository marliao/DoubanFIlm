package com.marliao.doubanfilm.Utils;

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
            Subjects subjects = new Subjects();

            JSONArray jsonGenres = jsonSubjects.getJSONObject(i).getJSONArray("genres");
            List<String> genresList = new ArrayList<>();
            for (int j = 0; j < jsonGenres.length(); j++) {
                genresList.add(jsonGenres.getString(j));
            }
            subjects.setGenres(genresList);

            String title = jsonSubjects.getJSONObject(i).getString("title");
            subjects.setTitle(title);

            JSONArray jsonCasts = jsonSubjects.getJSONObject(i).getJSONArray("casts");
            List<Detail> detailCastsLists = new ArrayList<>();
            for (int j = 0; j < jsonCasts.length(); j++) {
                Detail detail = new Detail();

                JSONObject avatars = jsonCasts.getJSONObject(j).getJSONObject("avatars");
                List<String> avatarsList = new ArrayList<>();
                avatarsList.add(avatars.getString("small"));
                avatarsList.add(avatars.getString("large"));
                avatarsList.add(avatars.getString("medium"));
                detail.setAvatars(avatarsList);

                detail.setName_en(jsonCasts.getJSONObject(j).getString("name_en"));
                detail.setName(jsonCasts.getJSONObject(j).getString("name"));
                detail.setAlt(jsonCasts.getJSONObject(j).getString("alt"));

                detailCastsLists.add(detail);
            }
            subjects.setCasts(detailCastsLists);

            JSONArray durations = jsonSubjects.getJSONObject(i).getJSONArray("durations");
            for (int j = 0; j < durations.length(); j++) {
                subjects.setDurations(durations.getString(j));
            }

            subjects.setCollect_count(jsonSubjects.getJSONObject(i).getLong("collect_count"));
            subjects.setMainland_pubdate(jsonSubjects.getJSONObject(i).getString("mainland_pubdate"));
            subjects.setOriginal_title(jsonSubjects.getJSONObject(i).getString("original_title"));

            JSONArray jsonDirectors = jsonSubjects.getJSONObject(i).getJSONArray("directors");
            List<Detail> detailDirectorsList = new ArrayList<>();
            for (int j = 0; j < jsonDirectors.length(); j++) {
                Detail detail = new Detail();

                JSONObject avatars = jsonDirectors.getJSONObject(j).getJSONObject("avatars");
                List<String> avatarsList = new ArrayList<>();
                avatarsList.add(avatars.getString("small"));
                avatarsList.add(avatars.getString("large"));
                avatarsList.add(avatars.getString("medium"));
                detail.setAvatars(avatarsList);

                detail.setName_en(jsonDirectors.getJSONObject(j).getString("name_en"));
                detail.setName(jsonDirectors.getJSONObject(j).getString("name"));
                detail.setAlt(jsonDirectors.getJSONObject(j).getString("alt"));

                detailDirectorsList.add(detail);
            }
            subjects.setDirectors(detailDirectorsList);

            JSONArray jsonPubdates = jsonSubjects.getJSONObject(i).getJSONArray("pubdates");
            List<String> pubdatesList = new ArrayList<>();
            for (int j = 0; j < jsonPubdates.length(); j++) {
                pubdatesList.add(jsonPubdates.getString(j));
            }
            subjects.setPubdates(pubdatesList);

            String year = jsonSubjects.getJSONObject(i).getString("year");
            subjects.setYear(year);

            JSONObject jsonImages = jsonSubjects.getJSONObject(i).getJSONObject("images");
            List<String> imageslist = new ArrayList<>();
            imageslist.add(jsonImages.getString("small"));
            imageslist.add(jsonImages.getString("large"));
            imageslist.add(jsonImages.getString("medium"));
            subjects.setImages(imageslist);

            subjects.setAlt(jsonSubjects.getJSONObject(i).getString("alt"));
            subjectsList.add(subjects);
        }
        douban.setSubjects(subjectsList);
        douban.setTitle(jsonObject.getString("title"));
        return douban;
    }
}
