package com.example.weatherapp.bib.Domins;

public class FutureDomains {

    private String day;
    private String status;
    private  String getPicpath;
    private int maxtemp;
    private int mintemp;


    public FutureDomains(String day, String status, String getPicpath, int maxtemp, int mintemp) {
        this.day = day;
        this.status = status;
        this.getPicpath = getPicpath;
        this.maxtemp = maxtemp;
        this.mintemp = mintemp;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGetPicpath() {
        return getPicpath;
    }

    public void setGetPicpath(String getPicpath) {
        this.getPicpath = getPicpath;
    }

    public int getMaxtemp() {
        return maxtemp;
    }

    public void setMaxtemp(int maxtemp) {
        this.maxtemp = maxtemp;
    }

    public int getMintemp() {
        return mintemp;
    }

    public void setMintemp(int mintemp) {
        this.mintemp = mintemp;
    }
}
