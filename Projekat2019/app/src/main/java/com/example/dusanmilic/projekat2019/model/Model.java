package com.example.dusanmilic.projekat2019.model;

import com.example.dusanmilic.projekat2019.game.Ball;
import com.example.dusanmilic.projekat2019.game.Coordinates;

import java.io.Serializable;
import java.util.ArrayList;

public class Model implements Serializable {

    public enum GameVsType{
        COMPUTER,
        PLAYER
    }

    public enum GameType{
        TIMED,
        GOAL_NUMBER
    }

    public enum CourtType{
        GRASS,
        HARD,
        SOFT
    }

    public enum Dificulty{
        BEGGINER,
        PRO,
        SUPERSTAR
    }

    public enum Speed{
        SLOW,
        MEDIUM,
        FAST
    }

    private ArrayList<Coordinates> allballs;
    private boolean isCanceled;
    private byte[] player1Image, player2Image;
    private boolean player1Turn;
    private GameVsType gameVsType;
    private CourtType courtType;
    private Dificulty dificulty;
    private Speed speed;
    private GameType gameType;
    private int numberOfGoals;
    private int time;
    private int player1Score, player2Score;
    private String player1Name;
    private String player2Name;
    private int minutes, seconds;
    private int team1, team2;

    public Model() {
        dificulty = Dificulty.BEGGINER;
        courtType = CourtType.GRASS;
        speed = Speed.SLOW;
        gameType = GameType.TIMED;
        time = 5;
        gameVsType = GameVsType.PLAYER;
        player1Score = 0;
        player2Score = 0;
        isCanceled = false;
        allballs = new ArrayList<>();
        player1Turn = true;
        seconds = 60;
    }

    public byte[] getPlayer1Image() {
        return player1Image;
    }

    public byte[] getPlayer2Image() {
        return player2Image;
    }

    public void setPlayer1Image(byte[] player1Image) {
        this.player1Image = player1Image;
    }

    public void setPlayer2Image(byte[] player2Image) {
        this.player2Image = player2Image;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public void setPlayer1Turn(boolean player1Turn) {
        this.player1Turn = player1Turn;
    }

    public boolean isPlayer1Turn() {
        return player1Turn;
    }

    public void setPlayer1Score(int player1Score) {
        this.player1Score = player1Score;
    }

    public void setPlayer2Score(int player2Score) {
        this.player2Score = player2Score;
    }

    public void setAllballs(ArrayList<Coordinates> allballs) {
        this.allballs = allballs;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    public ArrayList<Coordinates> getAllballs() {
        return allballs;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public int getPlayer1Score() {
        return player1Score;
    }

    public int getPlayer2Score() {
        return player2Score;
    }

    public void setCourtType(CourtType courtType) {
        this.courtType = courtType;
    }

    public void setDificulty(Dificulty dificulty) {
        this.dificulty = dificulty;
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    public CourtType getCourtType() {
        return courtType;
    }

    public Dificulty getDificulty() {
        return dificulty;
    }

    public Speed getSpeed() {
        return speed;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public GameVsType getGameVsType() {
        return gameVsType;
    }

    public void setGameVsType(GameVsType gameVsType) {
        this.gameVsType = gameVsType;
    }

    public void setNumberOfGoals(int numberOfGoals) {
        this.numberOfGoals = numberOfGoals;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }

    public GameType getGameType() {
        return gameType;
    }

    public int getNumberOfGoals() {
        return numberOfGoals;
    }

    public int getTime() {
        return time;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public void setTeam1(int team1) {
        this.team1 = team1;
    }

    public void setTeam2(int team2) {
        this.team2 = team2;
    }

    public int getTeam1() {
        return team1;
    }

    public int getTeam2() {
        return team2;
    }
}
