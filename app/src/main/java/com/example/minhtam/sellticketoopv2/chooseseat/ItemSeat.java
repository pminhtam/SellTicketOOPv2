package com.example.minhtam.sellticketoopv2.chooseseat;


/**
 * Created by Minh Tam on 11/9/2017.
 */

public class ItemSeat {
    //Thông tin của ghế ngồi
    private String row,column;
    private boolean isChoose;
    private String level;
    private int price;
    private String id;

    public ItemSeat(String row, String column) {
        this.row = row;
        this.column = column;
    }

    public ItemSeat(String row, String column, boolean isChoose, String level, int price, String id) {
        this.row = row;
        this.column = column;
        this.isChoose = isChoose;
        this.level = level;
        this.price = price;
        this.id = id;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
