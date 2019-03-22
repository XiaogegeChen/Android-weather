package com.xiaogege.jerry.model.gson;

import java.util.List;

public class Forecast {

    private List<HeWeather6Bean> HeWeather6;

    public List<HeWeather6Bean> getHeWeather6() {
        return HeWeather6;
    }

    public void setHeWeather6(List<HeWeather6Bean> HeWeather6) {
        this.HeWeather6 = HeWeather6;
    }

    public static class HeWeather6Bean {
        /**
         * basic : {"cid":"CN101010200","location":"海淀","parent_city":"北京","admin_area":"北京","cnty":"中国","lat":"39.95607376","lon":"116.31031799","tz":"+8.00"}
         * update : {"loc":"2019-03-20 12:55","utc":"2019-03-20 04:55"}
         * status : ok
         * daily_forecast : [{"cond_code_d":"305","cond_code_n":"101","cond_txt_d":"小雨","cond_txt_n":"多云","date":"2019-03-20","hum":"21","mr":"17:28","ms":"06:02","pcpn":"0.0","pop":"1","pres":"1010","sr":"06:17","ss":"18:27","tmp_max":"22","tmp_min":"6","uv_index":"2","vis":"24","wind_deg":"341","wind_dir":"西北风","wind_sc":"1-2","wind_spd":"3"},{"cond_code_d":"100","cond_code_n":"100","cond_txt_d":"晴","cond_txt_n":"晴","date":"2019-03-21","hum":"19","mr":"18:42","ms":"06:38","pcpn":"0.0","pop":"0","pres":"1017","sr":"06:15","ss":"18:28","tmp_max":"14","tmp_min":"0","uv_index":"5","vis":"25","wind_deg":"9","wind_dir":"北风","wind_sc":"4-5","wind_spd":"27"},{"cond_code_d":"100","cond_code_n":"100","cond_txt_d":"晴","cond_txt_n":"晴","date":"2019-03-22","hum":"16","mr":"19:55","ms":"07:12","pcpn":"0.0","pop":"0","pres":"1022","sr":"06:13","ss":"18:29","tmp_max":"16","tmp_min":"4","uv_index":"5","vis":"25","wind_deg":"285","wind_dir":"西北风","wind_sc":"3-4","wind_spd":"15"},{"cond_code_d":"100","cond_code_n":"100","cond_txt_d":"晴","cond_txt_n":"晴","date":"2019-03-23","hum":"22","mr":"21:06","ms":"07:45","pcpn":"0.0","pop":"0","pres":"1009","sr":"06:12","ss":"18:30","tmp_max":"14","tmp_min":"3","uv_index":"5","vis":"25","wind_deg":"226","wind_dir":"西南风","wind_sc":"3-4","wind_spd":"22"},{"cond_code_d":"100","cond_code_n":"100","cond_txt_d":"晴","cond_txt_n":"晴","date":"2019-03-24","hum":"15","mr":"22:14","ms":"08:19","pcpn":"0.0","pop":"0","pres":"1004","sr":"06:10","ss":"18:31","tmp_max":"19","tmp_min":"4","uv_index":"5","vis":"25","wind_deg":"180","wind_dir":"南风","wind_sc":"3-4","wind_spd":"17"},{"cond_code_d":"100","cond_code_n":"100","cond_txt_d":"晴","cond_txt_n":"晴","date":"2019-03-25","hum":"16","mr":"23:20","ms":"08:55","pcpn":"0.0","pop":"0","pres":"1005","sr":"06:08","ss":"18:32","tmp_max":"21","tmp_min":"4","uv_index":"5","vis":"25","wind_deg":"307","wind_dir":"西北风","wind_sc":"3-4","wind_spd":"24"},{"cond_code_d":"100","cond_code_n":"100","cond_txt_d":"晴","cond_txt_n":"晴","date":"2019-03-26","hum":"29","mr":"00:00","ms":"09:34","pcpn":"0.0","pop":"0","pres":"1017","sr":"06:07","ss":"18:33","tmp_max":"15","tmp_min":"3","uv_index":"5","vis":"25","wind_deg":"170","wind_dir":"南风","wind_sc":"1-2","wind_spd":"6"}]
         */

        private BasicBean basic;
        private UpdateBean update;
        private String status;
        private List<DailyForecastBean> daily_forecast;

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

        public List<DailyForecastBean> getDaily_forecast() {
            return daily_forecast;
        }

