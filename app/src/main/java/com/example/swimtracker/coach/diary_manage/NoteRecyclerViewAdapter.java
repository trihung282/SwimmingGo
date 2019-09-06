package com.example.swimtracker.coach.diary_manage;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.swimtracker.R;

import java.util.ArrayList;
import java.util.List;

public class NoteRecyclerViewAdapter extends RecyclerView.Adapter<NoteRecyclerViewAdapter.ViewHolder> {

    View view;
    List<Note> listNote = ListNote.getInstance().getListNote();
    @NonNull
    @Override
    public NoteRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        view = inflater.inflate(R.layout.item_note, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteRecyclerViewAdapter.ViewHolder viewHolder,final int i) {
        viewHolder.txtSwimmerName.setText(listNote.get(i).getSwimmer().getFullName());
        viewHolder.txtRank.setText("Xếp loại : " + listNote.get(i).getRank());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dlg = new Dialog(view.getContext());
                dlg.setContentView(R.layout.note_dialog);

                Button btnCancel = dlg.findViewById(R.id.btn_cancel);
                Button btnAdd = dlg.findViewById(R.id.btn_add);
                final Spinner spnRank = dlg.findViewById(R.id.spn_rank);

                List<String> listRank = new ArrayList<>();
                listRank.add("Giỏi");
                listRank.add("Khá");
                listRank.add("Trung bình");

                ArrayAdapter<String> rankAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, listRank);
                spnRank.setAdapter(rankAdapter);

                final EditText edtNote = dlg.findViewById(R.id.edt_note);
                TextView txtSwimmerName = dlg.findViewById(R.id.txt_swimmer_name);


                txtSwimmerName.setText(listNote.get(i).getSwimmer().getFullName());
                edtNote.setText(listNote.get(i).getNote());

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dlg.dismiss();
                    }
                });
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listNote.get(i).setNote(edtNote.getText().toString());
                        listNote.get(i).setRank(spnRank.getSelectedItem().toString());
                        notifyDataSetChanged();
                        dlg.dismiss();
                    }
                });
                dlg.show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return listNote.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtSwimmerName, txtRank;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSwimmerName = itemView.findViewById(R.id.txt_swimmer_name);
            txtRank = itemView.findViewById(R.id.txt_rank);
        }
    }
}
