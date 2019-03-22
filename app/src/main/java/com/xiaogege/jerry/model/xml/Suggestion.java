package com.xiaogege.jerry.model.xml;

public class Suggestion {
    private String comfort;
    private String carWash;
    private String sport;
    private String travel;

    public Suggestion() {
    }

    public Suggestion(String comfort, String carWash, String sport, String travel) {
        this.comfort = comfort;
        this.carWash = carWash;
        this.sport = sport;
        this.travel = travel;
    }

    public String getComfort() {
        return comfort;
    }

    public String getCarWash() {
        return carWash;
    }

    public String getSport() {
        return sport;
    }

    public String getTravel() {
        return travel;
    }
}
