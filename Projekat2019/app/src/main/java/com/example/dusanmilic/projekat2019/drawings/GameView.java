package com.example.dusanmilic.projekat2019.drawings;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;

import com.example.dusanmilic.projekat2019.R;
import com.example.dusanmilic.projekat2019.activities.GameActivity;
import com.example.dusanmilic.projekat2019.constants.Constants;
import com.example.dusanmilic.projekat2019.game.Ball;
import com.example.dusanmilic.projekat2019.game.Coordinates;
import com.example.dusanmilic.projekat2019.game.Football;
import com.example.dusanmilic.projekat2019.game.Player;
import com.example.dusanmilic.projekat2019.model.Model;

import java.util.ArrayList;


public class GameView extends AppCompatImageView {

    private Football football;

    private ArrayList<Player> player1Players, player2Players;
    private ArrayList<Ball> allBalls;
    private ArrayList<Coordinates> allBallsCoordinates;

    private Rect rect;

    private boolean firstOnDraw;
    private MediaPlayer ballMediaPlayer, crowdMediaPlayer;
    private int player1Score, player2Score;
    private double speed;


    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ArrayList<Coordinates> getAllBallsCoordinates() {
        return allBallsCoordinates;
    }

    public void setAllBallsCoordinates(ArrayList<Coordinates> allBallsCoordinates) {
        this.allBallsCoordinates = allBallsCoordinates;
    }

    public ArrayList<Player> getPlayer1Players() {
        return player1Players;
    }

    public ArrayList<Player> getPlayer2Players() {
        return player2Players;
    }

    public ArrayList<Ball> getAllBalls() {
        return allBalls;
    }

    public void setAllBalls(ArrayList<Ball> allBalls) {
        this.allBalls = allBalls;
    }

    public int getPlayer1Score() {
        return player1Score;
    }

    public int getPlayer2Score() {
        return player2Score;
    }

    public Football getFootball() {
        return football;
    }

