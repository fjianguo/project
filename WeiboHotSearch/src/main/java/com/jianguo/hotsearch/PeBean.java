package com.jianguo.hotsearch;

public class PeBean {
    int Id;
    String Title;
    String Rank;
    String View;
    public PeBean() {
        super();
        // TODO Auto-generated constructor stub
    }
    public PeBean(int s_id, String s_rank, String s_title, String s_view) {
        super();
        Id = s_id;
        Rank = s_rank;
        Title = s_title;
        View = s_view;
    }
    @Override
    public String toString() {
        return "PeBean [Id=" + Id + ", Rank=" + Rank + ", Title=" + Title + ", View=" + View + "]";
    }
    public int getId() {
        return Id;
    }
    public void setId(int id) {
        Id = id;
    }
    public String getRank() {
        return Rank;
    }
    public void setRank(String rank) {
        Rank = rank;
    }
    public String getTitle() {
        return Title;
    }
    public void setTitle(String title) {
        Title = title;
    }
    public String getView() {
        return View;
    }
    public void setView(String view) {
        View = view;
    }


}

