package com.example.dusanmilic.projekat2019.activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dusanmilic.projekat2019.R;
import com.example.dusanmilic.projekat2019.model.Model;

public class PlayerNameActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_FOR_PLAYING = 1;

    private RadioButton timeGameTypeRadio, goalGameTypeRadio;
    private RadioButton pvpGameModeRadio, pveGameModeRadio;
    private EditText player1NameEditText, player2NameEditText;
    private Spinner timesSpinner, goalsSpinner;
    private Model.GameType gameType;
    private int numberOfGoals, time;
    private Model.GameVsType gameMode;


    private Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_name);

        Intent intent = getIntent();
        model = (Model) intent.getSerializableExtra("options");
        gameType = model.getGameType();
        gameMode = model.getGameVsType();
        player1NameEditText = findViewById(R.id.player1NameEditText);
        player2NameEditText = findViewById(R.id.player2NameEditText);

        timesSpinner = findViewById(R.id.timeSpinnerId);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.times,
                android.R.layout.simple_spinner_dropdown_item);
        timesSpinner.setAdapter(adapter1);
        timesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        time = 3;
                        break;
                    case 1:
                        time = 5;
                        break;
                    case 2:
                        time = 10;
                        break;
                    case 3:
                        time = 15;
                        break;
                    case 4:
                        time = 20;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                time = 3;
            }
        });
        goalsSpinner = findViewById(R.id.goalsSpinnerId);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.goals,
                android.R.layout.simple_spinner_dropdown_item);
        goalsSpinner.setAdapter(adapter2);
        goalsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        numberOfGoals = 3;
                        break;
                    case 1:
                        numberOfGoals = 5;
                        break;
                    case 2:
                        numberOfGoals = 7;
                        break;
                    case 3:
                        numberOfGoals = 9;
                        break;
                    case 4:
                        numberOfGoals = 11;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                numberOfGoals = 3;
            }
        });

        timeGameTypeRadio = findViewById(R.id.timeGameTypeRadioButtonId);
        timeGameTypeRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goalsSpinner.setVisibility(View.INVISIBLE);
                timesSpinner.setVisibility(View.VISIBLE);
                gameType = Model.GameType.TIMED;
            }
        });

        goalGameTypeRadio = findViewById(R.id.goalsGameTypeRadioButtonId);
        goalGameTypeRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameType = Model.GameType.GOAL_NUMBER;
                timesSpinner.setVisibility(View.INVISIBLE);
                goalsSpinner.setVisibility(View.VISIBLE);
            }
        });

        pvpGameModeRadio = findViewById(R.id.pvpGameModeRadioButton);
        pvpGameModeRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameMode = Model.GameVsType.PLAYER;
                player2NameEditText.setVisibility(View.VISIBLE);
            }
        });

        pveGameModeRadio = findViewById(R.id.pveGameModeRadioButton);
        pveGameModeRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameMode = Model.GameVsType.COMPUTER;
                player2NameEditText.setVisibility(View.INVISIBLE);
            }
        });
        if(model.getGameVsType() == Model.GameVsType.COMPUTER){
            pvpGameModeRadio.setChecked(false);
            pveGameModeRadio.setChecked(true);
            player2NameEditText.setVisibility(View.INVISIBLE);
        } else {
            pvpGameModeRadio.setChecked(true);
            pveGameModeRadio.setChecked(false);
            player2NameEditText.setVisibility(View.VISIBLE);
        }
        if(model.getGameType() == Model.GameType.TIMED){
            timeGameTypeRadio.setChecked(true);
            goalGameTypeRadio.setChecked(false);
            timesSpinner.setVisibility(View.VISIBLE);
            goalsSpinner.setVisibility(View.INVISIBLE);
            switch (model.getTime()){
                case 3:
                    timesSpinner.setSelection(0);
                    break;
                case 5:
                    timesSpinner.setSelection(1);
                    break;
                case 10:
                    timesSpinner.setSelection(2);
                    break;
                case 15:
                    timesSpinner.setSelection(3);
                    break;
                case 20:
                    timesSpinner.setSelection(4);
                    break;
            }
        } else {
            timeGameTypeRadio.setChecked(false);
            goalGameTypeRadio.setChecked(true);
            timesSpinner.setVisibility(View.INVISIBLE);
            goalsSpinner.setVisibility(View.VISIBLE);
            switch (model.getNumberOfGoals()){
                case 3:
                    goalsSpinner.setSelection(0);
                    break;
                case 5:
                    goalsSpinner.setSelection(1);
                    break;
                case 7:
                    goalsSpinner.setSelection(2);
                    break;
                case 9:
                    goalsSpinner.setSelection(3);
                    break;
                case 11:
                    goalsSpinner.setSelection(4);
                    break;
            }
        }

    }

    public void playButtonClicked(View view) {
        if(player1NameEditText.getText().toString().isEmpty() ||
                (player2NameEditText.getText().toString().isEmpty() && gameMode == Model.GameVsType.PLAYER)){
            Toast.makeText(this, getString(R.string.player_names_validation), Toast.LENGTH_LONG).show();
        } else {
            model.setGameType(gameType);
            model.setGameVsType(gameMode);

            model.setPlayer1Name(player1NameEditText.getText().toString());

            if(gameMode == Model.GameVsType.PLAYER) {
                model.setPlayer2Name(player2NameEditText.getText().toString());
            } else{
                model.setPlayer2Name("Computer");
            }

            if(gameType == Model.GameType.TIMED){
                model.setTime(time);
            } else{
                model.setNumberOfGoals(numberOfGoals);
            }
            Intent intent = new Intent(this, TeamSelectActivity.class);
            intent.putExtra("options", model);
            startActivityForResult(intent,REQUEST_CODE_FOR_PLAYING);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_FOR_PLAYING && RESULT_OK == resultCode){
            setResult(RESULT_OK, data);
            finish();
        }
        if(requestCode == REQUEST_CODE_FOR_PLAYING && RESULT_CANCELED == resultCode){
            setResult(RESULT_CANCELED, data);
            finish();
        }
    }

    public void cancelButtonClicked(View view) {
        Intent intent = new Intent();
        intent.putExtra("options", model);
        setResult(RESULT_CANCELED, intent);
        finish();
    }
}
