package com.example.swimtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.example.swimtracker.user_manage.UserProfile;

//import android.support.v7.app.AppCompatActivity;

public class ForgotPasswordActivity2 extends AppCompatActivity {


    Button btn_next, btn_back;
    EditText edt_confrim_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password2);
        init();
        action();
    }


    public void sendCode() {
        final String code = edt_confrim_code.getText().toString();
        String email_code = UserProfile.getAccessToken();
       if(isEmpty(edt_confrim_code)) {
           Toast.makeText(ForgotPasswordActivity2.this, "Thông tin không được bỏ trống ", Toast.LENGTH_SHORT).show();
           return;
       }

       if(code.equals(email_code)) {
            Intent intent = new Intent(ForgotPasswordActivity2.this,ForgotPasswordActivity3.class);
            startActivity(intent);
       } else {
           Toast.makeText(ForgotPasswordActivity2.this, "Mã xác nhận không hợp lệ", Toast.LENGTH_SHORT).show();
       }


    }



    private void action() {
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCode();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPasswordActivity2.this,ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    public void init() {
        //Network
        AndroidNetworking.initialize(getApplication());

        //Button
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_back = (Button) findViewById(R.id.btn_back);

        //EditText
        edt_confrim_code = (EditText) findViewById(R.id.edt_confirm_code);

        Log.d("FGW2", "Token:"+ UserProfile.getAccessToken());
        Log.d("FGW2", "Identity:"+ UserProfile.getInstance().getIdentity());


    }


    public boolean isEmpty(EditText editText) {
        String string = editText.getText().toString();

        if (string.equals(""))
            return true;
        return false;
    }



}
