package com.xiaogege.jerry.model.xml;

public class Now {
    private String time;
    private String location;
    private String temperature;
    private String condition;

    public Now(){
    }

    public Now(String time, String location, String temperature, String condition) {
        this.time = time;
        this.location = location;
        this.temperature = temperature;
        this.condition = condition;
    }

    public String getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getCondition() {
        return condition;
    }
}
