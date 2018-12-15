package com.marliao.doubanfilm.vo;

import java.util.Date;
import java.util.List;

public class Subjects {
    private List<String> genres;
    private String title;
    private List<Detail> casts;
    private String durations;
    private long collect_count;
    private String mainland_pubdate;
    private String original_title;
    private List<Detail> directors;
    private List<String> pubdates;
    private String year;
    private List<String> images;
    private String alt;

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Detail> getCasts() {
        return casts;
    }

    public void setCasts(List<Detail> casts) {
        this.casts = casts;
    }

    public String getDurations() {
        return durations;
    }

    public void setDurations(String durations) {
        this.durations = durations;
    }

    public long getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(long collect_count) {
        this.collect_count = collect_count;
    }

    public String getMainland_pubdate() {
        return mainland_pubdate;
    }

    public void setMainland_pubdate(String mainland_pubdate) {
        this.mainland_pubdate = mainland_pubdate;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public List<Detail> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Detail> directors) {
        this.directors = directors;
    }

    public List<String> getPubdates() {
        return pubdates;
    }

    public void setPubdates(List<String> pubdates) {
        this.pubdates = pubdates;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }
}
