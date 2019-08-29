package com.example.dusanmilic.projekat2019.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.dusanmilic.projekat2019.database.dao.MatchDAO;
import com.example.dusanmilic.projekat2019.database.dao.PairDAO;
import com.example.dusanmilic.projekat2019.database.dao.PlayerDAO;
import com.example.dusanmilic.projekat2019.database.entities.MatchEntity;
import com.example.dusanmilic.projekat2019.database.entities.PairEntity;
import com.example.dusanmilic.projekat2019.database.entities.PlayerEntity;

@Database(entities = {PlayerEntity.class,  PairEntity.class, MatchEntity.class}, version = 2)
public abstract class GameDatabase extends RoomDatabase{

    private static GameDatabase instance;

    public abstract PlayerDAO getplayerDAO();

    public abstract PairDAO getPairDAO();

    public abstract MatchDAO getmatchDAO();

    public static synchronized GameDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), GameDatabase.class, "game_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
