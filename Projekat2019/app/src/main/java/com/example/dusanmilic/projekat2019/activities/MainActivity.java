package com.example.dusanmilic.projekat2019.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.dusanmilic.projekat2019.R;
import com.example.dusanmilic.projekat2019.constants.Constants;
import com.example.dusanmilic.projekat2019.model.Model;

public class MainActivity extends AppCompatActivity {

    private static final int LOADING_SCREEN_LOADING_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Constants.SCREEN_WIDTH = metrics.widthPixels;
        Constants.SCREEN_HEIGHT = metrics.heightPixels;
        Constants.goalHeight = 140 *  Constants.SCREEN_HEIGHT / 480;
        Constants.goalWidth = 40 * Constants.SCREEN_WIDTH / 800;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent loadingIntent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(loadingIntent);
                finish();
            }
        },LOADING_SCREEN_LOADING_TIME);
    }
}
