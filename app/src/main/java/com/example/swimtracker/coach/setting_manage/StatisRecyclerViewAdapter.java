package com.example.swimtracker.coach.setting_manage;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.swimtracker.R;
import com.example.swimtracker.coach.team_manage.ListSwimmer;
import com.example.swimtracker.user_manage.Swimmer;

public class StatisRecyclerViewAdapter extends RecyclerView.Adapter<StatisRecyclerViewAdapter.ViewHolder> {
    View view;
    @NonNull
    @Override
    public StatisRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        view = inflater.inflate(R.layout.item_swimmer, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StatisRecyclerViewAdapter.ViewHolder viewHolder, int i) {
        final Swimmer swimmer = ListSwimmer.getInstance().getSwimmer(i);
        viewHolder.txtFullName.setText("Tên :" + swimmer.getFullName());
        viewHolder.txtDob.setText("Ngày sinh : " + swimmer.getDob());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(new Intent(view.getContext(), ChartViewActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return ListSwimmer.getInstance().getListSwimmer().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtFullName, txtDob;
        Button btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtFullName = itemView.findViewById(R.id.txt_full_name);
            txtDob = itemView.findViewById(R.id.txt_dob);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
