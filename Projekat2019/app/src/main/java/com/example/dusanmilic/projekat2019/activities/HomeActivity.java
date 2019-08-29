package com.example.dusanmilic.projekat2019.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.example.dusanmilic.projekat2019.R;
import com.example.dusanmilic.projekat2019.constants.Constants;
import com.example.dusanmilic.projekat2019.controll.Controller;
import com.example.dusanmilic.projekat2019.model.Model;
import com.google.gson.Gson;

import java.util.Locale;

public class HomeActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_FOR_OPTIONS = 1;
    private static final int REQUEST_CODE_FOR_PLAYING = 2;
    private static final int REQUEST_CODE_FOR_STATISTICS = 3;
    private static final int REQUEST_CODE_FOR_CONTINUE = 4;

    private Model model = new Model();
    private Model canceledModel = new Model();
    private Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        continueButton = findViewById(R.id.continueButtonId);
        SharedPreferences preferences = this.getSharedPreferences("gamePreference", MODE_PRIVATE);

        String jsonContinue = preferences.getString("continueGame", "");
        if(!jsonContinue.equals("")) {
            Gson gson = new Gson();
            canceledModel = gson.fromJson(jsonContinue, Model.class);
            this.continueButton.setVisibility(View.VISIBLE);
        } else{
            canceledModel = null;
        }
        String jsonOptions = preferences.getString("options", "");
        if(!jsonOptions.equals("")){
            Gson gson = new Gson();
            model = gson.fromJson(jsonOptions, Model.class);
        }
    }

    public void changeLanguageToSerbian(View view) {
        Resources resources = getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(new Locale("sr"));
        } else{
            configuration.locale = new Locale("sr");
        }
        resources.updateConfiguration(configuration, metrics);
        Intent refresh = new Intent(this, HomeActivity.class);
        startActivity(refresh);
        finish();
    }

    public void changeLanguageToEnglish(View view) {
        Resources resources = getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(new Locale("en"));
        } else{
            configuration.locale = new Locale("en");
        }
        resources.updateConfiguration(configuration, metrics);
        Intent refresh = new Intent(this, HomeActivity.class);
        startActivity(refresh);
        finish();
    }

    public void close(View view) {
        finish();
        System.exit(0);
    }

    public void optionsClicked(View view) {
        Intent setOptionsIntent = new Intent(this, OptionsActivity.class);
        setOptionsIntent.putExtra("options", model);
        startActivityForResult(setOptionsIntent, REQUEST_CODE_FOR_OPTIONS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_FOR_OPTIONS){
            if(resultCode == RESULT_OK){
                model = (Model) data.getSerializableExtra("options");
                SharedPreferences preferences = this.getSharedPreferences("gamePreference", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
//                editor.clear();
                Gson gson = new Gson();
                String json = gson.toJson(model);
                editor.putString("options", json);
                editor.apply();
//                editor.commit();
            }
        }
        if(requestCode == REQUEST_CODE_FOR_PLAYING && resultCode == RESULT_CANCELED){
            Model model = (Model) data.getSerializableExtra("options");
            if(model.isCanceled()) {
                this.canceledModel = (Model) data.getSerializableExtra("options");
                SharedPreferences preferences = this.getSharedPreferences("gamePreference", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
//            editor.clear();
                Gson gson = new Gson();
                String json = gson.toJson(canceledModel);
                editor.putString("continueGame", json);
                editor.apply();
//            editor.commit();
                this.model.setGameVsType(model.getGameVsType());
                this.model.setGameType(model.getGameType());
                if(this.model.getGameType() == Model.GameType.TIMED){
                    this.model.setTime(model.getTime());
                } else {
                    this.model.setNumberOfGoals(model.getNumberOfGoals());
                }
                this.continueButton.setVisibility(View.VISIBLE);
            }
        }
        if(requestCode == REQUEST_CODE_FOR_CONTINUE && resultCode == RESULT_CANCELED){
            this.canceledModel = (Model) data.getSerializableExtra("options");
            SharedPreferences preferences = this.getSharedPreferences("gamePreference", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
//            editor.clear();
            Gson gson = new Gson();
            String json = gson.toJson(canceledModel);
            editor.putString("continueGame", json);
            editor.apply();
//            editor.commit();
            this.model.setGameVsType(canceledModel.getGameVsType());
            this.model.setGameType(canceledModel.getGameType());
            if(this.model.getGameType() == Model.GameType.TIMED){
                this.model.setTime(canceledModel.getTime());
            } else {
                this.model.setNumberOfGoals(canceledModel.getNumberOfGoals());
            }
            if(canceledModel.isCanceled()) {
                this.continueButton.setVisibility(View.VISIBLE);
            }
        }
        if(requestCode == REQUEST_CODE_FOR_CONTINUE && resultCode == RESULT_OK){
            this.canceledModel = null;
            SharedPreferences preferences = this.getSharedPreferences("gamePreference", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
//            editor.clear();
            editor.remove("continueGame");
            editor.apply();
//            editor.commit();
            this.continueButton.setVisibility(View.INVISIBLE);
        }
        if(requestCode == REQUEST_CODE_FOR_PLAYING && resultCode == RESULT_OK){
            this.canceledModel = null;
            Model model = (Model) data.getSerializableExtra("options");
            this.model.setGameVsType(model.getGameVsType());
            this.model.setGameType(model.getGameType());
            if(this.model.getGameType() == Model.GameType.TIMED){
                this.model.setTime(model.getTime());
            } else {
                this.model.setNumberOfGoals(model.getNumberOfGoals());
            }
            this.continueButton.setVisibility(View.INVISIBLE);
        }
    }

    public void playButtonClicked(View view) {
        Intent intent = new Intent(this, PlayerNameActivity.class);
        intent.putExtra("options",model);
        startActivityForResult(intent, REQUEST_CODE_FOR_PLAYING);
    }

    public void statisticsClicked(View view) {
        Intent statisticsIntent = new Intent(this, StatictiscActivity.class);
        startActivityForResult(statisticsIntent, REQUEST_CODE_FOR_STATISTICS);
    }

    public void continueButtonClicked(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("options", canceledModel);
        startActivityForResult(intent, REQUEST_CODE_FOR_CONTINUE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences preferences = this.getSharedPreferences("gamePreference", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(model);
        editor.putString("options", json);
        editor.apply();
    }
}
