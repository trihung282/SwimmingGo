package com.example.swimtracker.coach.diary_manage;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.swimtracker.R;
import com.example.swimtracker.coach.workout_manage.Workout;
import com.example.swimtracker.user_manage.Swimmer;
import com.example.swimtracker.user_manage.URLManage;
import com.example.swimtracker.user_manage.UserProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DiaryRecyclerViewAdapter extends RecyclerView.Adapter<DiaryRecyclerViewAdapter.ViewHolder> {
    View view;
    List<Diary> listDiary;
    List<Note> listNote = new ArrayList<>();
    ViewNoteRecyclerViewAdapter adapter;

    @NonNull
    @Override
    public DiaryRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        view = inflater.inflate(R.layout.item_diary, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public DiaryRecyclerViewAdapter(List<Diary> list){
        listDiary = list;
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryRecyclerViewAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.txtWorkoutName.setText(listDiary.get(i).getWorkoutName());
        viewHolder.txtTeamName.setText(listDiary.get(i).getTeamName());
        viewHolder.txtDate.setText(listDiary.get(i).getDate());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dlg = new Dialog(view.getContext());
                dlg.setContentView(R.layout.diary_dialog);

                TextView txtDate = dlg.findViewById(R.id.txt_date);
                TextView txtTeamName = dlg.findViewById(R.id.txt_team_name);
                Button btnBack = dlg.findViewById(R.id.btn_back);
                TextView txtWorkoutName = dlg.findViewById(R.id.txt_workout_name);

                txtDate.setText("Ngày : " + listDiary.get(i).getDate());
                txtTeamName.setText("Nhóm : " + listDiary.get(i).getTeamName());
                txtWorkoutName.setText(listDiary.get(i).getWorkoutName());


                btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dlg.dismiss();
                    }
                });
                loadListNote(i);

                RecyclerView mRecyclerView = dlg.findViewById(R.id.recycler_view_note);
                adapter = new ViewNoteRecyclerViewAdapter(listNote);
                mRecyclerView.setAdapter(adapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

                dlg.show();
            }
        });
    }

    public void loadListNote(int i){
        AndroidNetworking.initialize(view.getContext());
        AndroidNetworking.get(URLManage.getInstance().getMainURL() + "/diary/" + UserProfile.getUser().getUsername() + "/view/" +listDiary.get(i).getLessonID())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("values");
                            for(int i = 0 ; i < jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Note note = new Note();
                                Swimmer swimmer = new Swimmer();

                                swimmer.setFirst_name(jsonObject.getString("first_name"));
                                swimmer.setLast_name(jsonObject.getString("last_name"));
                                note.setSwimmer(swimmer);
                                note.setRank(jsonObject.getString("rank"));
                                note.setNote(jsonObject.getString("note"));
                                listNote.add(note);
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

    @Override
    public int getItemCount() {
        return listDiary.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtWorkoutName, txtTeamName, txtDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtWorkoutName = itemView.findViewById(R.id.txt_workout_name);
            txtTeamName = itemView.findViewById(R.id.txt_team_name);
            txtDate = itemView.findViewById(R.id.txt_date);
        }
    }
}
