package com.example.minhtam.sellticketoopv2.userhistorybookticket;

/**
 * Created by Minh Tam on 11/18/2017.
 */

public class ItemUserHistoryBookTicket {
    private int price;
    private String row,column;
    private String nameFilm,nameLocation;
    private String scheduleId;
    private String image;
    public ItemUserHistoryBookTicket(int price,String row,String column,String scheduleId,String nameLocation){
        this.price = price;
        this.row = row;
        this.column = column;
        this.scheduleId = scheduleId;
        this.nameLocation = nameLocation;
        this.nameFilm = "";
        this.image = "";
    }

    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
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

    public String getNameFilm() {
        return nameFilm;
    }

    public void setNameFilm(String nameFilm) {
        this.nameFilm = nameFilm;
    }

    public String getNameLocation() {
        return nameLocation;
    }

    public void setNameLocation(String nameLocation) {
        this.nameLocation = nameLocation;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
