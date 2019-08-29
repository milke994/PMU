package com.example.dusanmilic.projekat2019.database.models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.dusanmilic.projekat2019.database.entities.PairEntity;
import com.example.dusanmilic.projekat2019.database.repositories.PairRepository;

import java.util.List;

public class PairModel extends AndroidViewModel {
    private PairRepository pairRepository;
    private LiveData<List<PairEntity>> allPairs;

    public PairModel(@NonNull Application application) {
        super(application);
        pairRepository = new PairRepository(application);
        allPairs = pairRepository.getAllPairs();
    }

    public LiveData<List<PairEntity>> getAllPairs() { return allPairs; }

    public void insert(PairEntity pairEntity) { pairRepository.insert(pairEntity); }

    public void update(PairEntity pairEntity) { pairRepository.update(pairEntity); }

    public void delete(PairEntity pairEntity) { pairRepository.delete(pairEntity); }

    public void deleteAllPaires() { pairRepository.deleteAllPairs(); }

    public PairEntity findPair(int id1, int id2) { return pairRepository.findPair(id1, id2);}

    public LiveData<Integer> getPlayer1Wins(int id) { return pairRepository.getPlayer1Wins(id); }

    public LiveData<Integer> getPlayer2Wins(int id) { return pairRepository.getPlayer2Wins(id); }


}
