package com.example.dusanmilic.projekat2019.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.dusanmilic.projekat2019.database.entities.PairEntity;

import java.util.List;

@Dao
public interface PairDAO {

    @Update
    void update(PairEntity pairEntity);

    @Insert
    void insert(PairEntity pairEntity);

    @Delete
    void deletePair(PairEntity pairEntity);

    @Query("delete from playerpairs")
    void deleteAllPairs();

    @Query("select * from playerpairs")
    LiveData<List<PairEntity>> getAllPairs();

    @Query("select * from playerpairs where player1Id = :id1 and player2Id = :id2")
    PairEntity findPair(int id1, int id2);

    @Query("select player1Wins from playerpairs where id = :id")
    LiveData<Integer> getPlayer1Wins(int id);

    @Query("select player2Wins from playerpairs where id = :id")
    LiveData<Integer> getPlayer2Wins(int id);
}
