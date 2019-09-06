package com.example.swimtracker.coach.workout_manage;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.swimtracker.R;
import com.example.swimtracker.user_manage.URLManage;
import com.example.swimtracker.user_manage.UserProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class WorkoutFragment extends Fragment {
    Button btnAddWorkout, btnViewAll;
    View view;
    TextView txtDate;
    RecyclerView recyclerView;
    List<Workout> listWorkout = new ArrayList<>();
    WorkoutRecyclerViewAdapter adapter;
    DatePickerDialog datePickerDialog;
    public WorkoutFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_workout, container, false);
        init();

        return view;
    }

    public void init(){
        AndroidNetworking.initialize(getContext());

        txtDate = view.findViewById(R.id.txt_date);
        btnAddWorkout = view.findViewById(R.id.btn_add_workout);
        btnViewAll = view.findViewById(R.id.btn_view_all);

        Calendar c = Calendar.getInstance();
        int myear = c.get(Calendar.YEAR);
        int mmonth = c.get(Calendar.MONTH);
        int mday = c.get(Calendar.DAY_OF_MONTH);
        txtDate.setText(myear + "/" + (mmonth+1) + "/" +mday);
        loadDataWorkout();

        recyclerView = view.findViewById(R.id.recycler_view_workout);
        adapter = new WorkoutRecyclerViewAdapter(listWorkout);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        btnAddWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddWorkoutActivity.class));
            }
        });

        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDataWorkout();
            }
        });

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();

                int mYear = calendar.get(Calendar.YEAR); // current year
                int mMonth = calendar.get(Calendar.MONTH); // current month
                int mDay = calendar.get(Calendar.DAY_OF_MONTH); // current day

                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                txtDate.setText(year + "/"
                                        + (monthOfYear + 1) + "/" + dayOfMonth);
                                loadDataWorkoutWithDate();
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


    }

    public void loadDataWorkout(){
        listWorkout.clear();
        String urlWorkout = URLManage.getInstance().getMainURL() + "/workout/"+ UserProfile.getUser().getUsername() + "/lessonplan/view";
        AndroidNetworking.get(urlWorkout)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Run", "success");
                        try {
                            JSONArray jsonArray = response.getJSONArray("values");
                            Log.d("array", jsonArray.length()+"");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Workout workout = new Workout();
                                workout.getWorkoutFromJSONObject(jsonObject);
                                listWorkout.add(workout);
                                Log.d("workout", listWorkout.size() + "");
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

    public  void loadDataWorkoutWithDate(){
        final String username = UserProfile.getUser().getUsername();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("date",txtDate.getText().toString());
            Log.e("Date",jsonObject.getString("date"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        listWorkout.clear();
        adapter.notifyDataSetChanged();
        String urlWorkout = URLManage.getInstance().getMainURL() + "/workout/"+ username + "/lessonplan/view";
        AndroidNetworking.post(urlWorkout)
                .addJSONObjectBody(jsonObject)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Run", "success");
                        try {
                            JSONArray jsonArray = response.getJSONArray("values");
                            Log.d("array", jsonArray.length()+"");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                jsonObject.put("date",txtDate.getText().toString());
                                Workout workout = new Workout();
                                workout.getWorkoutFromJSONObject(jsonObject);
                                listWorkout.add(workout);
                                Log.d("workout", listWorkout.size() + "");
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
