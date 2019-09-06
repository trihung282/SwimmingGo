package com.example.swimtracker.coach.workout_manage;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.example.swimtracker.user_manage.URLManage;
import com.example.swimtracker.user_manage.UserProfile;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class WorkoutRecyclerViewAdapter extends RecyclerView.Adapter<WorkoutRecyclerViewAdapter.ViewHolder> {
    private List<Workout> listWorkout;
    private View view;

    @NonNull
    @Override
    public WorkoutRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        view = inflater.inflate(R.layout.item_workout, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public WorkoutRecyclerViewAdapter(List<Workout> list) {
        this.listWorkout = list;
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutRecyclerViewAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.txtWorkoutName.setText(listWorkout.get(i).getWorkoutName());
        viewHolder.txtDate.setText("Ngày : " + listWorkout.get(i).getDate());
        viewHolder.txtTeamName.setText("Nhóm : " + listWorkout.get(i).getTeamName());
        if (listWorkout.get(i).getCheckRecord() == 1)
            viewHolder.itemView.setBackgroundColor(R.drawable.background_item_record);

        viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), EditWorkoutActivity.class);
                intent.putExtra("workout_name", listWorkout.get(i).getWorkoutName());
                intent.putExtra("team_name", listWorkout.get(i).getTeamName());
                intent.putExtra("date", listWorkout.get(i).getDate());
                view.getContext().startActivity(intent);
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listWorkout.get(i).getCheckRecord() == 0) {
                    Intent intent = new Intent(view.getContext(), ViewWorkoutActivity.class);
                    intent.putExtra("workout_name", listWorkout.get(i).getWorkoutName());
                    intent.putExtra("team_name", listWorkout.get(i).getTeamName());
                    intent.putExtra("date", listWorkout.get(i).getDate());
                    view.getContext().startActivity(intent);
                }
            }
        });

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidNetworking.get(URLManage.getInstance().getMainURL() + "/workout/" + UserProfile.getInstance().getUser().getUsername() + "/lesson/delete/" + listWorkout.get(i).getWorkoutID())
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (response.getBoolean("success")) {
                                        listWorkout.remove(i);
                                        notifyDataSetChanged();
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
        });
    }

    @Override
    public int getItemCount() {
        return listWorkout.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtWorkoutName, txtDate, txtTeamName;
        private Button btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTeamName = itemView.findViewById(R.id.txt_team_name);
            txtWorkoutName = itemView.findViewById(R.id.txt_workout_name);
            txtDate = itemView.findViewById(R.id.txt_date);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
