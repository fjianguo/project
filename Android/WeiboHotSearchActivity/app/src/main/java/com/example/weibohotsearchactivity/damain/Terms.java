package com.example.weibohotsearchactivity.damain;

public class Terms {
    private String id;
    private String term;
    private String number;

    public Terms(String id,String term,String number){
        this.id = id;
        this.term = term;
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
