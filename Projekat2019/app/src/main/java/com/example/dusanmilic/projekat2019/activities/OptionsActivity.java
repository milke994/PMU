package com.example.dusanmilic.projekat2019.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.dusanmilic.projekat2019.R;
import com.example.dusanmilic.projekat2019.fragments.CourtTypeGrassFragment;
import com.example.dusanmilic.projekat2019.fragments.CourtTypeHardFragment;
import com.example.dusanmilic.projekat2019.fragments.CourtTypeSoftFragment;
import com.example.dusanmilic.projekat2019.model.Model;


public class OptionsActivity extends AppCompatActivity {

    private Spinner dificultySpinner, speedSpinner;

    private Model model;

    private Fragment fragment0, fragment1, fragment2;
    private String m_frag0Tag = "HomeActivity.Fragment0";
    private String m_frag1Tag = "HomeActivity.Fragment1";
    private String m_frag2Tag = "HomeActivity.Fragment2";
    private int m_active;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);


        Intent intent = getIntent();
        model = (Model) intent.getSerializableExtra("options");



        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment0 = new CourtTypeGrassFragment();
        fragment1 = new CourtTypeHardFragment();
        fragment2 = new CourtTypeSoftFragment();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (model.getCourtType()){
            case GRASS:
                m_active = 0;
                fragmentTransaction.add(R.id.courtTypeFrameLayoutId, fragment0, m_frag0Tag);
                break;
            case HARD:
                m_active = 1;
                fragmentTransaction.add(R.id.courtTypeFrameLayoutId, fragment1, m_frag1Tag);
                break;
            case SOFT:
                m_active = 2;
                fragmentTransaction.add(R.id.courtTypeFrameLayoutId, fragment2, m_frag2Tag);
                break;
        }

        fragmentTransaction.commit();

        dificultySpinner = findViewById(R.id.dificultySpinnerId);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.dificulties,
                android.R.layout.simple_spinner_dropdown_item);
        dificultySpinner.setAdapter(adapter1);
        switch (model.getDificulty()){
            case BEGGINER:
                dificultySpinner.setSelection(0);
                break;
            case PRO:
                dificultySpinner.setSelection(1);
                break;
            case SUPERSTAR:
                dificultySpinner.setSelection(2);
                break;
        }
        dificultySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                View view2 = getWindow().getDecorView();
                int options = View.SYSTEM_UI_FLAG_FULLSCREEN;
                view2.setSystemUiVisibility(options);
                switch (position){
                    case 0:
                        model.setDificulty(Model.Dificulty.BEGGINER);
                        break;
                    case 1:
                        model.setDificulty(Model.Dificulty.PRO);
                        break;
                    case 2:
                        model.setDificulty(Model.Dificulty.SUPERSTAR);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                View view = getWindow().getDecorView();
                int options = View.SYSTEM_UI_FLAG_FULLSCREEN;
                view.setSystemUiVisibility(options);
                model.setDificulty(Model.Dificulty.BEGGINER);
            }
        });

        speedSpinner = findViewById(R.id.speedSpinnerId);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.speeds,
                android.R.layout.simple_spinner_dropdown_item);
        speedSpinner.setAdapter(adapter2);
        switch (model.getSpeed()){
            case SLOW:
                speedSpinner.setSelection(0);
                break;
            case MEDIUM:
                speedSpinner.setSelection(1);
                break;
            case FAST:
                speedSpinner.setSelection(2);
                break;
        }
        speedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                View view1 = getWindow().getDecorView();
                int options = View.SYSTEM_UI_FLAG_FULLSCREEN;
                view1.setSystemUiVisibility(options);
                switch (position){
                    case 0:
                        model.setSpeed(Model.Speed.SLOW);
                        break;
                    case 1:
                        model.setSpeed(Model.Speed.MEDIUM);
                        break;
                    case 2:
                        model.setSpeed(Model.Speed.FAST);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                View view = getWindow().getDecorView();
                int options = View.SYSTEM_UI_FLAG_FULLSCREEN;
                view.setSystemUiVisibility(options);
                model.setSpeed(Model.Speed.SLOW);
            }
        });
    }

    public void saveOptions(View view) {
        Intent result = new Intent();
        switch (m_active){
            case 0:
                model.setCourtType(Model.CourtType.GRASS);
                break;
            case 1:
                model.setCourtType(Model.CourtType.HARD);
                break;
            case 2:
                model.setCourtType(Model.CourtType.SOFT);
                break;
        }
        result.putExtra("options", model);
        setResult(RESULT_OK, result);
        finish();
    }

    public void dontSaveOptions(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void nextClicked(View view) {
        if(m_active < 2){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if(m_active == 0){
                m_active = 1;
                transaction.remove(fragment0);
                transaction.add(R.id.courtTypeFrameLayoutId, fragment1,m_frag1Tag);
            } else {
                m_active = 2;
                transaction.remove(fragment1);
                transaction.add(R.id.courtTypeFrameLayoutId,fragment2,m_frag2Tag);
            }
            transaction.commit();
        }
    }

    public void previousClicked(View view) {
        if(m_active > 0){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if(m_active == 2){
                m_active = 1;
                transaction.remove(fragment2);
                transaction.add(R.id.courtTypeFrameLayoutId,fragment1,m_frag1Tag);
            } else {
                m_active = 0;
                transaction.remove(fragment1);
                transaction.add(R.id.courtTypeFrameLayoutId,fragment0,m_frag0Tag);
            }
            transaction.commit();
        }
    }

    public void resetOptions(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (m_active){
            case 0:
                break;
            case 1:
                transaction.remove(fragment1);
                break;
            case 2:
                transaction.remove(fragment2);
                break;
        }
        if(m_active != 0) {
            m_active = 0;
            transaction.add(R.id.courtTypeFrameLayoutId, fragment0, m_frag0Tag);
            transaction.commit();
        }

        dificultySpinner.setSelection(0);
        model.setDificulty(Model.Dificulty.BEGGINER);

        speedSpinner.setSelection(0);
        model.setSpeed(Model.Speed.SLOW);
    }
}
