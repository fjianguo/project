package com.example.weibohotsearchactivity.damain;

public class Weibo {

    private String id;
    private String rank;
    private String title;
    private String view;
    private String time;

    public Weibo(String id,String rank,String title,String view,String time){
        this.id = id;
        this.rank = rank;
        this.title = title;
        this.view = view;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

