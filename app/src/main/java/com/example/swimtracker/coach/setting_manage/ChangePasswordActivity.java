package com.example.swimtracker.coach.setting_manage;

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
import com.example.swimtracker.LoginActivity;
import com.example.swimtracker.R;
import com.example.swimtracker.coach.MainActivity;
import com.example.swimtracker.user_manage.URLManage;
import com.example.swimtracker.user_manage.UserProfile;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText edt_old_password,edt_new_password,edt_confirm_password;
    Button btn_cancel, btn_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        init();
        action();
    }

    public void changePassword () {
        Log.d("CP", "A");
        final String username = UserProfile.getInstance().getUser().getUsername();
        Log.d("CP", ""+username);
        final String newPassword = edt_new_password.getText().toString();
        final String oldPassword = edt_old_password.getText().toString();

        if (isEmpty(edt_new_password)) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isValidPassword()) {
            Toast.makeText(this, "Vui lòng nhập lại mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }

        if (checkSpecialCharacterPassword(edt_new_password)) {
            Toast.makeText(this, "Mật Khẩu không được có ký tự đặc biệt", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!isMatch()) {
            Toast.makeText(ChangePasswordActivity.this, "Mật khẩu không khớp. ", Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject result = addToJSONObject(newPassword,oldPassword);

        AndroidNetworking.post(URLManage.getInstance().getMainURL() + "/api/profile/"+username+"/changepassword")
                .addJSONObjectBody(result)
                .addHeaders("Authorization","Bearer "+ UserProfile.getAccessToken())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("CP", "B");
                        try {
                            if (response.getBoolean("success") ) {
                                Log.d("CP", "Change password success");
                                Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                                startActivity(intent);

                            } else {
                                Log.d("CP", "Fail");

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

    public void init() {
        AndroidNetworking.initialize(getApplication());

        edt_old_password = (EditText) findViewById(R.id.edt_old_password);
        edt_new_password = (EditText) findViewById(R.id.edt_new_password);
        edt_confirm_password = (EditText) findViewById(R.id.edt_confirm_password);

        btn_update = (Button) findViewById(R.id.btn_update);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
    }

    public void action() {
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangePasswordActivity.this, MainActivity.class);
                intent.putExtra("openSetting",true);
                overridePendingTransition(0, 0);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                startActivity(intent);
            }
        });
    }

    public JSONObject addToJSONObject( String new_password,String old_password){
        JSONObject result = new JSONObject();
        JSONObject user = new JSONObject();
        try {
            user.put("password", old_password);
            user.put("new_password",new_password);
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

    public boolean isValidPassword() {
        String newPassword = edt_new_password.getText().toString();
        String newPasswordConfirm = edt_confirm_password.getText().toString();

        if (newPassword.length() <= 6 && !newPasswordConfirm.equals(newPassword))
            return true;
        return false;
    }

    public boolean checkSpecialCharacterPassword(EditText editText) {
        String string = editText.getText().toString();
        String pattern = "[a-zA-Z0-9.! ]*";

        if (!string.matches(pattern))
            return true;
        return false;
    }

    public boolean isMatch() {
        String password = edt_new_password.getText().toString();
        String confirmPassword = edt_confirm_password.getText().toString();

        if(password.equals(confirmPassword))
            return true;
        return false;
    }
}
