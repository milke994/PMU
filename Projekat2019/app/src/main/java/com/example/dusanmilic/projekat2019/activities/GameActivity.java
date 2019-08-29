package com.example.dusanmilic.projekat2019.activities;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.dusanmilic.projekat2019.R;
import com.example.dusanmilic.projekat2019.controll.Controller;
import com.example.dusanmilic.projekat2019.controll.ViewInterface;
import com.example.dusanmilic.projekat2019.drawings.GameView;
import com.example.dusanmilic.projekat2019.game.Ball;
import com.example.dusanmilic.projekat2019.game.Coordinates;
import com.example.dusanmilic.projekat2019.model.Model;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.text.BreakIterator;
import java.text.Format;

public class GameActivity extends Activity implements View.OnTouchListener, ViewInterface {
    private static final int REQUEST_FOR_END_GAME = 1;


    private TextView textViewPlayer1Name, textViewPlayer2Name, textViewTime;

    private static TextView textViewPlayer1Score, textViewPlayer2Score;

    private Model model;

    private GameView gameView;
    private Controller controller;
    private AsyncTask timer;
    private int seconds;
    private int time;
    private int player1Score, player2Score;
    private boolean finished;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        textViewPlayer1Name = findViewById(R.id.textViewPlayer1Name);
        textViewPlayer2Name = findViewById(R.id.textViewPlayer2Name);
        textViewPlayer1Score = findViewById(R.id.textViewPlayer1Score);
        textViewPlayer2Score = findViewById(R.id.textViewPlayer2Score);
        finished = false;
        Intent intent = getIntent();
        model = (Model) intent.getSerializableExtra("options");
        textViewTime = findViewById(R.id.textViewTime);
        gameView = new GameView(this);
        gameView = findViewById(R.id.game_image_view);
        gameView.setBackground(model.getCourtType());
        gameView.setSpeed(model.getSpeed());
        if(model.isCanceled()){
            gameView.set(model);
            player2Score = model.getPlayer2Score();
            player1Score = model.getPlayer1Score();
        } else{
            gameView.init(model);
            player1Score = 0;
            player2Score = 0;
        }
        controller = new Controller(this, gameView, model.getGameVsType());
        if(model.isCanceled()) controller.setPlayer1Turn(model.isPlayer1Turn());
        model.setCanceled(false);
        gameView.setOnTouchListener(this);

        time = model.getTime();
        seconds = model.getSeconds();

        textViewPlayer1Name.setText(model.getPlayer1Name());
        textViewPlayer2Name.setText(model.getPlayer2Name());

        textViewPlayer1Score.setText(String.valueOf(player1Score));
        textViewPlayer2Score.setText(String.valueOf(player2Score));

