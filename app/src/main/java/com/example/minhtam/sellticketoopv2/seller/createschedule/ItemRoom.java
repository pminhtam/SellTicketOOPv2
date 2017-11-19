package com.example.minhtam.sellticketoopv2.seller.createschedule;

/**
 * Created by Minh Tam on 11/19/2017.
 */

public class ItemRoom {
    private String id,name;

    public ItemRoom(String id,String name){
        this.id = id;
        this.name = name;
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
}
