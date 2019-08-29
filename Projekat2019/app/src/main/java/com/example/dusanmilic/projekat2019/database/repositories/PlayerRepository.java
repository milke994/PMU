package com.example.dusanmilic.projekat2019.database.repositories;

import android.app.Application;
import android.os.AsyncTask;

import com.example.dusanmilic.projekat2019.database.GameDatabase;
import com.example.dusanmilic.projekat2019.database.dao.PlayerDAO;
import com.example.dusanmilic.projekat2019.database.entities.PlayerEntity;

import java.util.concurrent.ExecutionException;


public class PlayerRepository {
    private PlayerDAO playerDAO;

    public PlayerRepository(Application application){
        GameDatabase gameDatabase = GameDatabase.getInstance(application);
        playerDAO = gameDatabase.getplayerDAO();
    }

    public void insert(PlayerEntity playerEntity){ new InsertPlayerAsyncTask(this.playerDAO).execute(playerEntity);}

    public PlayerEntity findPlayer(final String name){
        AsyncTask<String, Void, PlayerEntity> findPlayerAsyncTask = new FindPlayerAsyncTask(this.playerDAO).execute(name);
        try {
            return findPlayerAsyncTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public PlayerEntity findPlayer(int id){
        AsyncTask task = new FindPlayerByIdAsyncTask(this.playerDAO).execute(id);
        try {
            return (PlayerEntity) task.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class InsertPlayerAsyncTask extends AsyncTask<PlayerEntity, Void, Void>{

        private PlayerDAO playerDAO;

        private InsertPlayerAsyncTask(PlayerDAO playerDAO){this.playerDAO = playerDAO;}

        @Override
        protected Void doInBackground(PlayerEntity... playerEntities) {
            playerDAO.insert(playerEntities[0]);
            return null;
        }
    }

    private static class FindPlayerAsyncTask extends AsyncTask<String, Void, PlayerEntity> {
        private PlayerDAO playerDAO;

        public FindPlayerAsyncTask(PlayerDAO playerDAO) {
            this.playerDAO = playerDAO;
        }

        @Override
        protected PlayerEntity doInBackground(String... strings) {
            PlayerEntity playerEntity = playerDAO.findPlayer(strings[0]);
            return playerEntity;
        }
    }

    private static class FindPlayerByIdAsyncTask extends AsyncTask<Integer, Void, PlayerEntity>{
        PlayerDAO playerDAO;

        public FindPlayerByIdAsyncTask(PlayerDAO playerDAO) {
            this.playerDAO = playerDAO;
        }

        @Override
        protected PlayerEntity doInBackground(Integer... integers) {
            PlayerEntity playerEntity = playerDAO.findPlayer(integers[0]);
            return playerEntity;
        }
    }

}
