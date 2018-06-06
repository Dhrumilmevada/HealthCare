package com.example.dhrumil.healthcare.appointment.model;

/**
 * Created by Dhrumil on 5/9/2018.
 */

public class AppointmentData {

    private String name;
    private String date;
    private String time;

    public AppointmentData(String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public AppointmentData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
