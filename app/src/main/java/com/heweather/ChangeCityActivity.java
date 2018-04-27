package com.heweather;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.heweather.util.DatabaseHandle;
import com.heweather.util.SpinnerTextColorChangeTool;

/**
 * Created by Administrator on 2018/3/31.
 * select city
 */

public class ChangeCityActivity extends Activity implements AdapterView.OnItemSelectedListener {
    private Spinner proSpinner; /** province */
    private Spinner citySpinner; /** city */
    ArrayAdapter<String> proAdapter; /** province adapter */
    private ArrayAdapter<String> cityAdapter; /** city adapter */
    private DatabaseHandle dbHandle;
    public static String cityName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change);
        proSpinner = (Spinner) findViewById(R.id.provinces);
        citySpinner = (Spinner) findViewById(R.id.cities);
        dbHandle = new DatabaseHandle(this);
        initData();
    }

    public void initData() {
        proAdapter = new SpinnerTextColorChangeTool(ChangeCityActivity.this, dbHandle.getAllProvinces());
        proAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        proSpinner.setAdapter(proAdapter);
        proSpinner.setOnItemSelectedListener(this);

        String proName = proSpinner.getSelectedItem().toString();
        cityAdapter = new SpinnerTextColorChangeTool(ChangeCityActivity.this, dbHandle.getCityByProvinceName(proName));
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(cityAdapter);
    }

    public void ensure(View view) {
        cityName=citySpinner.getSelectedItem().toString();
        SharedPreferences share = getSharedPreferences("name", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.putString("cn", cityName);
        editor.commit();
        Intent intent = new Intent();
        intent.setClass(ChangeCityActivity.this, MainActivity.class);
        this.startActivity(intent);
        finish();
    }

    public void back(View view) {
        Intent intent = new Intent();
        intent.setClass(ChangeCityActivity.this, MainActivity.class);
        this.startActivity(intent);
        this.finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                               long arg3) {
        String provinceName = proSpinner.getSelectedItem().toString();
        cityAdapter = new SpinnerTextColorChangeTool(ChangeCityActivity.this, dbHandle.getCityByProvinceName(provinceName));
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(cityAdapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

   }
}
