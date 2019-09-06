package com.example.swimtracker.coach.diary_manage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.swimtracker.R;
import com.example.swimtracker.coach.MainActivity;
import com.example.swimtracker.coach.workout_manage.CreateRecord;
import com.example.swimtracker.user_manage.Swimmer;
import com.example.swimtracker.user_manage.URLManage;
import com.example.swimtracker.user_manage.UserProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class AddNoteActivity extends AppCompatActivity {

    RecyclerView noteRecyclerView;
    NoteRecyclerViewAdapter adapter;
    TextView txtWorkoutName, txtDate, txtTeamName;
    Button btnFinish;
    CreateRecord createRecord = CreateRecord.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        init();
        loadData();
        action();
    }

    public void init(){
        AndroidNetworking.initialize(getApplicationContext());
        txtWorkoutName = findViewById(R.id.txt_workout_name);
        txtDate = findViewById(R.id.txt_date);
        txtTeamName = findViewById(R.id.txt_team_name);

        noteRecyclerView = findViewById(R.id.recycler_view_note);
        adapter = new NoteRecyclerViewAdapter();
        noteRecyclerView.setAdapter(adapter);
        noteRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnFinish = findViewById(R.id.btn_finish);


    }

    public void loadData(){
        txtWorkoutName.setText(createRecord.getWorkoutName());
        txtTeamName.setText(createRecord.getTeam().getTeamName());
        txtDate.setText(createRecord.getDate());

        loadDataSwimmer();
    }

    public void action(){
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote();
            }
        });
    }

    public void addNote(){
        JSONObject jsonObject = addToJSONObject();
        AndroidNetworking.post(URLManage.getInstance().getMainURL() + "/diary/" + UserProfile.getUser().getUsername() + "/add")
                .addJSONObjectBody(jsonObject)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success"))
                                startActivity(new Intent(AddNoteActivity.this, MainActivity.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });


    }

    public void loadDataSwimmer(){
        Log.d("TeamID",createRecord.getTeam().getTeamID()+"");
        AndroidNetworking.initialize(getApplication());
        AndroidNetworking.get(URLManage.getInstance().getMainURL()+"/team/" + UserProfile.getInstance().getUser().getUsername()+"/" +createRecord.getTeam().getTeamID() +"/view")
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
                                Note note = new Note();
                                note.setSwimmer(swimmer);
                                ListNote.getInstance().getListNote().add(note);
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

    public JSONObject addToJSONObject(){
        JSONObject jsonObject = new JSONObject();
        Calendar c = Calendar.getInstance();
        int myear = c.get(Calendar.YEAR);
        int mmonth = c.get(Calendar.MONTH);
        int mday = c.get(Calendar.DAY_OF_MONTH);
        String date = myear + "/" + (mmonth+1) + "/" + mday;
        try {
            jsonObject.put("lesson_id",createRecord.getLessonID());
            jsonObject.put("team_id",createRecord.getTeam().getTeamID());
            jsonObject.put("date", date);
            jsonObject.put("swimmer", ListNote.getInstance().addToJSONArray());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    public int idRank(String rank){
        if (rank.equals("Giỏi"))
            return 1;
        if (rank.equals("Khá"))
            return 2;
        return 3;
    }
}
