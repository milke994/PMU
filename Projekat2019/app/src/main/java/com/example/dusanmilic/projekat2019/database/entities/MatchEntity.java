package com.example.dusanmilic.projekat2019.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;



import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "matches", foreignKeys = {@ForeignKey(entity = PlayerEntity.class,
        parentColumns = "id",
        childColumns = "player1Id",
        onDelete = CASCADE),
        @ForeignKey(entity = PlayerEntity.class,
                parentColumns = "id",
                childColumns = "player2Id",
                onDelete = CASCADE)})
public class MatchEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int player1Id;
    private int player2Id;

    private int player1Score, player2Score;
    private String time;

    public MatchEntity(int player1Id, int player2Id) {
        this.player1Id = player1Id;
        this.player2Id = player2Id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPlayer1Id(int player1Id) {
        this.player1Id = player1Id;
    }

    public void setPlayer2Id(int player2Id) {
        this.player2Id = player2Id;
    }

    public int getPlayer1Id() {
        return player1Id;
    }

    public int getPlayer2Id() {
        return player2Id;
    }

    public void setPlayer1Score(int player1Score) {
        this.player1Score = player1Score;
    }

    public void setPlayer2Score(int player2Score) {
        this.player2Score = player2Score;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public int getPlayer1Score() {
        return player1Score;
    }

    public int getPlayer2Score() {
        return player2Score;
    }

    public String getTime() {
        return time;
    }
}
