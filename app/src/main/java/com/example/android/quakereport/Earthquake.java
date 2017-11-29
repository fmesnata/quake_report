package com.example.android.quakereport;

public class Earthquake {

    private double magnitude;

    private String location;

    private long date;

    private String detailsUrl;

    public Earthquake(double magnitude, String location, long date, String detailsUrl) {
        this.magnitude = magnitude;
        this.location = location;
        this.date = date;
        this.detailsUrl = detailsUrl;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getDetailsUrl() {
        return detailsUrl;
    }

    public void setDetailsUrl(String detailsUrl) {
        this.detailsUrl = detailsUrl;
    }
}
