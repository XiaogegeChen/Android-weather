package com.xiaogege.jerry.model.gson;

import java.util.List;

public class AQI {
    private List<HeWeather6Bean> HeWeather6;

    public List<HeWeather6Bean> getHeWeather6() {
        return HeWeather6;
    }

    public void setHeWeather6(List<HeWeather6Bean> HeWeather6) {
        this.HeWeather6 = HeWeather6;
    }

    public static class HeWeather6Bean {
        /**
         * basic : {"cid":"CN101110101","location":"西安","parent_city":"西安","admin_area":"陕西","cnty":"中国","lat":"34.26316071","lon":"108.94802094","tz":"+8.00"}
         * update : {"loc":"2019-03-20 14:55","utc":"2019-03-20 06:55"}
         * status : ok
         * air_now_city : {"aqi":"75","qlty":"良","main":"PM10","pm25":"29","pm10":"99","no2":"18","so2":"6","co":"0.5","o3":"132","pub_time":"2019-03-20 15:00"}
         * air_now_station : [{"air_sta":"高压开关厂","aqi":"113","asid":"CNA1462","co":"0.3","lat":"34.2749","lon":"108.882","main":"PM10","no2":"19","o3":"139","pm10":"175","pm25":"30","pub_time":"2019-03-20 15:00","qlty":"轻度污染","so2":"7"},{"air_sta":"兴庆小区","aqi":"67","asid":"CNA1463","co":"0.5","lat":"34.2629","lon":"108.993","main":"PM10","no2":"23","o3":"144","pm10":"83","pm25":"23","pub_time":"2019-03-20 15:00","qlty":"良","so2":"7"},{"air_sta":"纺织城","aqi":"53","asid":"CNA1464","co":"0.7","lat":"34.2572","lon":"109.06","main":"PM10","no2":"25","o3":"130","pm10":"55","pm25":"25","pub_time":"2019-03-20 15:00","qlty":"良","so2":"6"},{"air_sta":"小寨","aqi":"98","asid":"CNA1465","co":"0.5","lat":"34.2324","lon":"108.94","main":"PM10","no2":"22","o3":"132","pm10":"146","pm25":"26","pub_time":"2019-03-20 15:00","qlty":"良","so2":"7"},{"air_sta":"市人民体育场","aqi":"69","asid":"CNA1466","co":"0.5","lat":"34.2713","lon":"108.954","main":"PM10","no2":"16","o3":"144","pm10":"88","pm25":"31","pub_time":"2019-03-20 15:00","qlty":"良","so2":"7"},{"air_sta":"高新西区","aqi":"104","asid":"CNA1467","co":"0.5","lat":"34.2303","lon":"108.883","main":"PM10","no2":"2","o3":"117","pm10":"157","pm25":"32","pub_time":"2019-03-20 15:00","qlty":"轻度污染","so2":"3"},{"air_sta":"经开区","aqi":"53","asid":"CNA1468","co":"0.6","lat":"34.3474","lon":"108.935","main":"PM10","no2":"22","o3":"85","pm10":"55","pm25":"24","pub_time":"2019-03-20 15:00","qlty":"良","so2":"4"},{"air_sta":"长安区","aqi":"79","asid":"CNA1469","co":"0.5","lat":"34.1546","lon":"108.906","main":"PM10","no2":"14","o3":"135","pm10":"107","pm25":"24","pub_time":"2019-03-20 15:00","qlty":"良","so2":"7"},{"air_sta":"阎良区","aqi":"58","asid":"CNA1470","co":"0.5","lat":"34.6575","lon":"109.2","main":"PM10","no2":"14","o3":"145","pm10":"65","pm25":"36","pub_time":"2019-03-20 15:00","qlty":"良","so2":"6"},{"air_sta":"临潼区","aqi":"55","asid":"CNA1471","co":"0.7","lat":"34.3731","lon":"109.2186","main":"PM10","no2":"18","o3":"149","pm10":"60","pm25":"27","pub_time":"2019-03-20 15:00","qlty":"良","so2":"5"},{"air_sta":"草滩","aqi":"77","asid":"CNA1472","co":"0.3","lat":"34.378","lon":"108.869","main":"PM10","no2":"18","o3":"135","pm10":"104","pm25":"36","pub_time":"2019-03-20 15:00","qlty":"良","so2":"3"},{"air_sta":"曲江文化产业集团","aqi":"86","asid":"CNA1473","co":"0.4","lat":"34.1978","lon":"108.985","main":"PM10","no2":"24","o3":"101","pm10":"121","pm25":"28","pub_time":"2019-03-20 15:00","qlty":"良","so2":"6"},{"air_sta":"广运潭","aqi":"58","asid":"CNA1474","co":"0.3","lat":"34.3274","lon":"109.043","main":"PM10","no2":"17","o3":"148","pm10":"65","pm25":"24","pub_time":"2019-03-20 15:00","qlty":"良","so2":"4"}]
         */

        private BasicBean basic;
        private UpdateBean update;
        private String status;
        private AirNowCityBean air_now_city;
        private List<AirNowStationBean> air_now_station;

        public BasicBean getBasic() {
            return basic;
        }

        public void setBasic(BasicBean basic) {
            this.basic = basic;
        }

        public UpdateBean getUpdate() {
            return update;
        }

