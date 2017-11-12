package com.example.minhtam.sellticketoopv2.film;

/**
 * Created by Minh Tam on 11/10/2017.
 */

public class ItemFilmElementInfo {
    private String id,name,image,kind,duration,content;
    public ItemFilmElementInfo(String name,String image,String kind,String duration,String content){
        this.name = name;
        this.image = image;
        this.kind = kind;
        this.duration= duration;
        this.content = content;

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
