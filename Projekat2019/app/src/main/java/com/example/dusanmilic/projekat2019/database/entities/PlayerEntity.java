package com.example.dusanmilic.projekat2019.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "player")
public class PlayerEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    public PlayerEntity(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
