package com.example.swimtracker.coach.diary_manage;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.swimtracker.R;
import com.example.swimtracker.coach.workout_manage.AddWorkoutActivity;
import com.example.swimtracker.coach.workout_manage.ListWorkout;
import com.example.swimtracker.coach.workout_manage.Workout;
import com.example.swimtracker.user_manage.URLManage;
import com.example.swimtracker.user_manage.UserProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DiaryFragment extends Fragment {

    View view;
    List<Diary> listDiary = new ArrayList<>();
    TextView txtDate;
    Button btnViewAll;
    DiaryRecyclerViewAdapter diaryAdapter;
    RecyclerView recyclerView;
    DatePickerDialog datePickerDialog;
    String date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_diary, container, false);
        init();
        loadData();
        action();
        return view;
    }

    public void init() {

        txtDate = view.findViewById(R.id.txt_date);

        btnViewAll = view.findViewById(R.id.btn_view_all);

        recyclerView = view.findViewById(R.id.recycler_view_diary);
        diaryAdapter = new DiaryRecyclerViewAdapter(listDiary);
        recyclerView.setAdapter(diaryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void loadData() {
        AndroidNetworking.initialize(getContext());
        Calendar c = Calendar.getInstance();
        int myear = c.get(Calendar.YEAR);
        int mmonth = c.get(Calendar.MONTH);
        int mday = c.get(Calendar.DAY_OF_MONTH);
        date = myear + "/" + (mmonth + 1) + "/" + mday;
        txtDate.setText("Ngày tạo : " + date);

        loadDataDiary();
    }

    public void loadDataDiary() {
        listDiary.clear();
        AndroidNetworking.get(URLManage.getInstance().getMainURL() + "/diary/" + UserProfile.getUser().getUsername() + "/view")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("values");
                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Diary diary = new Diary();
                                diary.setDate(jsonObject.getString("diary_date"));
                                diary.setWorkoutName(jsonObject.getString("lesson_name"));
                                diary.setTeamName(jsonObject.getString("team_name"));
                                diary.setLessonID(jsonObject.getInt("lesson_id"));
                                listDiary.add(diary);
                                diaryAdapter.notifyDataSetChanged();
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

    public void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();

        int mYear = calendar.get(Calendar.YEAR); // current year
        int mMonth = calendar.get(Calendar.MONTH); // current month
        int mDay = calendar.get(Calendar.DAY_OF_MONTH); // current day


        datePickerDialog = new DatePickerDialog(view.getContext(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        date = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                        txtDate.setText("Ngày tạo : " +date);
                    loadDataDiaryWithDate();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }

    public void loadDataDiaryWithDate(){
        listDiary.clear();
        diaryAdapter.notifyDataSetChanged();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("diary_date", date);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(URLManage.getInstance().getMainURL() + "/diary/" + UserProfile.getUser().getUsername() + "/view")
                .addJSONObjectBody(jsonObject)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("values");
                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Diary diary = new Diary();
                                diary.setDate(jsonObject.getString("diary_date"));
                                diary.setWorkoutName(jsonObject.getString("lesson_name"));
                                diary.setTeamName(jsonObject.getString("team_name"));
                                diary.setLessonID(jsonObject.getInt("lesson_id"));
                                listDiary.add(diary);
                                diaryAdapter.notifyDataSetChanged();
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

    public void action() {
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDataDiary();
            }
        });

    }

}
