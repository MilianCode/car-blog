package com.bobocode.springbootdemo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String anons;
    private String fullText;
    private String imageSrc;
    private int views;

    public News() {
    }

    public News(String title, String anons, String fullText, String imageSrc) {
        this.title = title;
        this.anons = anons;
        this.fullText = fullText;
        this.imageSrc = imageSrc;
        this.views = 0;
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

    public String getImageSrc() {
        return imageSrc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }
}
