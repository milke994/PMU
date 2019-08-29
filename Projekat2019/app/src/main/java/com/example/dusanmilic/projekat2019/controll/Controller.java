package com.example.dusanmilic.projekat2019.controll;


import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.os.AsyncTask;

import com.example.dusanmilic.projekat2019.constants.Constants;
import com.example.dusanmilic.projekat2019.drawings.GameView;
import com.example.dusanmilic.projekat2019.game.Player;
import com.example.dusanmilic.projekat2019.model.Model;

import java.util.Random;

public class Controller implements GestureListener{

    private GestureDetector gestureDetector;
    private ViewInterface viewInterface;
    private GameView gameView;
    private boolean player1Turn;
    private boolean playerToMove;
    private int position;
    private Paint darkPaint, normalPaint;
    private Model.GameVsType gameVsType;

    public Controller(ViewInterface viewInterface, GameView gameView, Model.GameVsType gameVsType) {
        darkPaint = new Paint();
        darkPaint.setStyle(Paint.Style.FILL);
        ColorFilter filter = new LightingColorFilter(0xFF7F7F7F, 0x00000000);
        darkPaint.setColorFilter(filter);
        normalPaint = new Paint();
        normalPaint.setStyle(Paint.Style.FILL);
        this.viewInterface = viewInterface;
        this.gameView = gameView;
        this.gestureDetector = new GestureDetector();
        this.gestureDetector.setGestureListener(this);
        this.setPlayer1Turn(true);
        playerToMove = false;
        position = 0;
        this.gameVsType = gameVsType;
    }

    public void setPlayer1Turn(boolean player1Turn) {
        this.player1Turn = player1Turn;
        if(this.player1Turn){
            for (Player player: gameView.getPlayer1Players()) {
                player.setPaint(normalPaint);
            }
            for (Player player: gameView.getPlayer2Players()) {
                player.setPaint(darkPaint);
            }
        } else {
            for (Player player: gameView.getPlayer1Players()) {
                player.setPaint(darkPaint);
            }
            for (Player player: gameView.getPlayer2Players()) {
                player.setPaint(normalPaint);
            }
            if(this.gameVsType == Model.GameVsType.COMPUTER){
                new ComputerMoveAsyncTask(gameView).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        }
    }

    public void setCancel(boolean cancel) { this.gestureDetector.setCancel(cancel); }

    public boolean isPlayer1Turn() {
        return player1Turn;
    }

    public void firstPointerDown(float x, float y){
        if(gestureDetector.firstDown(x, y)){
            viewInterface.updateView();
        }
    }

    public void lastPointerUp(float x, float y){
        if(gestureDetector.lastUp(x, y)){
            viewInterface.updateView();
        }
    }

    public void adittionalTouchDown(float x, float y){
        gestureDetector.aditionalTouchDown(x, y);
    }

    @Override
    public boolean onFirstTouchDown(float x, float y) {
        boolean result = false;
        if(player1Turn){
            position = 0;
            for (Player player: gameView.getPlayer1Players()) {
                if(result){
                    break;
                }
                int xx = (int) x;
                int yy = (int) y;
                int endx = (int) (player.getCenterX() + player.getRadius());
                int endy = (int) (player.getCenterY() + player.getRadius());
                if(xx >= player.getDrawingX() && xx <= endx && yy >= player.getDrawingY() && yy <= endy){
                    playerToMove = true;
                    result = true;
                } else {
                    position++;
                }
            }
        } else {
            position = 0;
            for (Player player: gameView.getPlayer2Players()) {
                if(result){
                    break;
                }
                int xx = (int) x;
                int yy = (int) y;
                int endx = (int) (player.getCenterX() + player.getRadius());
                int endy = (int) (player.getCenterY() + player.getRadius());
                if(xx >= player.getDrawingX() && xx <= endx && yy >= player.getDrawingY() && yy <= endy){
                    playerToMove = true;
                    result = true;
                } else {
                    position++;
                }
            }
        }

        return result;
    }

    @Override
    public boolean onAllTouchesUp(float x, float y) {
        boolean result = false;
        if(playerToMove && !gestureDetector.isCancel()){
            if(player1Turn){

                Player player = gameView.getPlayer1Players().get(position);

                player.setVelocityX((int) (Constants.movement * (x - player.getCenterX())));
                player.setVelocityY((int) (Constants.movement * (y - player.getCenterY())));

                playerToMove = false;
                setPlayer1Turn(false);
                result = true;
            }
            else {
                if (this.gameVsType == Model.GameVsType.PLAYER) {
                    Player player = gameView.getPlayer2Players().get(position);

                    player.setVelocityX((int) (Constants.movement * (x - player.getCenterX())));
                    player.setVelocityY((int) (Constants.movement * (y - player.getCenterY())));

                    playerToMove = false;
                    setPlayer1Turn(true);
                    result = true;
                }
            }
        }
        return result;
    }

    class ComputerMoveAsyncTask extends AsyncTask<Void, Void, Void>{
        private GameView gameView;
        private Random random;

        public ComputerMoveAsyncTask(GameView gameView) {
            this.gameView = gameView;
            random = new Random();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int position = random.nextInt(4);
            Player player = gameView.getPlayer2Players().get(position);

            player.setVelocityX((int) (Constants.movement * (gameView.getFootball().getCenterX() - player.getCenterX())));
            player.setVelocityY((int) (Constants.movement * (gameView.getFootball().getCenterY() - player.getCenterY())));

            playerToMove = false;
            setPlayer1Turn(true);
            return null;
        }
    }

}
