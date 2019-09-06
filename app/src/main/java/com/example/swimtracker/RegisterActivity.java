package com.example.swimtracker;

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
import com.example.swimtracker.user_manage.URLManage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;


public class RegisterActivity extends AppCompatActivity {


    Button btn_register,btn_cancel;
    RadioGroup radio_group_gender;
    RadioButton radioButton;
    EditText edt_username, edt_password, edt_fname, edt_lname, edt_phone, edt_email,edt_address;
    TextView tv_dob;
    String URL_SIGNUP = URLManage.getInstance().getMainURL() + "/api/public/register";

    DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initDialog();
        action();
    }

    public void action(){
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);

                startActivity(intent);
            }
        });


    }

    public void register(){

        formValidation();

        //Get data
        final String username = edt_username.getText().toString();
        final String password = edt_password.getText().toString();
        final String first_name = edt_fname.getText().toString();
        final String last_name = edt_lname.getText().toString();
        final String phone = edt_phone.getText().toString();
        final String email = edt_email.getText().toString();
        final String address = edt_address.getText().toString();
        final String dob = tv_dob.getText().toString();
        final int gender = getGender();

        //Create JSON

        JSONObject result = addToJSONObject(username,password,first_name,last_name,phone,email,address,dob,gender);

        //API Request
        AndroidNetworking.post(URL_SIGNUP)
                .addJSONObjectBody(result)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("ABC","Yes");
                        try {
                            if (response.getBoolean("success")){
                                Log.d("ABC","Yes 2");
                                Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(RegisterActivity.this, response.getString("Nope"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("ABC","Nope");
                    }
                });

    }

    public JSONObject addToJSONObject(String username, String password, String first_name, String last_name, String phone, String email, String address, String dob, int gender){
        JSONObject result = new JSONObject();
        JSONObject user = new JSONObject();
        try {
            user.put("username", username);
            user.put("password", password);
            user.put("first_name", first_name);
            user.put("last_name", last_name);
            user.put("dob",dob);
            user.put("gender",gender);
            user.put("address",address);
            user.put("phone",phone);
            user.put("email",email);
            result.put("user", user);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;

    }

    public void initDialog(){
        //Network
        AndroidNetworking.initialize(getApplication());

        //EditText
        edt_username = (EditText) findViewById(R.id.edt_username);
        edt_password = (EditText) findViewById(R.id.edt_password);
        edt_lname = (EditText) findViewById(R.id.edt_last_name);
        edt_fname = (EditText) findViewById(R.id.edt_first_name);
        edt_phone = (EditText) findViewById(R.id.edt_phone);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_address = (EditText) findViewById(R.id.edt_address);

        //TextView
        tv_dob = (TextView) findViewById(R.id.tv_dob);

        //Button
        btn_register = findViewById(R.id.btn_register);
        btn_cancel = findViewById(R.id.btn_cancel);

        //Radio button
        radio_group_gender = (RadioGroup) findViewById(R.id.radioGender);


        //DatePicker
        tv_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(RegisterActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener, year, month, day);
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


    }



    public void formValidation() {
        if(isEmpty(edt_username) || !isValidUsername(edt_username)){
            Toast.makeText(RegisterActivity.this, "Tên đăng nhập không hợp lệ ", Toast.LENGTH_SHORT).show();
            return;
        }


        if(isEmpty(edt_password) || !isValidPassword(edt_username)){
            Toast.makeText(RegisterActivity.this, "Mật khẩu không hợp lệ ", Toast.LENGTH_SHORT).show();
            return;
        }

        if(isEmpty(edt_lname) || !isValidName(edt_lname)) {
            Toast.makeText(RegisterActivity.this, "Họ không hợp lệ ", Toast.LENGTH_SHORT).show();
            return;
        }

        if(isEmpty(edt_fname) || !isValidName(edt_fname)) {
            Toast.makeText(RegisterActivity.this, "Tên không hợp lệ ", Toast.LENGTH_SHORT).show();
            return;
        }

        if(isEmpty(edt_phone) || !isValidPhone(edt_phone)) {
            Toast.makeText(RegisterActivity.this, "Số điện thoại không hợp lệ ", Toast.LENGTH_SHORT).show();
            return;
        }

        if(isEmpty(edt_email) || !isValidEmail(edt_email)) {
            Toast.makeText(RegisterActivity.this, "Email không hợp lệ ", Toast.LENGTH_SHORT).show();
            return;
        }

        if(isEmpty(edt_address) || !isValidAddress(edt_address)) {
            Toast.makeText(RegisterActivity.this, "Địa chỉ không hợp lệ ", Toast.LENGTH_SHORT).show();
            return;
        }
    }


    public boolean isValidEmail(EditText editText){
        String email = editText.getText().toString();
        String pattern ="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.matches(pattern))
            return true;
        return false;
    }

    public boolean isValidUsername(EditText editText) {
        String username = editText.getText().toString();
        String pattern ="[a-zA-Z0-9]*";

        if(username.matches(pattern) && username.length()>=6)
            return true;
        return false;

    }

    public boolean isValidPassword(EditText editText) {
        String password = editText.getText().toString();
        String pattern = "[a-zA-Z0-9.! ]*";

        if(password.matches(pattern) && password.length()>=6)
            return true;
        return false;
    }

    public boolean isValidPhone(EditText editText){
        return editText.getText().toString().length() == 10;
    }

    public boolean isValidAddress(EditText editText){
        String address = editText.getText().toString();
        String pattern = "[a-zA-Z0-9,/ ]*";
        if(address.matches(pattern) && address.length()<=100)
            return true;
        return false;
    }

    public boolean isValidName(EditText editText) {
        String name = editText.getText().toString();
        String pattern = "[a-zA-Z ]*";

        if(name.length() <=45 && name.matches(pattern))
            return true;
        return false;
    }

    public int getGender () {
        int selectedId = radio_group_gender.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedId);

        if(radioButton.getText().equals("Nam"))
            return 1;
        return 0;
    }

    public boolean isEmpty (EditText editText) {
        String inputField = editText.getText().toString();

        if(inputField.matches(""))
            return true;
        return false;
    }


}