        public void setUpdate(UpdateBean update) {
            this.update = update;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public AirNowCityBean getAir_now_city() {
            return air_now_city;
        }

        public void setAir_now_city(AirNowCityBean air_now_city) {
            this.air_now_city = air_now_city;
        }

        public List<AirNowStationBean> getAir_now_station() {
            return air_now_station;
        }

        public void setAir_now_station(List<AirNowStationBean> air_now_station) {
            this.air_now_station = air_now_station;
        }

        public static class BasicBean {
            /**
             * cid : CN101110101
             * location : 西安
             * parent_city : 西安
             * admin_area : 陕西
             * cnty : 中国
             * lat : 34.26316071
             * lon : 108.94802094
             * tz : +8.00
             */

            private String cid;
            private String location;
            private String parent_city;
            private String admin_area;
            private String cnty;
            private String lat;
            private String lon;
            private String tz;

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getParent_city() {
                return parent_city;
            }

            public void setParent_city(String parent_city) {
                this.parent_city = parent_city;
            }

            public String getAdmin_area() {
                return admin_area;
            }

            public void setAdmin_area(String admin_area) {
                this.admin_area = admin_area;
            }

            public String getCnty() {
                return cnty;
            }

            public void setCnty(String cnty) {
                this.cnty = cnty;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLon() {
                return lon;
            }

            public void setLon(String lon) {
                this.lon = lon;
            }

            public String getTz() {
                return tz;
            }

            public void setTz(String tz) {
                this.tz = tz;
            }
        }

        public static class UpdateBean {
            /**
             * loc : 2019-03-20 14:55
             * utc : 2019-03-20 06:55
             */

            private String loc;
            private String utc;

            public String getLoc() {
                return loc;
            }

            public void setLoc(String loc) {
                this.loc = loc;
            }

            public String getUtc() {
                return utc;
            }

            public void setUtc(String utc) {
                this.utc = utc;
            }
        }

        public static class AirNowCityBean {
            /**
             * aqi : 75
             * qlty : 良
             * main : PM10
             * pm25 : 29
             * pm10 : 99
             * no2 : 18
             * so2 : 6
             * co : 0.5
             * o3 : 132
             * pub_time : 2019-03-20 15:00
             */

            private String aqi;
            private String qlty;
            private String main;
            private String pm25;
            private String pm10;
            private String no2;
            private String so2;
            private String co;
            private String o3;
            private String pub_time;

            public String getAqi() {
                return aqi;
            }

            public void setAqi(String aqi) {
                this.aqi = aqi;
            }

            public String getQlty() {
                return qlty;
            }

            public void setQlty(String qlty) {
                this.qlty = qlty;
            }

            public String getMain() {
                return main;
            }

            public void setMain(String main) {
                this.main = main;
            }

            public String getPm25() {
                return pm25;
            }

            public void setPm25(String pm25) {
                this.pm25 = pm25;
            }

            public String getPm10() {
                return pm10;
            }

            public void setPm10(String pm10) {
                this.pm10 = pm10;
            }

            public String getNo2() {
                return no2;
            }

            public void setNo2(String no2) {
                this.no2 = no2;
            }

            public String getSo2() {
                return so2;
            }

            public void setSo2(String so2) {
                this.so2 = so2;
            }

            public String getCo() {
                return co;
            }

            public void setCo(String co) {
                this.co = co;
            }

            public String getO3() {
                return o3;
            }

            public void setO3(String o3) {
                this.o3 = o3;
            }

            public String getPub_time() {
                return pub_time;
            }

            public void setPub_time(String pub_time) {
                this.pub_time = pub_time;
            }
        }

        public static class AirNowStationBean {
            /**
             * air_sta : 高压开关厂
             * aqi : 113
             * asid : CNA1462
             * co : 0.3
             * lat : 34.2749
             * lon : 108.882
             * main : PM10
             * no2 : 19
             * o3 : 139
             * pm10 : 175
             * pm25 : 30
             * pub_time : 2019-03-20 15:00
             * qlty : 轻度污染
             * so2 : 7
             */

            private String air_sta;
            private String aqi;
            private String asid;
            private String co;
            private String lat;
            private String lon;
            private String main;
            private String no2;
            private String o3;
            private String pm10;
            private String pm25;
            private String pub_time;
            private String qlty;
            private String so2;

            public String getAir_sta() {
                return air_sta;
            }

            public void setAir_sta(String air_sta) {
                this.air_sta = air_sta;
            }

            public String getAqi() {
                return aqi;
            }

            public void setAqi(String aqi) {
                this.aqi = aqi;
            }

            public String getAsid() {
                return asid;
            }

            public void setAsid(String asid) {
                this.asid = asid;
            }

            public String getCo() {
                return co;
            }

            public void setCo(String co) {
                this.co = co;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLon() {
                return lon;
            }

            public void setLon(String lon) {
                this.lon = lon;
            }

            public String getMain() {
                return main;
            }

            public void setMain(String main) {
                this.main = main;
            }

            public String getNo2() {
                return no2;
            }

            public void setNo2(String no2) {
                this.no2 = no2;
            }

            public String getO3() {
                return o3;
            }

            public void setO3(String o3) {
                this.o3 = o3;
            }

            public String getPm10() {
                return pm10;
            }

            public void setPm10(String pm10) {
                this.pm10 = pm10;
            }

            public String getPm25() {
                return pm25;
            }

            public void setPm25(String pm25) {
                this.pm25 = pm25;
            }

            public String getPub_time() {
                return pub_time;
            }

            public void setPub_time(String pub_time) {
                this.pub_time = pub_time;
            }

            public String getQlty() {
                return qlty;
            }

            public void setQlty(String qlty) {
                this.qlty = qlty;
            }

            public String getSo2() {
                return so2;
            }

            public void setSo2(String so2) {
                this.so2 = so2;
            }
        }
    }
}
