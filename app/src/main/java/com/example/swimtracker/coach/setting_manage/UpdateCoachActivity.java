package com.example.swimtracker.coach.setting_manage;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.swimtracker.R;
import com.example.swimtracker.user_manage.URLManage;
import com.example.swimtracker.user_manage.User;
import com.example.swimtracker.user_manage.UserProfile;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class UpdateCoachActivity extends AppCompatActivity {

    Button btn_cancel, btn_agree;
    TextView tv_dob;
    EditText edt_address, edt_pnumber, edt_email, edt_first_name, edt_last_name;
    RadioGroup radio_group_gender;
    RadioButton radioButton;

    DatePickerDialog.OnDateSetListener dateSetListener;

    private User coach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_info);
        init();
        action();
        loadData();
    }

    public void init() {
        //Network init
        AndroidNetworking.initialize(getApplicationContext());

        //Buttons
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_agree = (Button) findViewById(R.id.btn_agree);

        //EditText
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_pnumber = (EditText) findViewById(R.id.edt_pnumber);
        edt_address = (EditText) findViewById(R.id.edt_address);
        edt_first_name = (EditText) findViewById(R.id.edt_first_name);
        edt_last_name = (EditText) findViewById(R.id.edt_last_name);

        //Text View
        tv_dob = (TextView) findViewById(R.id.tv_dob);

        //Date picker
        tv_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(UpdateCoachActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;

                String date = year + "-" + month + "-" + dayOfMonth;
                tv_dob.setText(date);
            }
        };

        //Radio button
        radio_group_gender = (RadioGroup) findViewById(R.id.radioGender);

    }

    public void loadData() {
        coach = UserProfile.getInstance().getUser();
        edt_first_name.setText(coach.getFirst_name());
        edt_last_name.setText(coach.getLast_name());
        edt_address.setText(coach.getAddress());
        edt_pnumber.setText(coach.getPhone());
        edt_email.setText(coach.getEmail());

        /*SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        String dob = date.format(Date.parse(coach.getDob()));*/

        tv_dob.setText(coach.getDob());


    }

    public void action() {
        btn_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void update() {


        String username = coach.getUsername();

        String address = edt_address.getText().toString();
        String pnumber = edt_pnumber.getText().toString();
        String email = edt_email.getText().toString();
        String dob = tv_dob.getText().toString();
        String first_name = edt_first_name.getText().toString();
        String last_name = edt_last_name.getText().toString();
        String parent_name = "null";
        String parent_phone = "null";
        String height = "null";
        String weight = "null";
        final int gender = getGender();

        final JSONObject result = addToJSONObject(first_name, last_name, pnumber, email, address, dob, gender, height, weight, parent_name, parent_phone);

        AndroidNetworking.post(URLManage.getInstance().getMainURL() + "/api/profile/" + username + "/edit")
                .addHeaders("Authorization", "Bearer " + UserProfile.getAccessToken())
                .addJSONObjectBody(result)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success")) {
                                Log.e("User",""+result);
                                Toast.makeText(UpdateCoachActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                setProfile();
                                Intent intent = new Intent(UpdateCoachActivity.this, CoachViewInfo.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(UpdateCoachActivity.this, response.getString("Fail"), Toast.LENGTH_SHORT).show();
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

    private void setProfile() {
        User user = UserProfile.getInstance().getUser();
        user.setFirst_name(edt_first_name.getText().toString());
        user.setLast_name(edt_last_name.getText().toString());
        user.setEmail(edt_email.getText().toString());
        user.setDob(tv_dob.getText().toString());
        user.setGender((radio_group_gender.getCheckedRadioButtonId() == R.id.radioFemale) ? 0 : 1);
        user.setAddress(edt_address.getText().toString());
    }

    public JSONObject addToJSONObject(String first_name, String last_name, String phone, String email, String address, String dob, int gender, String height, String weight, String parent_name, String parent_phone) {
        JSONObject result = new JSONObject();
        JSONObject user = new JSONObject();
        try {
            user.put("first_name", first_name);
            user.put("last_name", last_name);
            user.put("gender", gender);
            user.put("dob", dob);
            user.put("weight", weight);
            user.put("height", height);
            user.put("address", address);
            user.put("phone", phone);
            user.put("email", email);
            user.put("parent_name", parent_name);
            user.put("parent_phone", parent_phone);
            result.put("user", user);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;

    }


    public boolean isValidEmail() {
        String email = edt_email.getText().toString();
        String pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (!email.matches(pattern))
            return false;
        return true;
    }

    public boolean isValidPhone() {
        String len = edt_pnumber.getText().toString();
        return len == null;
    }

    public boolean isValidAddress() {
        String address = edt_address.getText().toString();
        String pattern = "[a-zA-Z0-9,/ ]*";
        if (address.matches(pattern) && address.length() <= 100)
            return true;
        return false;
    }


    public int getGender() {
        int selectedId = radio_group_gender.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedId);

        if (radioButton.getText().equals("Nam"))
            return 1;
        return 0;
    }
}

