package com.doctorsteep.elementinspector.model;

public class AboutAppModel {

    private String name;
    private String work;
    private String url;

    public AboutAppModel(String name, String work, String url) {
        this.name = name;
        this.work = work;
        this.url = url;
    }

    public String getName() {
        return name;
    }
    public String getWork() {
        return work;
    }
    public String getUrl() {
        return url;
    }
}
