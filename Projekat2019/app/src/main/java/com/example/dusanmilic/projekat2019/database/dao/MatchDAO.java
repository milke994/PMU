package com.example.dusanmilic.projekat2019.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.dusanmilic.projekat2019.database.entities.MatchEntity;

import java.util.List;

@Dao
public interface MatchDAO {
    @Insert
    void insert(MatchEntity matchEntity);

    @Query("delete from matches")
    void deleteAllMatches();

    @Query("select * from matches where player1Id = :id1 and player2Id = :id2")
    LiveData<List<MatchEntity>> getMatches(int id1, int id2);

    @Query("delete from matches where player1Id = :id1 and player2Id = :id2")
    void deleteMatches(int id1, int id2);
}
