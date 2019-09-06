package com.example.swimtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.example.swimtracker.coach.MainActivity;
import com.example.swimtracker.user_manage.User;
import com.example.swimtracker.user_manage.UserProfile;


public class CoachViewInfo extends AppCompatActivity {


    Button btn_update,btn_cancel;
    TextView tv_name,tv_address,tv_pnumber,tv_email,tv_dob;
    ImageView img_gender;


    private User coach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coach_view_info);
        init();
        action();
        displayData();
    }

    public void init() {
        //Network init
        AndroidNetworking.initialize(getApplicationContext());

        //Buttons
        btn_update = (Button) findViewById(R.id.btn_update);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

        //Text View
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_pnumber = (TextView) findViewById(R.id.tv_pnumber);
        tv_email = (TextView) findViewById(R.id.tv_email);
        tv_dob = (TextView) findViewById(R.id.tv_dob);

        //Image View
        img_gender = (ImageView) findViewById(R.id.img_gender);

    }

    public void action() {
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CoachViewInfo.this, UpdateCoachActivity.class);
                startActivity(intent);
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CoachViewInfo.this, MainActivity.class);
                intent.putExtra("openSetting",true);
                overridePendingTransition(0, 0);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                startActivity(intent);
            }
        });
    }

    public void displayData () {
        coach = UserProfile.getInstance().getUser();

        String name = coach.getLast_name() + " " + coach.getFirst_name();
        tv_name.setText(name);
        tv_address.setText(coach.getAddress());
        tv_pnumber.setText(coach.getPhone());

        /*SimpleDateFormat date =new SimpleDateFormat("dd/MM/yyyy");
        String dob = date.format(Date.parse(coach.getDob()));*/
        tv_email.setText(coach.getEmail());


        tv_dob.setText(coach.getDob());


        int gender = UserProfile.getInstance().getUser().getGender();
        if(gender == 1) {
            img_gender.setBackgroundResource(R.drawable.icon_gender_male);
        }
        else {
            img_gender.setBackgroundResource(R.drawable.icon_gender_female);
        }
    }

}

