package com.example.weatherapp.bib.Domins;

public class Hourly {
    private String hour;
    private int temp;
    private String Picpath;

    public Hourly(String hour, int temp, String picpath) {
        this.hour = hour;
        this.temp = temp;
        Picpath = picpath;
    }



    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public String getPicpath() {
        return Picpath;
    }

    public void setPicpath(String picpath) {
        Picpath = picpath;
    }


}
