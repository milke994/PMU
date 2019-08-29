package com.example.dusanmilic.projekat2019.activities;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.dusanmilic.projekat2019.model.Model;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EndGameActivity extends AppCompatActivity {

    private PlayerModel playerModel;
    private PairModel pairModel;
    private MatchModel matchModel;
    private Model model;
    private TextView player1NameTextView, player2NameTextView;
    private TextView player1WinsTextView, player2WinsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        Intent intent = getIntent();
        model = (Model) intent.getSerializableExtra("options");
        player1NameTextView = findViewById(R.id.textViewPlayer1NameEndGame);
        player2NameTextView = findViewById(R.id.textViewPlayer2NameEndGame);
        player1WinsTextView = findViewById(R.id.player1WinsEndGame);
        player2WinsTextView = findViewById(R.id.player2WinsEndGame);
        player1NameTextView.setText(model.getPlayer1Name());
        player2NameTextView.setText(model.getPlayer2Name());

        RecyclerView recyclerView = findViewById(R.id.recyclerViewIdEndGame);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final MatchesAdapter adapter = new MatchesAdapter();
        recyclerView.setAdapter(adapter);

        playerModel = ViewModelProviders.of(this).get(PlayerModel.class);
        pairModel = ViewModelProviders.of(this).get(PairModel.class);
        matchModel = ViewModelProviders.of(this).get(MatchModel.class);

        PlayerEntity player1 = playerModel.find(model.getPlayer1Name());
        if(player1 == null) {
            player1 = new PlayerEntity(model.getPlayer1Name());
            playerModel.insert(player1);
            player1 = playerModel.find(model.getPlayer1Name());
        }
        PlayerEntity player2 = playerModel.find(model.getPlayer2Name());
        if(player2 == null){
            player2 = new PlayerEntity(model.getPlayer2Name());
            playerModel.insert(player2);
            player2 = playerModel.find(model.getPlayer2Name());
        }
        PairEntity pair = pairModel.findPair(player1.getId(), player2.getId());
        if(pair == null){
            pair = new PairEntity(player1.getId(), player2.getId());
            pairModel.insert(pair);
            pair = pairModel.findPair(player1.getId(), player2.getId());
        }
        if(model.getPlayer1Score() > model.getPlayer2Score()){
            pair.setPlayer1Wins(pair.getPlayer1Wins() + 1);
        }else{
            if(model.getPlayer2Score() > model.getPlayer1Score()){
                pair.setPlayer2Wins(pair.getPlayer2Wins() + 1);
            }
        }
        pairModel.update(pair);
        player1WinsTextView.setText(String.valueOf(pair.getPlayer1Wins()));
        player2WinsTextView.setText(String.valueOf(pair.getPlayer2Wins()));
        MatchEntity matchEntity = new MatchEntity(player1.getId(), player2.getId());
        matchEntity.setPlayer1Score(model.getPlayer1Score());
        matchEntity.setPlayer2Score(model.getPlayer2Score());
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        matchEntity.setTime(dateFormat.format(new Date()));
        matchModel.insert(matchEntity);
        matchModel.getMatches(player1.getId(), player2.getId()).observe(this, new Observer<List<MatchEntity>>() {
            @Override
            public void onChanged(@Nullable List<MatchEntity> matchEntities) {
                adapter.set(matchEntities);
            }
        });
    }

    public void closeButtonClicked(View view) {
        Intent intent = new Intent();
        intent.putExtra("options", model);
        setResult(RESULT_OK, intent);
        finish();
    }
}
