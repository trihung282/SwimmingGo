package com.example.swimtracker.coach.workout_manage;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.swimtracker.R;
import com.example.swimtracker.coach.MainActivity;
import com.example.swimtracker.coach.team_manage.ListTeam;
import com.example.swimtracker.coach.team_manage.Team;
import com.example.swimtracker.user_manage.URLManage;
import com.example.swimtracker.user_manage.UserProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ViewWorkoutActivity extends AppCompatActivity {
    LinearLayout startLayout, mainLayout, subLayout, relaxLayout;
    TextView txtRepetitionStart, txtDistanceStart, txtStyleStart, txtRepetitionMain, txtDistanceMain, txtStyleMain, txtRepetitionSub, txtDistanceSub, txtStyleSub, txtRepetitionRelax, txtDistanceRelax, txtStyleRelax;
    TextView txtDate;
    Workout workout = new Workout();
    Exercise startExercise, mainExercise, subExercise, relaxExercise;
    TextView txtWorkoutName, txtTeamName;
    Button btnCancel, btnCreateRecord;
    DatePickerDialog datePickerDialog;
    String lessonID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_workout);
        init();
        loadData();
        action();
    }

    public void init() {

        startExercise = new Exercise();
        mainExercise = new Exercise();
        subExercise = new Exercise();
        relaxExercise = new Exercise();

        startExercise.setType("Khởi động");
        mainExercise.setType("Bài tập chính");
        relaxExercise.setType("Thả lỏng");
        subExercise.setType("Bài tập phụ");

        startLayout = findViewById(R.id.layout_start);
        mainLayout = findViewById(R.id.layout_main);
        subLayout = findViewById(R.id.layout_sub);
        relaxLayout = findViewById(R.id.layout_relax);


        txtRepetitionStart = findViewById(R.id.txt_repetition_start);
        txtDistanceStart = findViewById(R.id.txt_distance_start);
        txtStyleStart = findViewById(R.id.txt_style_start);

        txtRepetitionMain = findViewById(R.id.txt_repetition_main);
        txtDistanceMain = findViewById(R.id.txt_distance_main);
        txtStyleMain = findViewById(R.id.txt_style_main);

        txtRepetitionSub = findViewById(R.id.txt_repetition_sub);
        txtDistanceSub = findViewById(R.id.txt_distance_sub);
        txtStyleSub = findViewById(R.id.txt_style_sub);

        txtRepetitionRelax = findViewById(R.id.txt_repetition_relax);
        txtDistanceRelax = findViewById(R.id.txt_distance_relax);
        txtStyleRelax = findViewById(R.id.txt_style_relax);

        txtWorkoutName = findViewById(R.id.txt_workout_name);

        txtTeamName = findViewById(R.id.txt_team_name);

        txtDate = findViewById(R.id.txt_date);

        btnCancel = findViewById(R.id.btn_cancel);
        btnCreateRecord = findViewById(R.id.btn_create_record);


    }

    public void loadData() {
        AndroidNetworking.initialize(getApplicationContext());
        Intent intent = getIntent();
        final String workoutName = intent.getStringExtra("workout_name");
        String teamName = intent.getStringExtra("team_name");
        String date = intent.getStringExtra("date");

        txtTeamName.setText(teamName);
        txtDate.setText("Ngày : " + date);
        txtWorkoutName.setText(workoutName);

        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("lesson_name", workoutName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String urlEditWorkout = URLManage.getInstance().getMainURL() + "/workout/" + UserProfile.getUser().getUsername() + "/exercise/view";
        AndroidNetworking.post(urlEditWorkout)
                .addJSONObjectBody(jsonObject)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("exercise");

                            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                            JSONObject jsonObject2 = jsonArray.getJSONObject(1);
                            JSONObject jsonObject3 = jsonArray.getJSONObject(2);
                            JSONObject jsonObject4 = jsonArray.getJSONObject(3);

                            getDialogData("Khởi động", jsonObject1.getInt("repetition"), jsonObject1.getInt("swim_distance"), jsonObject1.getString("swim_name"), jsonObject1.getString("description"));
                            getDialogData("Bài tập chính", jsonObject2.getInt("repetition"), jsonObject2.getInt("swim_distance"), jsonObject2.getString("swim_name"), jsonObject2.getString("description"));
                            getDialogData("Bài tập phụ", jsonObject3.getInt("repetition"), jsonObject3.getInt("swim_distance"), jsonObject3.getString("swim_name"), jsonObject3.getString("description"));
                            getDialogData("Thả lỏng", jsonObject4.getInt("repetition"), jsonObject4.getInt("swim_distance"), jsonObject4.getString("swim_name"), jsonObject4.getString("description"));

                            startExercise.setId(jsonObject1.getInt("exercise_id"));
                            mainExercise.setId(jsonObject2.getInt("exercise_id"));
                            subExercise.setId(jsonObject3.getInt("exercise_id"));
                            relaxExercise.setId(jsonObject4.getInt("exercise_id"));

                            Log.d("Khoidong", startExercise.getId()+"");
                            Log.d("chinh", mainExercise.getId()+"");
                            Log.d("sub", subExercise.getId()+"");
                            Log.d("relax", relaxExercise.getId()+"");

                            lessonID = response.getString("lesson_id");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    public void action() {

        btnCreateRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewWorkoutActivity.this, CreateRecordActivity.class);
                intent.putExtra("workoutName", txtWorkoutName.getText().toString());
                intent.putExtra("date",txtDate.getText().toString());
                intent.putExtra("teamName", txtTeamName.getText().toString());
                CreateRecord.newInstance();
                List<Team> list = ListTeam.getInstance().getListTeam();
                for(int i = 0 ; i < list.size(); i++)
                    if (list.get(i).getTeamName().equals(txtTeamName.getText().toString())){
                        CreateRecord.getInstance().setTeam(list.get(i));
                        break;
                    }

                CreateRecord.getInstance().setMainExercise(mainExercise);
                CreateRecord.getInstance().setRelaxExercise(relaxExercise);
                CreateRecord.getInstance().setStartExercise(startExercise);
                CreateRecord.getInstance().setSubExercise(subExercise);
                CreateRecord.getInstance().setWorkoutName(txtWorkoutName.getText().toString());
                CreateRecord.getInstance().setDate(txtDate.getText().toString());
                CreateRecord.getInstance().setLessonID(lessonID);

                startActivity(intent);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewWorkoutActivity.this, MainActivity.class));
            }
        });

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void getDialogData(String title, Integer reps, Integer distance, String style, String description) {
        if (title.equals("Khởi động")) {
            startExercise.setId(1);
            startExercise.setDistance(distance);
            startExercise.setStyle(style);
            startExercise.setDescription(description);
            startExercise.setRepetition(reps);

            txtRepetitionStart.setText("Số lần tập : " + reps);
            txtDistanceStart.setText("Khoảng cách : " + distance + "");
            txtStyleStart.setText("Kiểu bơi : " + style);

        } else if (title.equals("Bài tập chính")) {
            mainExercise.setId(2);
            mainExercise.setDistance(distance);
            mainExercise.setStyle(style);
            mainExercise.setDescription(description);
            mainExercise.setRepetition(reps);

            txtRepetitionMain.setText("Số lần tập : " + reps);
            txtDistanceMain.setText("Khoảng cách : " + distance + "");
            txtStyleMain.setText("Kiểu bơi : " + style);
        } else if (title.equals("Bài tập phụ")) {
            subExercise.setId(3);
            subExercise.setDistance(distance);
            subExercise.setStyle(style);
            subExercise.setDescription(description);
            subExercise.setRepetition(reps);

            txtRepetitionSub.setText("Số lần tập : " + reps);
            txtDistanceSub.setText("Khoảng cách : " + distance + "");
            txtStyleSub.setText("Kiểu bơi : " + style);
        } else {
            relaxExercise.setId(4);
            relaxExercise.setDistance(distance);
            relaxExercise.setStyle(style);
            relaxExercise.setDescription(description);
            relaxExercise.setRepetition(reps);

            txtRepetitionRelax.setText("Số lần tập : " + reps);
            txtDistanceRelax.setText("Khoảng cách : " + distance + "");
            txtStyleRelax.setText("Kiểu bơi : " + style);
        }
    }
}
