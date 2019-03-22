package com.xiaogege.jerry.model.database;

import org.litepal.crud.LitePalSupport;

/**
 * 城市的表
 */
public class City extends LitePalSupport {
    /**
     * 城市id
     */
    private String cityId;
    /**
     * 城市名
     */
    private String cityName;

    /**
     * 归属的省份的id
     */
    private String provinceId;

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
