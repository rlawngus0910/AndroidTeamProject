package org.techtown.androidteamproject;

public class ReviewInfo {
    private String title;
    private String content;
    private String reviewer;

    public ReviewInfo(){

    }

    public ReviewInfo(String title, String content,String reviewer){
        this.title=title;
        this.content=content;
        this.reviewer=reviewer;


    }

    public String getTitle(){ return this.title;}
    public void setTitle(String title){this.title=title;}
    public String getContent(){return this.content;}
    public void setContent(String content){this.content=content;}
    public String getReviewer(){return this.reviewer;}
    public void setReviewer(){this.reviewer=reviewer;}
}
