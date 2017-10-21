package com.example.minhtam.sellticketoopv2;

/**
 * Created by Minh Tam on 10/21/2017.
 */

public class ItemFilm {
    //Lưu thông tin từng bộ phim
    private String name,image,kind;

    public ItemFilm(String name, String image, String kind) {
        this.name = name;
        this.image = image;
        this.kind = kind;
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
}
