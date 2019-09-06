package com.example.swimtracker.coach.workout_manage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.swimtracker.R;

import java.util.List;

public class RecordRecyclerViewAdapter extends RecyclerView.Adapter<RecordRecyclerViewAdapter.ViewHolder> {
    View view;
    List<Record> listRecord;

    public RecordRecyclerViewAdapter(List<Record> listRecord) {
        this.listRecord = ListRecord.getInstance().getListRecord();
    }

    @NonNull
    @Override
    public RecordRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        view = inflater.inflate(R.layout.item_record, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecordRecyclerViewAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.txtSwimmerName.setText(listRecord.get(i).getSwimmer().getFullName());
        if (listRecord.get(i).getMinute() != 0)
            viewHolder.edtMinute.setText(listRecord.get(i).getMinute() + "");
        else
            viewHolder.edtMinute.setText("0");
        if (listRecord.get(i).getSec() != 0)
            viewHolder.edtSec.setText(listRecord.get(i).getSec() + "");
        else
            viewHolder.edtSec.setText("0");
        if (listRecord.get(i).getMillisec() != 0)
            viewHolder.edtMilliSec.setText(listRecord.get(i).getMillisec() + "");
        else
            viewHolder.edtMilliSec.setText("0");

        viewHolder.edtMinute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.edtMinute.getText().clear();
            }
        });
        viewHolder.edtSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.edtSec.getText().clear();
            }
        });
        viewHolder.edtMilliSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.edtMilliSec.getText().clear();
            }
        });

        viewHolder.edtMinute.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                listRecord.get(i).setMinute(Integer.parseInt(viewHolder.edtMinute.getText().toString()));
            }
        });
        viewHolder.edtSec.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                listRecord.get(i).setSec(Integer.parseInt(viewHolder.edtSec.getText().toString()));
            }
        });
        viewHolder.edtMilliSec.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                listRecord.get(i).setMillisec(Integer.parseInt(viewHolder.edtMilliSec.getText().toString()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listRecord.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtSwimmerName;
        EditText edtMinute, edtSec, edtMilliSec;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSwimmerName = itemView.findViewById(R.id.txt_swimmer_name);
            edtMinute = itemView.findViewById(R.id.edt_minute);
            edtSec = itemView.findViewById(R.id.edt_sec);
            edtMilliSec = itemView.findViewById(R.id.edt_millisec);
        }
    }
}
