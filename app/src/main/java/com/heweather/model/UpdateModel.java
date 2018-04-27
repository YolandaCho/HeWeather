package com.heweather.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/27.
 */

public class UpdateModel implements Serializable{
    /**
     * 当地时间，24小时制
     */
    private String loc;
    /**
     * UTC时间，24小时制
     */
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
