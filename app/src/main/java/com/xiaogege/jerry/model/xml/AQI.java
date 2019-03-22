package com.xiaogege.jerry.model.xml;

public class AQI {
    private String aqi;
    private String pm25;

    public AQI() {
    }

    public AQI(String aqi, String pm25) {
        this.aqi = aqi;
        this.pm25 = pm25;
    }

    public String getAqi() {
        return aqi;
    }

    public String getPm25() {
        return pm25;
    }

}
