package com.example.minhtam.sellticketoopv2;

/**
 * Created by thanhdat on 14/11/2017.
 */

public class ApiUrl {
    public final static String URL = "http://tickett.cloudapp.net/";

    public static String signIn() {
        return URL + "api/v1/customers/sign_in";
    }

    public static String signUp() {
        return URL + "api/v1/sign_up";
    }

    public static String getFilms(String page) {
        return URL + "api/v1/customers/films?page=" + page;
    }

    public static String getFilm(String filmId) {
        return URL + "api/v1/customers/films/" + filmId;
    }

    public static String getPlaces(String page) {
        return URL + "api/v1/customers/locations?page=" + page;
    }

    public static String getFilmSchedules(String filmId, String page) {
        return URL + "api/v1/customers/schedules?film_id=" + filmId + "&page=" + page;
    }

    public static String getPlaceSchedules(String placeId, String page) {
        return URL + "api/v1/customers/schedules?location_id=" + placeId + "&page=" + page;
    }

    public static String getSchedule(String scheduleId) {
        return URL + "api/v1/customers/schedules/" + scheduleId;
    }

    public static String bookTicket() {
        return URL + "api/v1/customers/tickets/book/";
    }
    public static String getUpdateUserInfo() {
        return URL + "api/v1/customers/users/info";
    }
    public static String getUpdateUserPassword() {
        return URL + "api/v1/customers/users/update_password";
    }
    public static String postCreateFilm() {
        return URL + "api/v1/customers/films";
    }
}
