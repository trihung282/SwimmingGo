package com.example.swimtracker.coach.setting_manage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.swimtracker.R;
import com.example.swimtracker.coach.team_manage.ListSwimmer;
import com.example.swimtracker.coach.team_manage.ListTeam;
import com.example.swimtracker.user_manage.Swimmer;
import com.example.swimtracker.user_manage.URLManage;
import com.example.swimtracker.user_manage.UserProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StatisActivity extends AppCompatActivity {

    Spinner spnTeamName;
    RecyclerView recyclerView;
    List<String> listSwimmer = new ArrayList<>();
    StatisRecyclerViewAdapter statisAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statis);

        init();
        loadData();
        action();

    }

    public void init(){
        spnTeamName = findViewById(R.id.spn_team_name);
        recyclerView = findViewById(R.id.recycler_view_swimmer);
        statisAdapter = new StatisRecyclerViewAdapter();
        recyclerView.setAdapter(statisAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    public void action(){
        spnTeamName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loadDataSwimmer(spnTeamName.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void loadData(){
        for(int i = 0; i < ListTeam.getInstance().getListTeam().size(); i++)
            listSwimmer.add(ListTeam.getInstance().getTeam(i).getTeamName());
        ArrayAdapter<String> swimmerAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, listSwimmer);
        spnTeamName.setAdapter(swimmerAdapter);
    }

    public int findTeamID(String teamName){
        for(int i = 0; i < ListTeam.getInstance().getListTeam().size(); i++)
            if (teamName.equals(ListTeam.getInstance().getTeam(i).getTeamName()))
                return ListTeam.getInstance().getTeam(i).getTeamID();
        return 0;
    }

    public void loadDataSwimmer(String teamName){
        AndroidNetworking.initialize(getApplication());
        ListSwimmer.newInstance().getListSwimmer().clear();
        int teamID = findTeamID(teamName);
        AndroidNetworking.get(URLManage.getInstance().getMainURL()+"/team/" + UserProfile.getInstance().getUser().getUsername()+"/" + teamID +"/view")
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
                                statisAdapter.notifyDataSetChanged();
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
