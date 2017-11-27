package com.example.minhtam.sellticketoopv2.film;

/**
 * Created by Minh Tam on 11/9/2017.
 */

public class ItemFilmSchedules {
    //Thông tin về lịch chiếu của từng bộ phim
    private String idSchedules;
    private String timeBegin,timeEnd;
    private String nameFilm;
    private String nameLocation;
    public ItemFilmSchedules(String id,String nameLocation){
        idSchedules = id;
        this.nameLocation = nameLocation;
    }
    public String getIdSchedules() {
        return idSchedules;
    }

    public void setIdSchedules(String idSchedules) {
        this.idSchedules = idSchedules;
    }

    public String getTimeBegin() {
        return timeBegin;
    }

    public void setTimeBegin(String timeBegin) {
        this.timeBegin = timeBegin;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
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
}
