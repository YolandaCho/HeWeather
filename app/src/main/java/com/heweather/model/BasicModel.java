package com.heweather.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/27.
 */

public class BasicModel implements Serializable {
    /**
     * 城市名称
     */
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

