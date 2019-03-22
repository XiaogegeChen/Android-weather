package com.xiaogege.jerry.model.database;

import org.litepal.crud.LitePalSupport;

/**
 * 县的表
 */
public class County extends LitePalSupport {
    /**
     * 县名
     */
    private String countyName;

    /**
     * 县id
     */
    private String countyId;

    /**
     * 所在市的id
     */
    private String cityId;

    /**
     * 查询天气数据使用的天气的id
     */
    private String weatherId;

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getCountyId() {
        return countyId;
    }

    public void setCountyId(String countyId) {
        this.countyId = countyId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }
}
