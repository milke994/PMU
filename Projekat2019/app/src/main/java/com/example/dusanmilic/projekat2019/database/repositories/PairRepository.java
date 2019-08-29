package com.example.dusanmilic.projekat2019.database.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.dusanmilic.projekat2019.database.GameDatabase;
import com.example.dusanmilic.projekat2019.database.dao.PairDAO;
import com.example.dusanmilic.projekat2019.database.entities.PairEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class PairRepository {
    private PairDAO pairDAO;
    private LiveData<List<PairEntity>> allPairs;

    public PairRepository(Application application){
        GameDatabase gameDatabase = GameDatabase.getInstance(application);
        pairDAO = gameDatabase.getPairDAO();
        allPairs = pairDAO.getAllPairs();
    }

    public LiveData<List<PairEntity>> getAllPairs() { return allPairs; }

    public void insert(PairEntity pairEntity){ new InsertPairAsyncTask(pairDAO).execute(pairEntity); }

    public void update(PairEntity pairEntity){ new UpdatePairAsyncTask(pairDAO).execute(pairEntity); }

    public void delete(PairEntity pairEntity) { new DeletePairAsyncTask(pairDAO).execute(pairEntity); }

    public void deleteAllPairs() { new DeleteAllPairsAsyncTask(pairDAO).execute(); }

    public PairEntity findPair(int id1, int id2){ AsyncTask pair = new FindPairAsyncTask(pairDAO).execute(id1, id2);
        try {
            return (PairEntity) pair.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LiveData<Integer> getPlayer1Wins(int id){return pairDAO.getPlayer1Wins(id);}

    public LiveData<Integer> getPlayer2Wins(int id){return pairDAO.getPlayer2Wins(id);}



    private class InsertPairAsyncTask extends AsyncTask<PairEntity, Void, Void>{
        private PairDAO pairDAO;

        public InsertPairAsyncTask(PairDAO pairDAO) {
            this.pairDAO = pairDAO;
        }

        @Override
        protected Void doInBackground(PairEntity... pairEntities) {
            pairDAO.insert(pairEntities[0]);
            return null;
        }
    }

    private class DeletePairAsyncTask extends AsyncTask<PairEntity, Void, Void>{
        private PairDAO pairDAO;

        public DeletePairAsyncTask(PairDAO pairDAO) {
            this.pairDAO = pairDAO;
        }

        @Override
        protected Void doInBackground(PairEntity... pairEntities) {
            pairDAO.deletePair(pairEntities[0]);
            return null;
        }
    }

    private class DeleteAllPairsAsyncTask extends AsyncTask<Void, Void, Void>{
        private PairDAO pairDAO;

        public DeleteAllPairsAsyncTask(PairDAO pairDAO) {
            this.pairDAO = pairDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            pairDAO.deleteAllPairs();
            return null;
        }
    }

    private class FindPairAsyncTask extends AsyncTask<Integer, Void, PairEntity>{
        private PairDAO pairDAO;

        public FindPairAsyncTask(PairDAO pairDAO) {
            this.pairDAO = pairDAO;
        }

        @Override
        protected PairEntity doInBackground(Integer... integers) {
            return pairDAO.findPair(integers[0], integers[1]);
        }
    }

    private class UpdatePairAsyncTask extends AsyncTask<PairEntity, Void, Void>{
        private PairDAO pairDAO;

        public UpdatePairAsyncTask(PairDAO pairDAO) {
            this.pairDAO = pairDAO;
        }

        @Override
        protected Void doInBackground(PairEntity... pairEntities) {
            pairDAO.update(pairEntities[0]);
            return null;
        }
    }
}
