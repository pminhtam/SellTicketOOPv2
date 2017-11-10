package com.example.minhtam.sellticketoopv2.chooseseat;

/**
 * Created by Minh Tam on 11/9/2017.
 */

public class ItemSeat {
    //Thông tin của ghế ngồi
    private String row,column;
    private boolean isChoose;
    private int price;


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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
