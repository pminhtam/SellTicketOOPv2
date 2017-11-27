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
    private String time_begin,time_end;
    private String time_user_book;
    public ItemUserHistoryBookTicket(int price,String row,String column ,String nameFilm,String nameLocation,String image,String time_begin,String time_end,String time_user_book){
        this.price = price;
        this.row = row;
        this.column = column;
        this.nameLocation = nameLocation;
        this.nameFilm = nameFilm;
        this.image = image;
        this.time_begin = time_begin;
        this.time_end = time_end;
        this.time_user_book = time_user_book;
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

    public String getTime_begin() {
        return time_begin;
    }

    public void setTime_begin(String time_begin) {
        this.time_begin = time_begin;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public String getTime_user_book() {
        return time_user_book;
    }

    public void setTime_user_book(String time_user_book) {
        this.time_user_book = time_user_book;
    }
}
