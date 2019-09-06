package com.example.swimtracker.coach.workout_manage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.swimtracker.R;
import com.example.swimtracker.coach.diary_manage.AddNoteActivity;
import com.example.swimtracker.coach.team_manage.Team;
import com.example.swimtracker.user_manage.Swimmer;
import com.example.swimtracker.user_manage.URLManage;
import com.example.swimtracker.user_manage.UserProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CreateRecordActivity extends AppCompatActivity {
    Team team;
    Spinner spnListWorkout;
    RecyclerView recyclerViewRecord;
    List<Record> listRecord = ListRecord.getInstance().getListRecord();
    List<List<Record>> listStart = new ArrayList<>();
    List<List<Record>> listMain = new ArrayList<>();
    List<List<Record>> listSub = new ArrayList<>();
    List<List<Record>> listRelax = new ArrayList<>();
    RecordRecyclerViewAdapter adapter;
    TextView txtTeamName, txtWorkoutName, txtDate, txtType, txtStyle, txtRepetition, txtDistance, txtRepetition1;
    String workoutName, teamName, date;
    Button btnNext, btnPrevious, btnAddRecord;
    CreateRecord createRecord = CreateRecord.getInstance();
    Exercise startExercise, subExercise, mainExercise, relaxExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_record);

        init();
        loadData();
        action();

    }

    public void init(){
        txtWorkoutName = findViewById(R.id.txt_workout_name);
        txtDate = findViewById(R.id.txt_date);
        txtTeamName = findViewById(R.id.txt_team_name);
        txtDistance = findViewById(R.id.txt_distance);
        txtType = findViewById(R.id.txt_type);
        txtStyle = findViewById(R.id.txt_style);
        txtRepetition = findViewById(R.id.txt_repetition);
        txtRepetition1 = findViewById(R.id.txt_repetition1);

        btnAddRecord = findViewById(R.id.btn_add_record);
        btnNext = findViewById(R.id.btn_next);
        btnPrevious = findViewById(R.id.btn_previous);

        recyclerViewRecord = findViewById(R.id.recycler_view_swimmer);
        adapter  = new RecordRecyclerViewAdapter(listRecord);
        recyclerViewRecord.setAdapter(adapter);
        recyclerViewRecord.setLayoutManager(new LinearLayoutManager(this));
    }

    public void loadData(){
        Intent intent = getIntent();
        workoutName = intent.getStringExtra("workoutName");
        teamName = intent.getStringExtra("teamName");
        date = intent.getStringExtra("date");

        startExercise = createRecord.getStartExercise();
        mainExercise = createRecord.getMainExercise();
        subExercise = createRecord.getSubExercise();
        relaxExercise = createRecord.getRelaxExercise();

        txtTeamName.setText("Tên nhóm : " + teamName);
        txtWorkoutName.setText(workoutName);
        txtDate.setText(date);
        loadLayout(startExercise);

        loadDataSwimmer();
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
                                Record r = new Record();
                                r.setSwimmer(swimmer);
                                listRecord.add(r);
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

    public void action(){
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeExercise(txtType.getText().toString());
            }
        });

        btnAddRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRecord();
                startActivity(new Intent(CreateRecordActivity.this, AddNoteActivity.class));
            }
        });
    }

    public void changeExercise(String type){
        if (type.equals(startExercise.getType())){
            if (Integer.parseInt(txtRepetition.getText().toString()) == startExercise.getRepetition()) {
                Log.d("type", "0");

                loadLayout(mainExercise);
            }
            else{
                Log.d("type", "1");
                txtRepetition.setText(Integer.parseInt(txtRepetition.getText().toString())+1 +"");
            }
            Log.d("listrecord", listRecord.get(1).getMinute()+"");
            listStart.add(listRecord);
            loadList();
        }
        else if (type.equals(mainExercise.getType())){
            if (Integer.parseInt(txtRepetition.getText().toString()) == mainExercise.getRepetition()) {
                Log.d("type", "0");
                loadLayout(subExercise);
            }
            else{
                Log.d("type", "1");
                txtRepetition.setText(Integer.parseInt(txtRepetition.getText().toString())+1 +"");
            }
            listMain.add(listRecord);
            loadList();
        }
        else if (type.equals(subExercise.getType())){
            if (Integer.parseInt(txtRepetition.getText().toString()) == subExercise.getRepetition()) {
                Log.d("type", "4");

                loadLayout(relaxExercise);
            }
            else{
                Log.d("type", "1");
                txtRepetition.setText(Integer.parseInt(txtRepetition.getText().toString())+1 +"");
            }
            listSub.add(listRecord);
            loadList();
        }else{
            if (Integer.parseInt(txtRepetition.getText().toString()) == relaxExercise.getRepetition()) {
                btnAddRecord.setVisibility(View.VISIBLE);
            }
            else{
                Log.d("type", "1");
                txtRepetition.setText(Integer.parseInt(txtRepetition.getText().toString())+1 +"");
            }
            listRelax.add(listRecord);
            loadList();
        }
    }

    public void loadLayout(Exercise exercise){
        txtRepetition1.setText("Số lần : " + exercise.getRepetition());
        txtDistance.setText("Khoảng cách : " + exercise.getDistance());
        txtType.setText(exercise.getType());
        txtStyle.setText("Kiểu bơi : " + exercise.getStyle());
        txtRepetition.setText("1");


    }

    public void loadList(){
        for(int i = 0; i < listRecord.size(); i++){
            listRecord.get(i).setMillisec(0);
            listRecord.get(i).setMinute(0);
            listRecord.get(i).setSec(0);
            adapter.notifyDataSetChanged();
        }
    }

    public void addRecord(){
        AndroidNetworking.initialize(getApplicationContext());
        addExerciseRecord(listStart, startExercise);
        addExerciseRecord(listMain, mainExercise);
        addExerciseRecord(listSub, subExercise);
        addExerciseRecord(listRelax, relaxExercise);

    }

    public void addExerciseRecord(final List<List<Record>> listExercise,final Exercise exercise){
        for(int i = 0; i < listExercise.size(); i++){
            List<Record> list = listExercise.get(i);
            JSONArray jsonArray = addToJSONArray(list);
            JSONObject jsonObject = new JSONObject();
            try{
                jsonObject.put("lesson_id",createRecord.getLessonID());
                jsonObject.put("repetition",exercise.getRepetition());
                jsonObject.put("exercise_id", exercise.getId());
                Log.d("exercise_id", exercise.getId()+"");
                jsonObject.put("record", jsonArray);
            }catch (Exception e){
                e.printStackTrace();
            }
            AndroidNetworking.post(URLManage.getInstance().getMainURL() + "/record/" + UserProfile.getUser().getUsername() + "/add")
                    .addJSONObjectBody(jsonObject)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.d("success", response.getBoolean("success")+"");
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

    public JSONArray addToJSONArray(List<Record> list){
        JSONArray jsonArray = new JSONArray();
        for(int i = 0; i < list.size(); i++)
            jsonArray.put(list.get(i).addToJSONObject());
        return jsonArray;
    }
}
