package com.heweather;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.heweather.model.DairyWeatherModel;
import com.heweather.model.WeatherModel;
import com.heweather.util.SpinnerTextColorChangeTool;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener, OnClickListener{
    private OkHttpClient okHttpClient ;
    private TextView cityName;
    private DairyWeatherModel mDairyWeatherModel;
    private String name = null;
    private SimpleAdapter adapter;
    private ArrayAdapter<String> styleAdapter;
    private Spinner style;
    private int index = 1;
    public static LinearLayout mainview;
    /** today */
    private TextView todayTmp; // 气温
    private TextView date; // 日期
    private ImageView weatherImg; // 天气情况对应图片
    private TextView weatherText; // 天气情况
    private TextView windDireact; // 风向
    private TextView uvIndex; // 紫外线强度
    /** tomorrow */
    private ImageView tomorrowWeatherImg;
    private TextView tomorrowWeatherTmp;
    private TextView tomorrowWeather;
    private TextView tomorrowDate;
    /** the day after tomorrow */
    private ImageView afterTomorrowWeatherImg;
    private TextView afterTomorrowWeatherTmp;
    private TextView afterTomorrowWeather;
    private TextView afterTomorrowDate;
    private WeatherModel weatherModel;
    List<DairyWeatherModel> dairyWeatherModelsList;

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            getData();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        loadData();
    }

    private void initView() {
        weatherModel = new WeatherModel();
        okHttpClient = new OkHttpClient();
        mDairyWeatherModel = new DairyWeatherModel();
        dairyWeatherModelsList = new ArrayList<DairyWeatherModel>();
        mainview = (LinearLayout) findViewById(R.id.mainview);

        cityName = (TextView) findViewById(R.id.cityName);
        /** today */
        weatherText = (TextView) findViewById(R.id.weatherText);
        todayTmp = (TextView) findViewById(R.id.today_tmp);
        date = (TextView) findViewById(R.id.toDayDateStr);
        weatherImg = (ImageView) findViewById(R.id.weatherImg);
        windDireact = (TextView) findViewById(R.id.wind_direct);
        uvIndex = (TextView) findViewById(R.id.uv_index);
        /** tomorrow */
        tomorrowWeatherImg = (ImageView) findViewById(R.id.tomorrow_img);
        tomorrowWeatherTmp = (TextView) findViewById(R.id.tomorrow_tmp);
        tomorrowWeather = (TextView) findViewById(R.id.tomorrow_weather);
        tomorrowDate = (TextView) findViewById(R.id.tomorrow_date);
        /** the day after tomorrow */
        afterTomorrowWeatherImg = (ImageView) findViewById(R.id.after_tomorrow_img);
        afterTomorrowWeatherTmp = (TextView) findViewById(R.id.after_tomorrow_tmp);
        afterTomorrowWeather = (TextView) findViewById(R.id.after_tomorrow_weather);
        afterTomorrowDate = (TextView) findViewById(R.id.after_tomorrow_date);

        SharedPreferences sharedPreferences = getSharedPreferences("name",
                Activity.MODE_PRIVATE);
        /** 所选择的城市名 */
        name = sharedPreferences.getString("cn", "");
        if (name == null || name.equals("")){
            name = "广州";
        }

        index = WelcomeActivity.mode_index;
        ChangeBackgroundImage(index);
    }

    /**
     * 请求接口
     */
    private void loadData() {
        final String urlStr = "https://free-api.heweather.com/s6/weather/forecast?location=" + name +"&key=0e5525e4401146ef8146d0db4b0faf3a";
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Request request = new Request.Builder()
                        .url(urlStr)
                        .get()
                        .build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        return;
                    }
                    @Override
                    public void onResponse(Response response) throws IOException {
                        String str = response.body().string();
                        try {
                            JSONObject jsonObject = new JSONObject(str);
                            JSONArray jsonArray=jsonObject.getJSONArray("HeWeather6");
                            String weatherContent=jsonArray.getJSONObject(0).toString();
                            Gson gson=new Gson();
                            weatherModel = gson.fromJson(weatherContent,WeatherModel.class);
                            if (weatherModel != null && weatherModel.getBasic() != null && weatherModel.getDaily_forecast() != null) {
                                handler.sendEmptyMessage(0);
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this, "未能刷新数据",
                                        Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();
    }

    /**
     * 展示数据
     */
    public void getData() {
        if (weatherModel != null)
        {
            cityName.setText(weatherModel.getBasic().getLocation());
            dairyWeatherModelsList = weatherModel.getDaily_forecast();
            for (int i = 0; i < dairyWeatherModelsList.size(); i++) {
                switch (i) {
                    case 0:
                        mDairyWeatherModel = dairyWeatherModelsList.get(0);
                        date.setText(mDairyWeatherModel.getDate());
                        todayTmp.setText(mDairyWeatherModel.getTmp_min() + "℃ / " + mDairyWeatherModel.getTmp_max()+ "℃");
                        weatherText.setText(mDairyWeatherModel.getCond_txt_d());
                        windDireact.setText(mDairyWeatherModel.getWind_dir());
                        uvIndex.setText("紫外线强度：" + mDairyWeatherModel.getUv_index());
                        if ("晴".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            weatherImg.setImageResource(R.mipmap.qing);
                        }
                        else if ("多云".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            weatherImg.setImageResource(R.mipmap.cloudy_more);
                        }
                        else if ("雷阵雨".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            weatherImg.setImageResource(R.mipmap.chinaz22);
                        }
                        else if ("雨夹雪".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            weatherImg.setImageResource(R.mipmap.chinaz18);
                        }
                        else if ("阵雨".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            weatherImg.setImageResource(R.mipmap.rain_zhen);
                        }
                        else if ("阴".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            weatherImg.setImageResource(R.mipmap.wind);
                        }
                        else if ("小雨".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            weatherImg.setImageResource(R.mipmap.rain_little);
                        }
                        else if ("中雨".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            weatherImg.setImageResource(R.mipmap.rain_middle1);
                        }
                        else if ("大雨".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            weatherImg.setImageResource(R.mipmap.chinaz17);
                        }
                        else if ("暴雨".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            weatherImg.setImageResource(R.mipmap.rainstorm);
                        }
                        else if ("阵雪".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            weatherImg.setImageResource(R.mipmap.snow_little1);
                        }
                        else if ("大雪".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            weatherImg.setImageResource(R.mipmap.snow_2);
                        }
                        else if ("中雪".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            weatherImg.setImageResource(R.mipmap.snow);
                        }
                        else if ("雾".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            weatherImg.setImageResource(R.mipmap.fog);
                        }
                        else if ("霾".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            weatherImg.setImageResource(R.mipmap.chinaz6);
                        }
                        break;
                    case 1:
                        mDairyWeatherModel = dairyWeatherModelsList.get(1);
                        tomorrowWeatherTmp.setText(mDairyWeatherModel.getTmp_min() + "℃ / " + mDairyWeatherModel.getTmp_max() + "℃");
                        tomorrowWeather.setText(mDairyWeatherModel.getCond_txt_d());
                        tomorrowDate.setText(mDairyWeatherModel.getDate());
                        if ("晴".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            tomorrowWeatherImg.setImageResource(R.mipmap.qing);
                        }
                        else if ("多云".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            tomorrowWeatherImg.setImageResource(R.mipmap.cloudy_more);
                        }
                        else if ("雷阵雨".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            tomorrowWeatherImg.setImageResource(R.mipmap.chinaz22);
                        }
                        else if ("雨夹雪".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            tomorrowWeatherImg.setImageResource(R.mipmap.chinaz18);
                        }
                        else if ("阵雨".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            tomorrowWeatherImg.setImageResource(R.mipmap.rain_zhen);
                        }
                        else if ("阴".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            tomorrowWeatherImg.setImageResource(R.mipmap.wind);
                        }
                        else if ("小雨".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            tomorrowWeatherImg.setImageResource(R.mipmap.rain_little);
                        }
                        else if ("中雨".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            tomorrowWeatherImg.setImageResource(R.mipmap.rain_middle1);
                        }
                        else if ("大雨".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            tomorrowWeatherImg.setImageResource(R.mipmap.chinaz17);
                        }
                        else if ("暴雨".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            tomorrowWeatherImg.setImageResource(R.mipmap.rainstorm);
                        }
                        else if ("阵雪".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            tomorrowWeatherImg.setImageResource(R.mipmap.snow_little1);
                        }
                        else if ("大雪".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            tomorrowWeatherImg.setImageResource(R.mipmap.snow_2);
                        }
                        else if ("中雪".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            tomorrowWeatherImg.setImageResource(R.mipmap.snow);
                        }
                        else if ("雾".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            tomorrowWeatherImg.setImageResource(R.mipmap.fog);
                        }
                        else if ("霾".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            tomorrowWeatherImg.setImageResource(R.mipmap.chinaz6);
                        }
                        break;
                    case 2:
                        mDairyWeatherModel = dairyWeatherModelsList.get(2);
                        afterTomorrowWeatherTmp.setText(mDairyWeatherModel.getTmp_min() + "℃ / " + mDairyWeatherModel.getTmp_max() + "℃");
                        afterTomorrowWeather.setText(mDairyWeatherModel.getCond_txt_d());
                        afterTomorrowDate.setText(mDairyWeatherModel.getDate());
                        if ("晴".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            afterTomorrowWeatherImg.setImageResource(R.mipmap.qing);
                        }
                        else if ("多云".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            afterTomorrowWeatherImg.setImageResource(R.mipmap.cloudy_more);
                        }
                        else if ("雷阵雨".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            afterTomorrowWeatherImg.setImageResource(R.mipmap.chinaz22);
                        }
                        else if ("雨夹雪".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            afterTomorrowWeatherImg.setImageResource(R.mipmap.chinaz18);
                        }
                        else if ("阵雨".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            afterTomorrowWeatherImg.setImageResource(R.mipmap.rain_zhen);
                        }
                        else if ("阴".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            afterTomorrowWeatherImg.setImageResource(R.mipmap.wind);
                        }
                        else if ("小雨".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            afterTomorrowWeatherImg.setImageResource(R.mipmap.rain_little);
                        }
                        else if ("中雨".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            afterTomorrowWeatherImg.setImageResource(R.mipmap.rain_middle1);
                        }
                        else if ("大雨".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            afterTomorrowWeatherImg.setImageResource(R.mipmap.chinaz17);
                        }
                        else if ("暴雨".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            afterTomorrowWeatherImg.setImageResource(R.mipmap.rainstorm);
                        }
                        else if ("阵雪".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            afterTomorrowWeatherImg.setImageResource(R.mipmap.snow_little1);
                        }
                        else if ("大雪".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            afterTomorrowWeatherImg.setImageResource(R.mipmap.snow_2);
                        }
                        else if ("中雪".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            afterTomorrowWeatherImg.setImageResource(R.mipmap.snow);
                        }
                        else if ("雾".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            afterTomorrowWeatherImg.setImageResource(R.mipmap.fog);
                        }
                        else if ("霾".equals(mDairyWeatherModel.getCond_txt_d()))
                        {
                            afterTomorrowWeatherImg.setImageResource(R.mipmap.chinaz6);
                        }
                        break;
                }
            }
        }
        return;
    }
    public void style(View view) {
        // TODO 自动生成的方法存根
        // 城市设置 弹一个对话框出来让用户选择省份和城市
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择今日活动类型：");
        LayoutInflater inflater = LayoutInflater.from(this);
        view = inflater.inflate(R.layout.dialog, null);
        style = (Spinner) view.findViewById(R.id.style);
        style.setOnItemSelectedListener(this);

        List<String> styles = new ArrayList<String>();
        styles.add("常规");
        styles.add("户外");
        styles.add("旅行");
        styleAdapter = new SpinnerTextColorChangeTool(MainActivity.this, styles);
        styleAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        style.setAdapter(styleAdapter);

        builder.setView(view);
        builder.setPositiveButton("确定", this);
        builder.setNegativeButton("取消", this);
        builder.show();

    }
    /**
     * 刷新数据
     */
    public void refleshData(View view) {
        loadData();
        Toast.makeText(MainActivity.this, "数据刷新成功！", Toast.LENGTH_SHORT).show();
    }
    /**
     * 更换城市
     * @param view
     */
    public void change(View view) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, ChangeCityActivity.class);
        this.startActivity(intent);
        this.finish();
    }

    /**
     * 更改风格背景
     * @param index
     */
    private void ChangeBackgroundImage(int index) {
        // 根据index序号更改背景图片
        if (1 == index) {
            mainview.setBackgroundResource(R.mipmap.bg_change);
        } else if (2 == index) {
            mainview.setBackgroundResource(R.mipmap.fish);
        } else if (3 == index) {
            mainview.setBackgroundResource(R.mipmap.lvxing1);
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        index = position + 1;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        ChangeBackgroundImage(index);
    }
}