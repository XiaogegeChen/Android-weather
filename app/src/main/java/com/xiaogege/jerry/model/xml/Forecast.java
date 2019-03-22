package com.xiaogege.jerry.model.xml;

import java.util.List;

public class Forecast {

    private List<ForecastInfo> ForecastInfoList;

    public Forecast(){
    }

    public Forecast(List<ForecastInfo> forecastInfoList) {
        ForecastInfoList = forecastInfoList;
    }

    public List<ForecastInfo> getForecastInfoList() {
        return ForecastInfoList;
    }

    public static class ForecastInfo{
        private String time;
        private String condition;
        private String maxTem;
        private String minTem;

        public ForecastInfo() {
        }

        public ForecastInfo(String time, String condition, String maxTem, String minTem) {
            this.time = time;
            this.condition = condition;
            this.maxTem = maxTem;
            this.minTem = minTem;
        }

        public String getTime() {
            return time;
        }

        public String getCondition() {
            return condition;
        }

        public String getMaxTem() {
            return maxTem;
        }

        public String getMinTem() {
            return minTem;
        }
    }
}
