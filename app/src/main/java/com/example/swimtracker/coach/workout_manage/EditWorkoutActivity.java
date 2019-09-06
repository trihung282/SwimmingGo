package com.example.swimtracker.coach.workout_manage;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.swimtracker.R;
import com.example.swimtracker.coach.MainActivity;
import com.example.swimtracker.coach.team_manage.ListTeam;
import com.example.swimtracker.user_manage.URLManage;
import com.example.swimtracker.user_manage.UserProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditWorkoutActivity extends AppCompatActivity {

    LinearLayout startLayout, mainLayout, subLayout, relaxLayout;
    TextView txtRepetitionStart, txtDistanceStart, txtStyleStart, txtRepetitionMain, txtDistanceMain, txtStyleMain, txtRepetitionSub, txtDistanceSub, txtStyleSub, txtRepetitionRelax, txtDistanceRelax, txtStyleRelax;
    TextView txtDate;
    Workout workout = new Workout();
    Exercise startExercise, mainExercise, subExercise, relaxExercise;
    EditText edtWorkoutName;
    Spinner spnTeamName;
    Button btnCancel, btnAddWorkout;
    DatePickerDialog datePickerDialog;
    String lessonID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout);
        init();
        loadData();
        action();
    }

    public void init() {

        startExercise = new Exercise();
        mainExercise = new Exercise();
        subExercise = new Exercise();
        relaxExercise = new Exercise();

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

        edtWorkoutName = findViewById(R.id.edt_workout_name);

        spnTeamName = findViewById(R.id.spn_team_name);

        txtDate = findViewById(R.id.txt_date);

        btnCancel = findViewById(R.id.btn_cancel);
        btnAddWorkout = findViewById(R.id.btn_add_workout);

        List<String> listTeamName = new ArrayList<>();
        for (int i = 0; i < ListTeam.getInstance().getListTeam().size(); i++)
            listTeamName.add(ListTeam.getInstance().getTeam(i).getTeamName());

        ArrayAdapter<String> adapterTeamName = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listTeamName);
        spnTeamName.setAdapter(adapterTeamName);

    }

    public void loadData() {
        AndroidNetworking.initialize(getApplicationContext());
        Intent intent = getIntent();
        final String workoutName = intent.getStringExtra("workout_name");
        String teamName = intent.getStringExtra("team_name");
        String date = intent.getStringExtra("date");

        spnTeamName.setPrompt(teamName);

        edtWorkoutName.setText(workoutName);
        txtDate.setText(date);

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
                            Log.d("Exercise_id", startExercise.getId() + "");
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
        startLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("Khởi động");
            }
        });

        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("Bài tập chính");
            }
        });

        subLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("Bài tập phụ");
            }
        });

        relaxLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("Thả lỏng");
            }
        });

        btnAddWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWorkout();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditWorkoutActivity.this, MainActivity.class));
            }
        });

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    public void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();

        int mYear = calendar.get(Calendar.YEAR); // current year
        int mMonth = calendar.get(Calendar.MONTH); // current month
        int mDay = calendar.get(Calendar.DAY_OF_MONTH); // current day

        datePickerDialog = new DatePickerDialog(EditWorkoutActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        txtDate.setText(year + "/"
                                + (monthOfYear + 1) + "/" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void editWorkout() {
        AndroidNetworking.initialize(getApplicationContext());
        JSONObject jsonObject = addWorkoutToJSONObject();


        AndroidNetworking.post(URLManage.getInstance().getMainURL() + "/workout/" + UserProfile.getUser().getUsername() + "/lesson/edit")
                .addJSONObjectBody(jsonObject)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success"))
                                startActivity(new Intent(EditWorkoutActivity.this, MainActivity.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    public JSONObject addWorkoutToJSONObject() {
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        String lessonName = edtWorkoutName.getText().toString();
        String date = txtDate.getText().toString();
        String teamName = spnTeamName.getSelectedItem().toString();
        try {
            new JSONObject();
            try {
                result.put("lesson_id", lessonID);
                result.put("lesson_name", lessonName);
                result.put("date", date);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(startExercise.ConvertToJSONObject());
            jsonArray.put(mainExercise.ConvertToJSONObject());
            jsonArray.put(subExercise.ConvertToJSONObject());
            jsonArray.put(relaxExercise.ConvertToJSONObject());
            result.put("exercise", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void showDialog(final String title) {
        final Dialog dlg = new Dialog(this);
        dlg.setContentView(R.layout.workout_dialog);

        List<String> listStyle = ListStyle.getInstance().getListStyle();
        final List<Integer> listDistance = ListDistance.getInstance().getListDistance();
        Log.d("ABC", listStyle.size() + "");

        final Spinner spnStyle = dlg.findViewById(R.id.spn_style);
        Button btnRepsMinus = dlg.findViewById(R.id.btn_reps_minus);
        Button btnRepsPlus = dlg.findViewById(R.id.btn_reps_plus);
        Button btnDistancePlus = dlg.findViewById(R.id.btn_distance_plus);
        Button btnDistanceMinus = dlg.findViewById(R.id.btn_distance_minus);
        Button btnAdd = dlg.findViewById(R.id.btn_add);
        Button btnCancel = dlg.findViewById(R.id.btn_cancel);

        TextView txtTitle = dlg.findViewById(R.id.txt_title);
        txtTitle.setText(title);
        final EditText edtReps = dlg.findViewById(R.id.edt_rep);
        final EditText edtDescription = dlg.findViewById(R.id.edt_description);

        final TextView txtDistance = dlg.findViewById(R.id.txt_distance);
        txtDistance.setText(listDistance.get(0) + "");


        ArrayAdapter<String> styleAdapter = new ArrayAdapter<String>(dlg.getContext(), android.R.layout.simple_spinner_dropdown_item, listStyle);
        edtReps.setText("1");
        btnRepsMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(edtReps.getText().toString()) > 0)
                    edtReps.setText("" + (Integer.parseInt(edtReps.getText().toString()) - 1));
                else {
                    edtReps.setText("" + 0);
                }
            }
        });

        btnRepsPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(edtReps.getText().toString()) < 50)
                    edtReps.setText("" + (Integer.parseInt(edtReps.getText().toString()) + 1));
                else {
                    edtReps.setText("50");
                }
            }
        });

        btnDistanceMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = listDistance.indexOf(Integer.parseInt(txtDistance.getText().toString()));
                Log.e("hungpro", "" + index);
                if (index > 0) {
                    txtDistance.setText("" + (listDistance.get(index - 1)));
                } else
                    txtDistance.setText("" + Integer.parseInt(txtDistance.getText().toString()));
            }
        });
        btnDistancePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = listDistance.indexOf(Integer.parseInt(txtDistance.getText().toString()));
                Log.e("hungpro", "" + index);
                if (index < listDistance.size() - 1) {
                    txtDistance.setText("" + (listDistance.get(index + 1)));
                }

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialogData(title, Integer.parseInt(edtReps.getText().toString()), Integer.parseInt(txtDistance.getText().toString()), spnStyle.getSelectedItem().toString(), edtDescription.getText().toString());
                dlg.dismiss();
            }
        });
        spnStyle.setAdapter(styleAdapter);
        dlg.show();

    }

    public void getDialogData(String title, Integer reps, Integer distance, String style, String description) {
        if (title.equals("Khởi động")) {
            startExercise.setDistance(distance);
            startExercise.setStyle(style);
            startExercise.setDescription(description);
            startExercise.setRepetition(reps);

            txtRepetitionStart.setText("Số lần tập : " + reps);
            txtDistanceStart.setText("Khoảng cách : " + distance + "");
            txtStyleStart.setText("Kiểu bơi : " + style);

        } else if (title.equals("Bài tập chính")) {
            mainExercise.setDistance(distance);
            mainExercise.setStyle(style);
            mainExercise.setDescription(description);
            mainExercise.setRepetition(reps);

            txtRepetitionMain.setText("Số lần tập : " + reps);
            txtDistanceMain.setText("Khoảng cách : " + distance + "");
            txtStyleMain.setText("Kiểu bơi : " + style);
        } else if (title.equals("Bài tập phụ")) {
            subExercise.setDistance(distance);
            subExercise.setStyle(style);
            subExercise.setDescription(description);
            subExercise.setRepetition(reps);

            txtRepetitionSub.setText("Số lần tập : " + reps);
            txtDistanceSub.setText("Khoảng cách : " + distance + "");
            txtStyleSub.setText("Kiểu bơi : " + style);
        } else {
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


