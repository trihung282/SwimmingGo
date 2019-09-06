package com.example.swimtracker.coach.setting_manage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.swimtracker.R;

public class ChartViewActivity extends AppCompatActivity {
    RadioGroup rdgStatis;
    RadioButton rdbWeek, rdbMonth, rdbQuarter;
    TextView txtSwimmerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_view);
        initView();
        initDatabase();
        action();
    }

    public void initView(){
        rdgStatis = findViewById(R.id.rdg_statis);
        rdbWeek = findViewById(R.id.rdb_week);
        rdbMonth = findViewById(R.id.rdb_month);
        rdbQuarter = findViewById(R.id.rdb_quarter);
        txtSwimmerName = findViewById(R.id.txt_swimmer_name);
    }

    public void initDatabase(){
        txtSwimmerName.setText("");
        rdbWeek.setChecked(true);
    }

    public void action(){

    }
}
