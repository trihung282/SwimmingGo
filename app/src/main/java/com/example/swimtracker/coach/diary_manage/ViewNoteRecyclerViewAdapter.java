package com.example.swimtracker.coach.diary_manage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.swimtracker.R;

import java.util.List;

public class ViewNoteRecyclerViewAdapter extends RecyclerView.Adapter<ViewNoteRecyclerViewAdapter.ViewHolder> {
    View view;
    List<Note> listNote;
    @NonNull
    @Override
    public ViewNoteRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        view = inflater.inflate(R.layout.item_view_note, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public ViewNoteRecyclerViewAdapter(List<Note> list){
        listNote = list;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewNoteRecyclerViewAdapter.ViewHolder viewHolder, int i) {
        viewHolder.txtSwimmerName.setText(listNote.get(i).getSwimmer().getFullName());
        viewHolder.txtRank.setText("Xếp hạng : " + listNote.get(i).getRank());
        viewHolder.txtNote.setText(listNote.get(i).getNote());
    }

    @Override
    public int getItemCount() {
        return listNote.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtSwimmerName, txtRank, txtNote;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtSwimmerName = itemView.findViewById(R.id.txt_swimmer_name);
            txtRank = itemView.findViewById(R.id.txt_rank);
            txtNote = itemView.findViewById(R.id.txt_note);
        }
    }
}
