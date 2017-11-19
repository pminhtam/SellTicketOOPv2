package com.example.minhtam.sellticketoopv2.seller.createschedule;

/**
 * Created by Minh Tam on 11/19/2017.
 */

public class ItemChooseFilmSell {
    private String filmName,filmId,roomId,timeBegin,timeEnd,priceVIP,priceNORMAL,image,locationId;
    public ItemChooseFilmSell(String filmId,String filmName,String image){
        this.filmId = filmId;
        this.image = image;
        this.filmName = filmName;
    }

    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
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

    public String getPriceVIP() {
        return priceVIP;
    }

    public void setPriceVIP(String priceVIP) {
        this.priceVIP = priceVIP;
    }

    public String getPriceNORMAL() {
        return priceNORMAL;
    }

    public void setPriceNORMAL(String priceNORMAL) {
        this.priceNORMAL = priceNORMAL;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }
}
