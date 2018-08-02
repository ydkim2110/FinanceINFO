package com.example.anti2110.financeinfo.Model;

public class Post {
    private String title;
    private String content;
    private String id;
    private String date;

    public Post() {
    }

    public Post(String title, String content, String id, String date) {
        this.title = title;
        this.content = content;
        this.id = id;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
