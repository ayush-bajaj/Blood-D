package com.example.admin1.blooddonation;

public class myLocation {

    public double lat;
    public double lng;

    public myLocation()
    {
        lat = 0;
        lng = 0;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setLocation(double lat, double lng)
    {
        setLat(lat);
        setLng(lng);
    }

}
