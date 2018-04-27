package com.heweather.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/27.
 */

public class DairyWeatherModel implements Serializable{
    /**
     * 预报时间
     */
    private String date;
    /**
     * 最高温度
     */
    private String tmp_max;
    /**
     * 最低温度
     */
    private String tmp_min;
    /**
     * 白天天气状况描述
     */
    private String cond_txt_d;
    /**
     * 晚间天气状况描述
     */
    private String cond_txt_n;
    /**
     * 风向
     */
    private String wind_dir;
    /**
     *风力
     */
    private String wind_sc;
    /**
     * 相对湿度
     */
    private String hum;
    /**
     * 降水量
     */
    private String pcpn;
    /**
     * 紫外线强度指数
     */
    private String uv_index;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getPcpn() {
        return pcpn;
    }

    public void setPcpn(String pcpn) {
        this.pcpn = pcpn;
    }

    public String getUv_index() {
        return uv_index;
    }

    public void setUv_index(String uv_index) {
        this.uv_index = uv_index;
    }
}
