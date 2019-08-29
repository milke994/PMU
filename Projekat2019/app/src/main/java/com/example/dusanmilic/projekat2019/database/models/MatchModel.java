package com.example.dusanmilic.projekat2019.database.models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.dusanmilic.projekat2019.database.entities.MatchEntity;
import com.example.dusanmilic.projekat2019.database.repositories.MatchRepository;

import java.util.List;

public class MatchModel extends AndroidViewModel {

    private MatchRepository repository;

    public MatchModel(@NonNull Application application) {
        super(application);
        repository = new MatchRepository(application);
    }

    public void insert(MatchEntity matchEntity){repository.insert(matchEntity);}

    public LiveData<List<MatchEntity>> getMatches(int id1, int id2) {return repository.getMatches(id1, id2);}

    public void deleteMatches(int id1, int id2){repository.deleteMatches(id1, id2);}

    public void deleteAllMatches() { repository.deleteAllMatches(); }
}
