package com.example.swimtracker.coach.team_manage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.swimtracker.R;

public class AddSwimmerActivity extends AppCompatActivity {
    TextView txtTeamName, txtTeamAge;
    Button btnEditTeamName;
    RadioGroup rdgAddSwimmer;
    RadioButton rdbAddNewSwimmer, rdbAddAvaiableSwimmer;
    Team team;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_swimmer);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        team = new Team(intent.getStringExtra("teamName"), intent.getStringExtra("teamAge"), intent.getIntExtra("teamID", 10));

        txtTeamName = findViewById(R.id.txt_team_name);
        txtTeamAge = findViewById(R.id.txt_team_age);
        btnEditTeamName = findViewById(R.id.btn_edit_team_name);

        rdgAddSwimmer = findViewById(R.id.rdg_add_swimmer);
        rdbAddAvaiableSwimmer = findViewById(R.id.rdb_add_available_swimmer);
        rdbAddNewSwimmer = findViewById(R.id.rdb_add_new_swimmer);

        txtTeamAge.setText("Độ tuổi : " + team.getTeamAge());
        txtTeamName.setText(team.getTeamName());
        rdbAddNewSwimmer.setChecked(true);
        loadFragment(new AddNewSwimmerFragment());
        rdgAddSwimmer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rdb_add_available_swimmer:
                        loadFragment(new AddAvailableSwimmerFragment());
                        return;
                    case R.id.rdb_add_new_swimmer:
                        loadFragment(new AddNewSwimmerFragment());
                        return;
                }
            }
        });

    }
    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_add_swimmer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
