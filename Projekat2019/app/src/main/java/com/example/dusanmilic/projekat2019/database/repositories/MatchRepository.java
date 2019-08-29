package com.example.dusanmilic.projekat2019.database.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.dusanmilic.projekat2019.database.GameDatabase;
import com.example.dusanmilic.projekat2019.database.dao.MatchDAO;
import com.example.dusanmilic.projekat2019.database.entities.MatchEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MatchRepository {
    private MatchDAO matchDAO;

    public MatchRepository(Application application){
        GameDatabase gameDatabase = GameDatabase.getInstance(application);
        matchDAO = gameDatabase.getmatchDAO();
    }

    public void insert(MatchEntity matchEntity){ new InsertMatchAsyncTask(matchDAO).execute(matchEntity); }

    public void deleteAllMatches() { new DeleteAllMatchesAsyncTask(matchDAO).execute();}

    public LiveData<List<MatchEntity>> getMatches(int id1, int id2){AsyncTask task = new FindMatchesAsyncTask(matchDAO).execute(id1, id2);
        try {
            return (LiveData<List<MatchEntity>>) task.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteMatches(int id1, int id2) {new DeleteMatchesAsyncTask(matchDAO).execute(id1, id2);}



    private static class InsertMatchAsyncTask extends AsyncTask<MatchEntity, Void, Void>{

        private MatchDAO matchDAO;

        public InsertMatchAsyncTask(MatchDAO matchDAO) {
            this.matchDAO = matchDAO;
        }

        @Override
        protected Void doInBackground(MatchEntity... matchEntities) {
            matchDAO.insert(matchEntities[0]);
            return null;
        }
    }

    private static class FindMatchesAsyncTask extends AsyncTask<Integer, Void, LiveData<List<MatchEntity>>>{

        private MatchDAO matchDAO;

        public FindMatchesAsyncTask(MatchDAO matchDAO) {
            this.matchDAO = matchDAO;
        }

        @Override
        protected LiveData<List<MatchEntity>> doInBackground(Integer... integers) {
            return matchDAO.getMatches(integers[0], integers[1]);
        }
    }

    private static class DeleteMatchesAsyncTask extends AsyncTask<Integer, Void, Void>{
        private MatchDAO matchDAO;

        public DeleteMatchesAsyncTask(MatchDAO matchDAO) {
            this.matchDAO = matchDAO;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            matchDAO.deleteMatches(integers[0], integers[1]);
            return null;
        }
    }

    private static class DeleteAllMatchesAsyncTask extends AsyncTask<Void, Void, Void>{

        private MatchDAO matchDAO;

        public DeleteAllMatchesAsyncTask(MatchDAO matchDAO) {
            this.matchDAO = matchDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            matchDAO.deleteAllMatches();
            return null;
        }
    }

}
