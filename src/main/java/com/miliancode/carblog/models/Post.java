package com.bobocode.springbootdemo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String anons;
    private String fullText;
    private String nickName;
    private int likes;

    public Post() {
    }

    public Post(String title, String anons, String fullText, String nickName) {
        this.title = title;
        this.anons = anons;
        this.fullText = fullText;
        this.nickName = nickName;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnons() {
        return anons;
    }

    public void setAnons(String anons) {
        this.anons = anons;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public int getLikes() {
        return likes;
    }

    public String getNickName() {
        return nickName;
    }

    public void setLikes(int views) {
        this.likes = views;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
