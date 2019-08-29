package com.example.dusanmilic.projekat2019.activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dusanmilic.projekat2019.R;
import com.example.dusanmilic.projekat2019.adapters.SwipeAdapter;
import com.example.dusanmilic.projekat2019.model.Model;

public class TeamSelectActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_FOR_PLAYING = 1;

    private ViewPager team1ViewPager, team2ViewPager;
    private TextView player1TextView, player2TextView;
    private SwipeAdapter adapter1, adapter2;
    private Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_select);

        team1ViewPager = findViewById(R.id.viewPagerTeam1);
        team2ViewPager = findViewById(R.id.viewPagerTeam2);
        player1TextView = findViewById(R.id.teamSelectPlayer1Name);
        player2TextView = findViewById(R.id.teamSelectPlayer2Name);

        adapter1 = new SwipeAdapter(this);
        adapter2 = new SwipeAdapter(this);

        team1ViewPager.setAdapter(adapter1);
        team2ViewPager.setAdapter(adapter2);

        Intent intent = getIntent();
        model = (Model) intent.getSerializableExtra("options");
        player1TextView.setText(model.getPlayer1Name());
        player2TextView.setText(model.getPlayer2Name());
    }

    public void playButtonClicked(View view) {
        if(adapter1.getTeamSelected(team1ViewPager.getCurrentItem()) == adapter2.getTeamSelected(team2ViewPager.getCurrentItem())){
            Toast.makeText(this, getString(R.string.teamSelectError),Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(this, GameActivity.class);
            model.setTeam1(adapter1.getTeamSelected(team1ViewPager.getCurrentItem()));
            model.setTeam2(adapter2.getTeamSelected(team2ViewPager.getCurrentItem()));
            intent.putExtra("options", model);
            startActivityForResult(intent, REQUEST_CODE_FOR_PLAYING);
        }
    }

    public void cancelButtonClicked(View view) {
        Intent intent = new Intent();
        intent.putExtra("options", model);
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_FOR_PLAYING && RESULT_OK == resultCode){
            setResult(RESULT_OK, data);
            finish();
        }
        if(requestCode == REQUEST_CODE_FOR_PLAYING && RESULT_CANCELED == resultCode){
            setResult(RESULT_CANCELED, data);
            finish();
        }
    }
}
