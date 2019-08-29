package com.example.dusanmilic.projekat2019.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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
import android.widget.TextView;

import com.example.dusanmilic.projekat2019.R;
import com.example.dusanmilic.projekat2019.adapters.MatchesAdapter;
import com.example.dusanmilic.projekat2019.database.entities.MatchEntity;
import com.example.dusanmilic.projekat2019.database.entities.PairEntity;
import com.example.dusanmilic.projekat2019.database.entities.PlayerEntity;
import com.example.dusanmilic.projekat2019.database.models.MatchModel;
import com.example.dusanmilic.projekat2019.database.models.PairModel;
import com.example.dusanmilic.projekat2019.database.models.PlayerModel;

import java.util.List;

public class StatisticsDetailActivity extends AppCompatActivity {
    private MatchModel matchModel;
    private PlayerModel playerModel;
    private PairModel pairModel;
    private TextView player1Name, player2Name, player1Wins, player2Wins;
    private PlayerEntity player1, player2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_detail);

        Toolbar toolbar = findViewById(R.id.applicationBar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        player1Name = findViewById(R.id.textViewPlayer1NameStatisticsDetail);
        player2Name = findViewById(R.id.textViewPlayer2NameStatisticsDetail);
        player1Wins = findViewById(R.id.player1WinsStatisticsDetail);
        player2Wins = findViewById(R.id.player2WinsStatisticsDetail);
        player1Name.setText(intent.getStringExtra("player1Name"));
        player2Name.setText(intent.getStringExtra("player2Name"));
        player1Wins.setText(intent.getStringExtra("player1Wins"));
        player2Wins.setText(intent.getStringExtra("player2Wins"));

        playerModel = ViewModelProviders.of(this).get(PlayerModel.class);
        matchModel = ViewModelProviders.of(this).get(MatchModel.class);
        pairModel = ViewModelProviders.of(this).get(PairModel.class);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewIdStatisticsDetail);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final MatchesAdapter adapter = new MatchesAdapter();
        recyclerView.setAdapter(adapter);

        player1 = playerModel.find(player1Name.getText().toString());
        player2 = playerModel.find(player2Name.getText().toString());
        matchModel.getMatches(player1.getId(), player2.getId()).observe(this, new Observer<List<MatchEntity>>() {
            @Override
            public void onChanged(@Nullable List<MatchEntity> matchEntities) {
                adapter.set(matchEntities);
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
                matchModel.deleteMatches(player1.getId(), player2.getId());
                PairEntity pairEntity = pairModel.findPair(player1.getId(), player2.getId());
                pairModel.delete(pairEntity);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void resetButtonClicked(View view) {
        matchModel.deleteMatches(player1.getId(), player2.getId());
        PairEntity pairEntity = pairModel.findPair(player1.getId(), player2.getId());
        pairModel.delete(pairEntity);
        finish();
    }

    public void closeButtonClicked(View view) {
        finish();
    }
}
