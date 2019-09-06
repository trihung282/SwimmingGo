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
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.swimtracker.user_manage.URLManage;
import com.example.swimtracker.user_manage.UserProfile;

import org.json.JSONException;
import org.json.JSONObject;

//import android.support.v7.app.AppCompatActivity;

public class ForgotPasswordActivity3 extends AppCompatActivity {


    Button btn_agree;
    EditText  edt_new_password, edt_confrim_new_passowrd, edt_username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password3);
        init();
        action();
    }


    public void sendPassword() {
        final String new_password = edt_new_password.getText().toString();
        final String username = edt_username.getText().toString();
        String identity = UserProfile.getInstance().getIdentity();
        Log.d("FG3","Identity"+ UserProfile.getInstance().getIdentity());

       if(isEmpty(edt_new_password) && isEmpty(edt_confrim_new_passowrd)) {
           Toast.makeText(ForgotPasswordActivity3.this, "Thông tin không được bỏ trống", Toast.LENGTH_SHORT).show();
           return;
       }

       if(!isMatch()) {
           Toast.makeText(ForgotPasswordActivity3.this, "Mật khẩu không khớp. ", Toast.LENGTH_SHORT).show();
           return;
       }

       JSONObject result = addToJSONObject(new_password,username);

       AndroidNetworking.post(URLManage.getInstance().getMainURL() + "/api/forgotpassword/"+identity+">/newpassword")
               .addHeaders("Authorization","Bearer "+ UserProfile.getAccessToken())
               .addJSONObjectBody(result)
               .build()
               .getAsJSONObject(new JSONObjectRequestListener() {
                   @Override
                   public void onResponse(JSONObject response) {
                       try {
                           if(response.getBoolean("success")) {
                               Log.d("FG3","Success");
                               Intent intent = new Intent(ForgotPasswordActivity3.this, LoginActivity.class);
                               startActivity(intent);
                           } else {
                                Log.d("FG3","Fail");
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
        btn_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPassword();
            }
        });
    }

    public void init() {
        //Network
        AndroidNetworking.initialize(getApplication());

        //Button
        btn_agree = (Button) findViewById(R.id.btn_agree);

        //EditText
        edt_username = (EditText) findViewById(R.id.edt_username);
        edt_new_password = (EditText) findViewById(R.id.edt_new_password);
        edt_confrim_new_passowrd = (EditText) findViewById(R.id.edt_confirm_password);

    }



    public JSONObject addToJSONObject(String new_password,String username){
        JSONObject result = new JSONObject();
        JSONObject user = new JSONObject();
        try {
            user.put("username", username);
            user.put("new_password", new_password);
            result.put("user", user);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;

    }


    public boolean isEmpty(EditText editText) {
        String string = editText.getText().toString();

        if (string.equals(""))
            return true;
        return false;
    }

    public boolean isMatch() {
        String password = edt_new_password.getText().toString();
        String confirmPassword = edt_confrim_new_passowrd.getText().toString();

        if(password.equals(confirmPassword))
            return true;
        return false;
    }

}
