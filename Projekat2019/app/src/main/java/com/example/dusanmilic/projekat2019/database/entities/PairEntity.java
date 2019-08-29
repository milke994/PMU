package com.example.dusanmilic.projekat2019.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "playerpairs", foreignKeys = {@ForeignKey(entity = PlayerEntity.class,
        parentColumns = "id",
        childColumns = "player1Id",
        onDelete = CASCADE),
        @ForeignKey(entity = PlayerEntity.class,
                parentColumns = "id",
                childColumns = "player2Id",
                onDelete = CASCADE)})
public class PairEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int player1Id;
    private int player2Id;
    private int player1Wins, player2Wins;

    public PairEntity(int player1Id, int player2Id) {
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        player1Wins = 0;
        player2Wins = 0;
    }

    public void setPlayer1Wins(int player1Wins) {
        this.player1Wins = player1Wins;
    }

    public void setPlayer2Wins(int player2Wins) {
        this.player2Wins = player2Wins;
    }

    public int getPlayer1Wins() {
        return player1Wins;
    }

    public int getPlayer2Wins() {
        return player2Wins;
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

    public int getId() {
        return id;
    }

    public int getPlayer1Id() {
        return player1Id;
    }

    public int getPlayer2Id() {
        return player2Id;
    }
}
