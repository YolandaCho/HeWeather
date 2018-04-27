package com.heweather;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by Administrator on 2018/3/29.
 * 启动页
 */

public class WelcomeActivity extends Activity implements RadioGroup.OnCheckedChangeListener {
    private RadioGroup mode;
    private RadioButton general;
    private RadioButton outdoors;
    private RadioButton tour;
    public static int mode_index = 1;

    private final long SPLASH_LENGTH = 2000;
    Handler handler = new Handler();

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        SharedPreferences sharePreferences = this.getSharedPreferences("share",
                MODE_PRIVATE);
        boolean isFirstRun = sharePreferences.getBoolean("isFirstRun", true);
        SharedPreferences.Editor editor = sharePreferences.edit();

        if (isFirstRun) {
            editor.putBoolean("isFirstRun", false);
            editor.commit();
            setContentView(R.layout.select_style_activity);

            mode = (RadioGroup) findViewById(R.id.mode_radioGroup);
            mode.setOnCheckedChangeListener(this);

            general = (RadioButton) findViewById(R.id.general);
            outdoors = (RadioButton) findViewById(R.id.outdoors);
            tour = (RadioButton) findViewById(R.id.tour);

        } else {
            setContentView(R.layout.welcome_activity);
            handler.postDelayed(new Runnable() {
                public void run() {
                    Intent intent = new Intent(WelcomeActivity.this,
                            MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, SPLASH_LENGTH);
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        if (checkedId == general.getId()) {
            mode_index = 1;
        } else if (checkedId == outdoors.getId()) {
            mode_index = 2;
        } else if (checkedId == tour.getId()) {
            mode_index = 3;
        }
    }

    public void start(View view) {
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
