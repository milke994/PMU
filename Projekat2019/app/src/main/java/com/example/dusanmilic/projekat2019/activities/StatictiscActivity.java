package com.example.dusanmilic.projekat2019.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.dusanmilic.projekat2019.R;
import com.example.dusanmilic.projekat2019.adapters.PairsAdapter;
import com.example.dusanmilic.projekat2019.database.entities.PairEntity;
import com.example.dusanmilic.projekat2019.database.models.MatchModel;
import com.example.dusanmilic.projekat2019.database.models.PairModel;
import com.example.dusanmilic.projekat2019.database.models.PlayerModel;

import java.util.List;

public class StatictiscActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PlayerModel playerModel;
    private PairModel pairModel;
    private MatchModel matchModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statictisc);

        Toolbar toolbar = findViewById(R.id.appBar);
        setSupportActionBar(toolbar);

        getIntent();

        recyclerView = findViewById(R.id.statisticsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        playerModel = ViewModelProviders.of(this).get(PlayerModel.class);
        pairModel = ViewModelProviders.of(this).get(PairModel.class);
        matchModel = ViewModelProviders.of(this).get(MatchModel.class);

        final PairsAdapter adapter = new PairsAdapter(this, playerModel);
        recyclerView.setAdapter(adapter);

        pairModel.getAllPairs().observe(this, new Observer<List<PairEntity>>() {
            @Override
            public void onChanged(@Nullable List<PairEntity> pairEntities) {
                adapter.setPairs(pairEntities);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionReset:
                matchModel.deleteAllMatches();
                pairModel.deleteAllPaires();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void resetButtonClicked(View view) {
        matchModel.deleteAllMatches();
        pairModel.deleteAllPaires();
    }

    public void closeBUttonClicked(View view) {
        setResult(RESULT_OK);
        finish();
    }
}
