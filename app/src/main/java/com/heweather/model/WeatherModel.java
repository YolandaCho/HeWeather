package com.heweather.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/3/27.
 * "HeWeather6"  corresponde  model
 */

public class WeatherModel implements Serializable {
    /**
     * {"HeWeather6":
         [{
         "basic":{"cid":"CN101010100","location":"北京","parent_city":"北京","admin_area":"北京","cnty":"中国","lat":"39.90498734","lon":"116.4052887","tz":"+8.00"},
         "update":{"loc":"2018-03-27 10:47","utc":"2018-03-27 02:47"},
         "status":"ok",
         "daily_forecast":
             [
             {"cond_code_d":"100","cond_code_n":"101","cond_txt_d":"晴","cond_txt_n":"多云",
             "date":"2018-03-27","hum":"27","mr":"13:49","ms":"03:29","pcpn":"0.0","pop":"0",
             "pres":"1009","sr":"06:07","ss":"18:33","tmp_max":"27","tmp_min":"11","uv_index":"5",
             "vis":"20","wind_deg":"180","wind_dir":"南风","wind_sc":"1-2","wind_spd":"4"},
             {"cond_code_d":"101","cond_code_n":"100","cond_txt_d":"多云","cond_txt_n":"晴",
             "date":"2018-03-28","hum":"22","mr":"14:57","ms":"04:14","pcpn":"0.0","pop":"0",
             "pres":"1013","sr":"06:05","ss":"18:34","tmp_max":"26","tmp_min":"10","uv_index":"5",
             "vis":"20","wind_deg":"89","wind_dir":"东风","wind_sc":"1-2","wind_spd":"8"},
             {"cond_code_d":"100","cond_code_n":"100","cond_txt_d":"晴","cond_txt_n":"晴",
             "date":"2018-03-29","hum":"24","mr":"16:06","ms":"04:55","pcpn":"0.0","pop":"0",
             "pres":"1028","sr":"06:04","ss":"18:35","tmp_max":"19","tmp_min":"7","uv_index":"5",
             "vis":"20","wind_deg":"191","wind_dir":"南风","wind_sc":"1-2","wind_spd":"9"}
         ]}]}
     */
    private BasicModel basic;
    private UpdateModel mUpdateModel;
    /** request status   "ok"-successful */
    private String status;
    private List<DairyWeatherModel> daily_forecast;

    public BasicModel getBasic() {
        return basic;
    }

    public void setBasicModel(BasicModel basic) {
        basic = basic;
    }

    public UpdateModel getUpdateModel() {
        return mUpdateModel;
    }

    public void setUpdateModel(UpdateModel updateModel) {
        mUpdateModel = updateModel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DairyWeatherModel> getDaily_forecast() {
        return daily_forecast;
    }

    public void setDaily_forecast(List<DairyWeatherModel> daily_forecast) {
        this.daily_forecast = daily_forecast;
    }
}
