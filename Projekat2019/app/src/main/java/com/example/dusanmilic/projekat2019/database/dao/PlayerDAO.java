package com.example.dusanmilic.projekat2019.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.dusanmilic.projekat2019.database.entities.PlayerEntity;

@Dao
public interface PlayerDAO {
    @Insert
    void insert(PlayerEntity player);

    @Query("select * from player where name = :name")
    PlayerEntity findPlayer(String name);

    @Query("select * from player where id = :playerid")
    PlayerEntity findPlayer(int playerid);
}
