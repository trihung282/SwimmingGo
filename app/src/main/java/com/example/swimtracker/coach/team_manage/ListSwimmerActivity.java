package com.example.swimtracker.coach.team_manage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.swimtracker.R;
import com.example.swimtracker.coach.workout_manage.CreateRecordActivity;
import com.example.swimtracker.user_manage.Swimmer;
import com.example.swimtracker.user_manage.URLManage;
import com.example.swimtracker.user_manage.UserProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ListSwimmerActivity extends AppCompatActivity {
    TextView txtTeamName, txtTeamAge;
    Button btnEditTeamName;
    Button btnAddSwimmer, btnCreateRecord;
    Team team;
    RecyclerView mRecyclerView;
    SwimmerRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_swimmer);
        initView();
        action();
    }

    public void initView(){
        Intent intent = getIntent();
        team = new Team(intent.getStringExtra("teamName"), intent.getStringExtra("teamAge"), intent.getIntExtra("teamID", 10));

        loadData();

        txtTeamName = findViewById(R.id.txt_team_name);
        txtTeamAge = findViewById(R.id.txt_team_age);
        btnEditTeamName = findViewById(R.id.btn_edit_team_name);
        btnCreateRecord = findViewById(R.id.btn_create_record);

        txtTeamAge.setText("Độ tuổi : " + team.getTeamAge());
        txtTeamName.setText(team.getTeamName());


        mRecyclerView = findViewById(R.id.recycler_view_swimmer);
        adapter = new SwimmerRecyclerViewAdapter();
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnAddSwimmer = findViewById(R.id.btn_add_swimmer);


    }

    public void action() {
        btnAddSwimmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListSwimmerActivity.this, AddSwimmerActivity.class);
                intent.putExtra("teamName", team.getTeamName());
                intent.putExtra("teamAge", team.getTeamAge());
                intent.putExtra("teamID",team.getTeamID());
                startActivity(intent);
            }
        });

        btnCreateRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListSwimmerActivity.this, CreateRecordActivity.class);
                intent.putExtra("teamName", team.getTeamName());
                intent.putExtra("teamAge", team.getTeamAge());
                intent.putExtra("teamID",team.getTeamID());
                startActivity(intent);
            }
        });
    }


    public void loadData(){
        AndroidNetworking.initialize(getApplication());
        ListSwimmer.newInstance();
        AndroidNetworking.get(URLManage.getInstance().getMainURL()+"/team/" + UserProfile.getInstance().getUser().getUsername()+"/" +team.getTeamID() +"/view")
                .addHeaders("Authorization", "Bearer " + UserProfile.getAccessToken())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray result = response.getJSONObject("values").getJSONArray("team");
                            for(int i = 0; i < result.length(); i++){
                                JSONObject jsonObject = result.getJSONObject(i);
                                Swimmer swimmer = new Swimmer();
                                swimmer.getSwimmerFromJSONObject(jsonObject);
                                ListSwimmer.getInstance().addSwimmer(swimmer);
                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
}