        public void setDaily_forecast(List<DailyForecastBean> daily_forecast) {
            this.daily_forecast = daily_forecast;
        }

        public static class BasicBean {
            /**
             * cid : CN101010200
             * location : 海淀
             * parent_city : 北京
             * admin_area : 北京
             * cnty : 中国
             * lat : 39.95607376
             * lon : 116.31031799
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
             * loc : 2019-03-20 12:55
             * utc : 2019-03-20 04:55
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

        public static class DailyForecastBean {
            /**
             * cond_code_d : 305
             * cond_code_n : 101
             * cond_txt_d : 小雨
             * cond_txt_n : 多云
             * date : 2019-03-20
             * hum : 21
             * mr : 17:28
             * ms : 06:02
             * pcpn : 0.0
             * pop : 1
             * pres : 1010
             * sr : 06:17
             * ss : 18:27
             * tmp_max : 22
             * tmp_min : 6
             * uv_index : 2
             * vis : 24
             * wind_deg : 341
             * wind_dir : 西北风
             * wind_sc : 1-2
             * wind_spd : 3
             */

            private String cond_code_d;
            private String cond_code_n;
            private String cond_txt_d;
            private String cond_txt_n;
            private String date;
            private String hum;
            private String mr;
            private String ms;
            private String pcpn;
            private String pop;
            private String pres;
            private String sr;
            private String ss;
            private String tmp_max;
            private String tmp_min;
            private String uv_index;
            private String vis;
            private String wind_deg;
            private String wind_dir;
            private String wind_sc;
            private String wind_spd;

            public String getCond_code_d() {
                return cond_code_d;
            }

            public void setCond_code_d(String cond_code_d) {
                this.cond_code_d = cond_code_d;
            }

            public String getCond_code_n() {
                return cond_code_n;
            }

            public void setCond_code_n(String cond_code_n) {
                this.cond_code_n = cond_code_n;
            }

            public String getCond_txt_d() {
                return cond_txt_d;
            }

            public void setCond_txt_d(String cond_txt_d) {
                this.cond_txt_d = cond_txt_d;
            }

            public String getCond_txt_n() {
                return cond_txt_n;
            }

            public void setCond_txt_n(String cond_txt_n) {
                this.cond_txt_n = cond_txt_n;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHum() {
                return hum;
            }

            public void setHum(String hum) {
                this.hum = hum;
            }

            public String getMr() {
                return mr;
            }

            public void setMr(String mr) {
                this.mr = mr;
            }

            public String getMs() {
                return ms;
            }

            public void setMs(String ms) {
                this.ms = ms;
            }

            public String getPcpn() {
                return pcpn;
            }

            public void setPcpn(String pcpn) {
                this.pcpn = pcpn;
            }

            public String getPop() {
                return pop;
            }

            public void setPop(String pop) {
                this.pop = pop;
            }

            public String getPres() {
                return pres;
            }

            public void setPres(String pres) {
                this.pres = pres;
            }

            public String getSr() {
                return sr;
            }

            public void setSr(String sr) {
                this.sr = sr;
            }

            public String getSs() {
                return ss;
            }

            public void setSs(String ss) {
                this.ss = ss;
            }

            public String getTmp_max() {
                return tmp_max;
            }

            public void setTmp_max(String tmp_max) {
                this.tmp_max = tmp_max;
            }

            public String getTmp_min() {
                return tmp_min;
            }

            public void setTmp_min(String tmp_min) {
                this.tmp_min = tmp_min;
            }

            public String getUv_index() {
                return uv_index;
            }

            public void setUv_index(String uv_index) {
                this.uv_index = uv_index;
            }

            public String getVis() {
                return vis;
            }

            public void setVis(String vis) {
                this.vis = vis;
            }

            public String getWind_deg() {
                return wind_deg;
            }

            public void setWind_deg(String wind_deg) {
                this.wind_deg = wind_deg;
            }

            public String getWind_dir() {
                return wind_dir;
            }

            public void setWind_dir(String wind_dir) {
                this.wind_dir = wind_dir;
            }

            public String getWind_sc() {
                return wind_sc;
            }

            public void setWind_sc(String wind_sc) {
                this.wind_sc = wind_sc;
            }

            public String getWind_spd() {
                return wind_spd;
            }

            public void setWind_spd(String wind_spd) {
                this.wind_spd = wind_spd;
            }
        }
    }
}
