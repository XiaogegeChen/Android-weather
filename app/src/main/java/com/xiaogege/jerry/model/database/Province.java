package com.xiaogege.jerry.model.database;

import org.litepal.crud.LitePalSupport;

/**
 * 省份的表
 */
public class Province extends LitePalSupport {
    /**
     * 省份id
     */
    private String provinceId;
    /**
     * 省份名
     */
    private String provinceName;

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }
}
