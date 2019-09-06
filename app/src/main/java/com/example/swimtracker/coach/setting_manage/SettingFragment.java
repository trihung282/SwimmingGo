package com.example.swimtracker.coach.setting_manage;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.swimtracker.ChangePasswordActivity;
import com.example.swimtracker.CoachViewInfo;
import com.example.swimtracker.LoginActivity;
import com.example.swimtracker.R;
import com.example.swimtracker.user_manage.User;
import com.example.swimtracker.user_manage.UserProfile;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment  {

    Button btn_logout,btn_info,btn_change_password, btnStatis;
    TextView tv_name;
    ImageView img_gender;

    View view;

    private User coach;

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_setting, container, false);
        init();
        action();
        displayData();
        return view;

    }


    public void init() {
        coach = UserProfile.getInstance().getUser();

        btn_logout = (Button) view.findViewById(R.id.btn_logout);
        btn_info = (Button) view.findViewById(R.id.btn_show_info);
        btn_change_password = (Button) view.findViewById(R.id.btn_change_password);
        btnStatis = view.findViewById(R.id.btn_statis);

        tv_name = (TextView) view.findViewById(R.id.tv_name);
        img_gender = (ImageView) view.findViewById(R.id.img_gender);

    }

    public void action() {
        btnStatis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), StatisActivity.class));
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setCancelable(true);
                builder.setTitle("Đăng xuất ");
                builder.setMessage("Bạn có muốn đăng xuất ");

                builder.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.setPositiveButton("Co", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserProfile.getInstance().setAccessToken("");
                        Intent intent = new Intent(getActivity().getBaseContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                });
                builder.show();
            }
        });

        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getBaseContext(), CoachViewInfo.class);
                startActivity(intent);
            }
        });

        btn_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getBaseContext(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    public void displayData() {
        String name = coach.getLast_name() + " " + coach.getFirst_name();
        tv_name.setText(name);

        int gender = UserProfile.getInstance().getUser().getGender();
        if(gender == 1) {
            Log.d("Gen",""+gender);
            img_gender.setBackgroundResource(R.drawable.icon_gender_male);
        }
        else {
            img_gender.setBackgroundResource(R.drawable.icon_gender_female);
        }

    }

}