    public void init(Model model){
        player1Score = player2Score = 0;
        ballMediaPlayer = new MediaPlayer();
        ballMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
        crowdMediaPlayer = new MediaPlayer();
        crowdMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
        ballMediaPlayer = MediaPlayer.create(getContext(), R.raw.football_kick);
        crowdMediaPlayer = MediaPlayer.create(getContext(), R.raw.goal);
        player1Players = new ArrayList<>();
        player2Players = new ArrayList<>();
        allBalls = new ArrayList<>();
        allBallsCoordinates = new ArrayList<>();
        Bitmap ball = BitmapFactory.decodeResource(getResources(),R.drawable.ball0);
        football = new Football(ball);
        allBalls.add(football);
        allBallsCoordinates.add(football.getCenter());
        Bitmap player1Bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), model.getTeam1()),
                110, 110, false);
        Bitmap player2Bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), model.getTeam2()),
                110, 110, false);

        for(int i  = 0; i < 4; i++) {
            Player player1 = new Player(player1Bitmap);
            player1Players.add(player1);
            allBalls.add(player1);
            allBallsCoordinates.add(player1.getCenter());
        }
        for(int i = 0; i < 4; i++){
            Player player2 = new Player(player2Bitmap);
            player2Players.add(player2);
            allBalls.add(player2);
            allBallsCoordinates.add(player2.getCenter());
        }
        firstOnDraw = true;
    }

    public void set(Model model){
        player1Score = model.getPlayer1Score();
        player2Score = model.getPlayer2Score();
        Bitmap player1Bitmap = BitmapFactory.decodeByteArray(model.getPlayer1Image(),0, model.getPlayer1Image().length);
        Bitmap player2Bitmap = BitmapFactory.decodeByteArray(model.getPlayer2Image(), 0, model.getPlayer2Image().length);
        allBalls = new ArrayList<>();
        allBallsCoordinates = new ArrayList<>();
        player1Players = new ArrayList<>();
        player2Players = new ArrayList<>();
        ballMediaPlayer = new MediaPlayer();
        ballMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
        ballMediaPlayer = MediaPlayer.create(getContext(), R.raw.football_kick);
        crowdMediaPlayer = new MediaPlayer();
        crowdMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
        crowdMediaPlayer = MediaPlayer.create(getContext(), R.raw.goal);
        Bitmap ball = BitmapFactory.decodeResource(getResources(),R.drawable.ball0);
        football = new Football(ball);
        football.setCenterX(model.getAllballs().get(0).getX());
        football.setCenterY(model.getAllballs().get(0).getY());
        allBalls.add(football);
        allBallsCoordinates.add(football.getCenter());
        for(int i  = 0; i < 4; i++) {
            Player player1 = new Player(player1Bitmap);
            player1.setCenterX(model.getAllballs().get(i + 1).getX());
            player1.setCenterY(model.getAllballs().get(i + 1).getY());
            player1Players.add(player1);
            allBalls.add(player1);
            allBallsCoordinates.add(player1.getCenter());
        }
        for(int i = 0; i < 4; i++){
            Player player2 = new Player(player2Bitmap);
            player2.setCenterX(model.getAllballs().get(i + 5).getX());
            player2.setCenterY(model.getAllballs().get(i + 5).getY());
            player2Players.add(player2);
            allBalls.add(player2);
            allBallsCoordinates.add(player2.getCenter());
        }
        firstOnDraw = false;
    }

    public void setSpeed(Model.Speed speed){
        if(speed == Model.Speed.SLOW){
            this.speed = Constants.low;
        } else if (speed == Model.Speed.MEDIUM){
            this.speed = Constants.medium;
        } else {
            this.speed = Constants.high;
        }
    }

    public void setBackground(Model.CourtType courtType) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = true;
        Bitmap background;
        if (courtType == Model.CourtType.GRASS) {
            background = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.grass_court_with_goals,
                    options), (int) Constants.SCREEN_WIDTH, (int) Constants.SCREEN_HEIGHT, false);
        } else {
            if (courtType == Model.CourtType.HARD) {
                background = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.hard_court_with_goals,
                        options), (int) Constants.SCREEN_WIDTH, (int) Constants.SCREEN_HEIGHT, false);
            } else {
                background = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.soft_court_with_goals,
                        options), (int) Constants.SCREEN_WIDTH, (int) Constants.SCREEN_HEIGHT, false);
            }
        }
        setImageBitmap(background);
    }



    private void setPlayer1Positions(Rect rect){
        Player player;
        for(int i = 0; i < player1Players.size(); i++){
            player = player1Players.get(i);
            switch (i){
                case 0:
                    player.setCenterX((int) (rect.left + 3 * player.getRadius()));
                    player.setCenterY(rect.centerY());
                    break;
                case 1:
                    player.setCenterX((int) (rect.centerX() - rect.centerX() / 3 - player.getRadius()));
                    player.setCenterY((int) (rect.centerY() - rect.centerY() / 2 - player.getRadius()));
                    break;
                case 2:
                    player.setCenterX((int) (rect.centerX() - rect.centerX() / 3 - player.getRadius()));
                    player.setCenterY((int) (rect.centerY() + rect.centerY() / 2 + player.getRadius()));
                    break;
                case 3:
                    player.setCenterX((int) (rect.centerX() - rect.centerX() / 3 + player.getRadius()));
                    player.setCenterY(rect.centerY());

            }
        }
    }

    private void setPlayer2Positions(Rect rect){
        Player player;
        for(int i = 0; i < player2Players.size(); i++){
            player = player2Players.get(i);

            switch (i){
                case 0:
                    player.setCenterX((int) (rect.right - 3 * player.getRadius()));
                    player.setCenterY(rect.centerY());
                    break;
                case 1:
                    player.setCenterX((int) (rect.centerX() + rect.centerX() / 3 + player.getRadius()));
                    player.setCenterY((int) (rect.centerY() - rect.centerY() / 2 - player.getRadius()));
                    break;
                case 2:
                    player.setCenterX((int) (rect.centerX() + rect.centerX() / 3 + player.getRadius()));
                    player.setCenterY((int) (rect.centerY() + rect.centerY() / 2 + player.getRadius()));
                    break;
                case 3:
                    player.setCenterX((int) (rect.centerX() + rect.centerX() / 3 - player.getRadius()));
                    player.setCenterY(rect.centerY());

            }
        }
    }





    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        rect = canvas.getClipBounds();

        if(firstOnDraw) {
            setPlayer1Positions(rect);
            setPlayer2Positions(rect);
            football.setCenterX(rect.centerX());
            football.setCenterY(rect.centerY());
            firstOnDraw = false;
            for (Ball ball: allBalls) {
                ball.setVelocityX(0);
                ball.setVelocityY(0);
            }
        }

        for(Ball player : allBalls) {
            if (player.getVelocityX() != 0 || player.getVelocityY() != 0) {
                player.setAcelerationX((int) (-player.getVelocityX() * Constants.friction));
                player.setAcelerationY((int) (-player.getVelocityY() * Constants.friction));
                player.setVelocityX((int) (player.getVelocityX() + player.getAcelerationX() * speed));
                player.setVelocityY((int) (player.getVelocityY() + player.getAcelerationY() * speed));
                player.setCenterX(player.getCenterX() + player.getVelocityX() / 2);
                limitX(player);
                player.setCenterY(player.getCenterY() + player.getVelocityY() / 2);
                limitY(player);

                if (Math.abs(player.getVelocityX() * player.getVelocityX() + player.getVelocityY() * player.getVelocityY()) < 0.1f) {
                    player.setVelocityX(0);
                    player.setVelocityY(0);
                }
            }
        }
        checkCollision();
        isItAGoal();




        synchronized (canvas) {
            int i = 0;
            for (Ball ball : allBalls) {
                allBallsCoordinates.get(i).setX(ball.getCenterX());
                allBallsCoordinates.get(i).setY(ball.getCenterY());
                i++;
                canvas.drawBitmap(ball.getBitmap(), ball.getDrawingX(),
                        ball.getDrawingY(), ball.getPaint());
            }
        }
        invalidate();

    }

    private void checkCollision() {
        for(Ball movingball : allBalls){
                for (Ball ball : allBalls) {
                    if (movingball != ball) {
                        double d = Math.sqrt((movingball.getCenterX() - ball.getCenterX())
                                * (movingball.getCenterX() - ball.getCenterX())
                                + (movingball.getCenterY() - ball.getCenterY())
                                * (movingball.getCenterY() - ball.getCenterY()));
                        double r = movingball.getRadius() + ball.getRadius();
                        if (d < r) {

                            if (ball == football) {
                                ballMediaPlayer.start();
                            }
                            movingball.setCenterX((int) (movingball.getCenterX() - (d - r) / 2));
                            limitX(movingball);
                            movingball.setCenterY((int) (movingball.getCenterY() - (d - r) / 2));
                            limitY(movingball);
                            ball.setCenterX((int) (ball.getCenterX() + (d - r) / 2));
                            limitX(movingball);
                            ball.setCenterY((int) (ball.getCenterY() + (d - r) / 2));
                            limitY(movingball);


                            int xVelocityDiff = movingball.getVelocityX() - ball.getVelocityX();
                            int yVelocityDiff = movingball.getVelocityY() - ball.getVelocityY();

                            int xDist = ball.getCenterX() - movingball.getCenterX();
                            int yDist = ball.getCenterY() - movingball.getCenterY();

                            if (xVelocityDiff * xDist + yVelocityDiff * yDist >= 0) {
                                double angle = -Math.atan2(ball.getCenterY() - movingball.getCenterY(), ball.getCenterX() - movingball.getCenterX());

                                float m1 = movingball.getMass();
                                float m2 = ball.getMass();

                                Coordinates u1;
                                Coordinates u2;

                                u1 = this.rotate(movingball.getVelocity(), angle);
                                u2 = this.rotate(ball.getVelocity(), angle);

                                Coordinates v1 = new Coordinates();
                                Coordinates v2 = new Coordinates();

                                v1.setX((int) (u1.getX() * (m1 - m2) / (m1 + m2) + u2.getX() * 2 * m2 / (m1 + m2)));
                                v1.setY(u1.getY());

                                v2.setX((int) (u2.getX() * (m1 - m2) / (m1 + m2) + u1.getX() * 2 * m2 / (m1 + m2)));
                                v2.setY(u2.getY());


                                u1 = rotate(v1, -angle);
                                u2 = rotate(v2, -angle);

                                movingball.setVelocityX(u1.getX());
                                movingball.setVelocityY(u1.getY());

                                ball.setVelocityX(u2.getX());
                                ball.setVelocityY(u2.getY());
                            }
                        }
                    }
                }
        }
    }

    private void isItAGoal(){
        if((football.getCenterX() > rect.right - Constants.goalWidth)
                && (football.getDrawingY() > rect.centerY() - Constants.goalHeight / 2)
                && (football.getCenterY() + football.getRadius() < rect.centerY() + Constants.goalHeight / 2)){
            this.player1Score++;
            GameActivity.setPlayer1Score(this.player1Score);
            crowdMediaPlayer.start();
            this.firstOnDraw = true;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(football.getCenterX() < rect.left + Constants.goalWidth
                && football.getDrawingY() > rect.centerY() - Constants.goalHeight / 2
                && football.getCenterY() + football.getRadius() < rect.centerY() + Constants.goalHeight / 2){
            this.player2Score++;
            GameActivity.setPlayer2Score(this.player2Score);
            crowdMediaPlayer.start();
            this.firstOnDraw = true;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Coordinates rotate(Coordinates velocity, double angle){
        Coordinates result = new Coordinates();
        int x = (int) (velocity.getX() * Math.cos(angle) - velocity.getY() * Math.sin(angle));
        int y = (int) (velocity.getX() * Math.sin(angle) + velocity.getY() * Math.cos(angle));
        result.setX(x);
        result.setY(y);
        return result;
    }


    private void limitX(Ball player){
        if(player.getDrawingX() < rect.left){
            player.setCenterX((int) (rect.left + 2 * player.getRadius()));
            player.setVelocityX(-player.getVelocityX());
        }
        if(player.getCenterX() + player.getRadius() > rect.right){
            player.setCenterX((int) (rect.right - 2 * player.getRadius()));
            player.setVelocityX(-player.getVelocityX());
        }
    }

    private void limitY(Ball player){
        if(player.getDrawingY() < rect.top){
            player.setCenterY((int) (rect.top + 2 * player.getRadius()));
            player.setVelocityY(-player.getVelocityY());
        }
        if(player.getCenterY() + player.getRadius() > rect.bottom){
            player.setCenterY((int) (rect.bottom - 2 * player.getRadius()));
            player.setVelocityY(-player.getVelocityY());
        }

        if(player.getDrawingX() < rect.left + Constants.goalWidth){
            if(player.getDrawingY() < rect.centerY()){
                if(player.getDrawingY() + 2 * player.getRadius() > rect.centerY() - Constants.goalHeight / 2
                        && player.getDrawingY() < rect.centerY() - Constants.goalHeight / 2){
                    if(player.getCenterY() < rect.centerY() - Constants.goalHeight / 2) {
                        player.setCenterY((int) (rect.centerY() - Constants.goalHeight / 2 - player.getRadius()));
                    } else {
                        player.setCenterY((int) (rect.centerY() - Constants.goalHeight / 2 + player.getRadius()));
                    }
                    player.setVelocityY(-player.getVelocityY());
                }
            } else {
                if(player.getDrawingY() + 2 * player.getRadius() > rect.centerY() + Constants.goalHeight / 2
                        && player.getDrawingY() < rect.centerY() + Constants.goalHeight / 2){
                    if(player.getCenterY() < rect.centerY() + Constants.goalHeight / 2) {
                        player.setCenterY((int) (rect.centerY() + Constants.goalHeight / 2 - player.getRadius()));
                    } else {
                        player.setCenterY((int) (rect.centerY() + Constants.goalHeight / 2 + player.getRadius()));
                    }
                    player.setVelocityY(-player.getVelocityY());
                }
            }
        }
        if(player.getDrawingX() + 2 * player.getRadius() > rect.right - Constants.goalWidth){
            if(player.getDrawingY() < rect.centerY()){
                if(player.getDrawingY() + 2 * player.getRadius() > rect.centerY() - Constants.goalHeight / 2
                        && player.getDrawingY() < rect.centerY() - Constants.goalHeight / 2){
                    if(player.getCenterY() < rect.centerY() - Constants.goalHeight / 2) {
                        player.setCenterY((int) (rect.centerY() - Constants.goalHeight / 2 - player.getRadius()));
                    } else {
                        player.setCenterY((int) (rect.centerY() - Constants.goalHeight / 2 + player.getRadius()));
                    }
                    player.setVelocityY(-player.getVelocityY());
                }
            } else{
                if(player.getDrawingY() + 2 * player.getRadius() > rect.centerY() + Constants.goalHeight / 2
                        && player.getDrawingY() < rect.centerY() + Constants.goalHeight / 2){
                    if(player.getCenterY() < rect.centerY() + Constants.goalHeight / 2) {
                        player.setCenterY((int) (rect.centerY() + Constants.goalHeight / 2 - player.getRadius()));
                    } else {
                        player.setCenterY((int) (rect.centerY() + Constants.goalHeight / 2 + player.getRadius()));
                    }
                    player.setVelocityY(-player.getVelocityY());
                }
            }
        }
    }

}
