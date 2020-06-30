package com.doctorsteep.elementinspector.model;

import android.graphics.Bitmap;

public class BackHistoryModel {

    private String title;
    private String url;
    private Bitmap icon;
    private String time;

    public BackHistoryModel(String title, String url, Bitmap icon, String time) {
        this.title = title;
        this.url = url;
        this.icon = icon;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }
    public String getUrl() {
        return url;
    }
    public Bitmap getIcon() {
        return icon;
    }
    public String getTime() {
        return time;
    }
}
