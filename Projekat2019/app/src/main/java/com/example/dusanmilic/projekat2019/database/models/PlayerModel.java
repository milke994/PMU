package com.example.dusanmilic.projekat2019.database.models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.example.dusanmilic.projekat2019.database.entities.PlayerEntity;
import com.example.dusanmilic.projekat2019.database.repositories.PlayerRepository;

public class PlayerModel extends AndroidViewModel {
    private PlayerRepository repository;

    public PlayerModel(@NonNull Application application) {
        super(application);
        repository = new PlayerRepository(application);
    }

    public void insert(PlayerEntity playerEntity){ repository.insert(playerEntity); }

    public PlayerEntity find(String name){ return repository.findPlayer(name); }

    public PlayerEntity find(int id){ return repository.findPlayer(id); }
}
