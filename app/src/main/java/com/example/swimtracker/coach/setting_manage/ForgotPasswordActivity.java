package com.example.swimtracker.coach.setting_manage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.swimtracker.LoginActivity;
import com.example.swimtracker.R;
import com.example.swimtracker.user_manage.URLManage;
import com.example.swimtracker.user_manage.UserProfile;

import org.json.JSONException;
import org.json.JSONObject;

//import android.support.v7.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {


    Button btn_next, btn_back;
    EditText edt_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        init();
        action();
    }


    public void sendEmail() {
        final String email = edt_email.getText().toString();

       if(!isValidEmail(email) || isEmpty(edt_email)) {
           Toast.makeText(ForgotPasswordActivity.this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
           return;
       }


       AndroidNetworking.get(URLManage.getInstance().getMainURL() + "/api/public/forgotpassword/"+email+"/sendpin")
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (response.getBoolean("success") ) {
                                        UserProfile.getInstance().setAccessToken(response.getJSONObject("values").getString("token"));
                                        UserProfile.getInstance().setIdentity(response.getJSONObject("values").getString("identity"));

                                        Intent intent = new Intent(ForgotPasswordActivity.this, ForgotPasswordActivity2.class);
                                        startActivity(intent);
                                    } else {

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



    private void action() {
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
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

        edt_email = (EditText) findViewById(R.id.edt_email);

    }

    public boolean isValidEmail(String email) {

        String pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (!email.matches(pattern))
            return false;
        return true;
    }


    public boolean isEmpty(EditText editText) {
        String string = editText.getText().toString();

        if (string.equals(""))
            return true;
        return false;
    }

}