        if(model.getGameType() == Model.GameType.TIMED){
            timer = new Timer(this, textViewTime).execute();
        } else {
            timer = new Timer(this, textViewTime).execute();
            textViewTime.setText(String.valueOf(model.getNumberOfGoals()));
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x;
        float y;

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                controller.firstPointerDown(x, y);
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                x = event.getX();
                y = event.getY();
                controller.adittionalTouchDown(x, y);
                break;

            case MotionEvent.ACTION_UP:
                x = event.getX();
                y = event.getY();
                controller.lastPointerUp(x, y);
                break;
        }
        return true;
    }

    @Override
    public void updateView() {
        //gameView.invalidate();
    }

    @Override
    public void onBackPressed() {
        model.setCanceled(true);
        model.setAllballs(gameView.getAllBallsCoordinates());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        gameView.getPlayer1Players().get(0).getBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream);
        model.setPlayer1Image(stream.toByteArray());
        ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
        gameView.getPlayer2Players().get(0).getBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream2);
        model.setPlayer2Image(stream2.toByteArray());
        model.setPlayer1Turn(controller.isPlayer1Turn());
        timer.cancel(false);
        model.setPlayer1Score(Integer.parseInt(textViewPlayer1Score.getText().toString()));
        model.setPlayer2Score(Integer.parseInt(textViewPlayer2Score.getText().toString()));
    }

    public static void setPlayer1Score(int i) { textViewPlayer1Score.setText(String.valueOf(i)); }

    public static void setPlayer2Score(int i){
        textViewPlayer2Score.setText(String.valueOf(i));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_FOR_END_GAME && resultCode == RESULT_OK){
            setResult(RESULT_OK, data);
            finish();
        }
    }



    public class Timer extends AsyncTask<Void, Integer, Void>{

        private TextView timeTextView;
        private GameActivity gameActivity;
        private boolean lastPlayerTurn;
        private int timer;

        public Timer(GameActivity gameActivity, TextView timeTextView) {
            this.gameActivity = gameActivity;
            this.timeTextView = timeTextView;
            lastPlayerTurn = controller.isPlayer1Turn();
            timer = 0;

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if(model.getGameType() == Model.GameType.TIMED) {
                int minutes = values[0];
                int seconds = values[1];
                if (seconds == 60) {
                    this.timeTextView.setText(String.valueOf(minutes) + ":00");
                } else {
                    if (seconds < 10) {
                        this.timeTextView.setText(String.valueOf(minutes) + ":0" + String.valueOf(seconds));
                    } else {
                        this.timeTextView.setText(String.valueOf(minutes) + ":" + String.valueOf(seconds));
                    }
                }
            }
        }

        @Override
        protected void onCancelled() {
            model.setTime(time);
            model.setSeconds(seconds);
            Intent intent = new Intent();
            intent.putExtra("options", model);
            setResult(RESULT_CANCELED, intent);
            finished = true;
            finish();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent intent = new Intent(gameActivity, EndGameActivity.class);
            model.setPlayer1Score(Integer.parseInt(textViewPlayer1Score.getText().toString()));
            model.setPlayer2Score(Integer.parseInt(textViewPlayer2Score.getText().toString()));
            intent.putExtra("options", model);
            finished = true;
            startActivityForResult(intent, REQUEST_FOR_END_GAME);
        }



        @Override
        protected Void doInBackground(Void... voids) {
            while (true) {
                if(isCancelled()) break;
                if (model.getGameType() == Model.GameType.TIMED) {
                    if(controller.isPlayer1Turn() == lastPlayerTurn){
                        timer++;
                    } else{
                        lastPlayerTurn = controller.isPlayer1Turn();
                        timer = 0;
                    }
                    if(timer >= 4){
                        if(lastPlayerTurn){
                            controller.setCancel(true);
                            controller.setPlayer1Turn(false);
                            lastPlayerTurn = controller.isPlayer1Turn();
                            timer = 0;
                        } else {
                            controller.setCancel(true);
                            controller.setPlayer1Turn(true);
                            lastPlayerTurn = controller.isPlayer1Turn();
                            timer = 0;
                        }
                    }
                    publishProgress(time, seconds);
                    if (seconds == 60) {
                        time--;
                    }
                    seconds--;
                    if (seconds == 0 && time != 0) {
                        seconds = 60;
                    }
                    if (seconds == 0 && time == 0) {
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    if(gameView.getPlayer1Score() == model.getNumberOfGoals()
                            || gameView.getPlayer2Score() == model.getNumberOfGoals()){
                        break;
                    }
                    if(controller.isPlayer1Turn() == lastPlayerTurn){
                        timer++;
                    } else{
                        lastPlayerTurn = controller.isPlayer1Turn();
                        timer = 0;
                    }
                    if(timer >= 4){
                        if(lastPlayerTurn){
                            controller.setCancel(true);
                            controller.setPlayer1Turn(false);
                            lastPlayerTurn = controller.isPlayer1Turn();
                            timer = 0;
                        } else {
                            controller.setCancel(true);
                            controller.setPlayer1Turn(true);
                            lastPlayerTurn = controller.isPlayer1Turn();
                            timer = 0;
                        }
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(!finished){
            model.setCanceled(true);
            model.setAllballs(gameView.getAllBallsCoordinates());
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            gameView.getPlayer1Players().get(0).getBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream);
            model.setPlayer1Image(stream.toByteArray());
            ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
            gameView.getPlayer2Players().get(0).getBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream2);
            model.setPlayer2Image(stream2.toByteArray());
            model.setPlayer1Turn(controller.isPlayer1Turn());
            timer.cancel(false);
            model.setPlayer1Score(Integer.parseInt(textViewPlayer1Score.getText().toString()));
            model.setPlayer2Score(Integer.parseInt(textViewPlayer2Score.getText().toString()));
            model.setTime(time);
            model.setSeconds(seconds);
            SharedPreferences preferences = this.getSharedPreferences("gamePreference", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(model);
            editor.putString("continueGame", json);
            editor.apply();
            finished = true;
        }
    }
}
