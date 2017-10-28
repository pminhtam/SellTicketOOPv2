package com.example.minhtam.sellticketoopv2;

/**
 * Created by ThanhDat on 10/27/2017.
 */

public class ItemPlace {
    private String name;
    private int id;

    public ItemPlace(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName(){
        return name;
    }
}
