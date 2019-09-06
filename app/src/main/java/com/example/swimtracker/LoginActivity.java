package com.example.swimtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.swimtracker.coach.MainActivity;
import com.example.swimtracker.coach.team_manage.ListAge;
import com.example.swimtracker.coach.team_manage.ListTeam;
import com.example.swimtracker.coach.workout_manage.DataManage;
import com.example.swimtracker.coach.workout_manage.ListDistance;
import com.example.swimtracker.coach.workout_manage.ListStyle;
import com.example.swimtracker.coach.workout_manage.ListWorkout;
import com.example.swimtracker.coach.workout_manage.Workout;
import com.example.swimtracker.user_manage.Swimmer;
import com.example.swimtracker.user_manage.URLManage;
import com.example.swimtracker.user_manage.Coach;
import com.example.swimtracker.user_manage.User;
import com.example.swimtracker.user_manage.UserProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    EditText edtUsername, edtPassword;
    Button btnLogin, btnResgister;
    TextView txtForgotPassword;
    String URL_LOGIN = URLManage.getInstance().getMainURL() + "/api/public/login";
    String URL_TEAM = URLManage.getInstance().getMainURL() + "/team/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    public void init(){
        AndroidNetworking.initialize(getApplication());

        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
        btnResgister = findViewById(R.id.btn_register);
        txtForgotPassword = findViewById(R.id.txt_forgot_password);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        btnResgister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

    }

    public void login(){
        final String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();
        Log.e("ABC","1");
        JSONObject result = addToJSONObject(username, password);
        AndroidNetworking.post(URL_LOGIN)
                .addJSONObjectBody(result)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("ABC","A");
                        try {
                            if (response.getBoolean("success")){
                                Log.e("ABC","B");
                                UserProfile.getInstance().setAccessToken(response.getJSONObject("values").getString("token"));
                                loadData();
                            }
                            else{
                                Log.e("ABC","C");
                                Toast.makeText(LoginActivity.this, "ec ec", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("ABC","ERROR");
                    }
                });
    }

    public JSONObject addToJSONObject(String username, String password){
        JSONObject result = new JSONObject();
        JSONObject user = new JSONObject();
        try {
            user.put("username", username);
            user.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;

    }

    public void loadData(){
        loadDataUser();
        loadListTeam();
        loadStyle();
        loadDistance();
        loadListAge();
        loadDataWorkout();
    }
    private void loadDataWorkout() {

    }

    private void loadDistance() {
        ListDistance.getInstance().newInstance();
        String URL_DISTANCE = URLManage.getInstance().getMainURL() + "/public/distance";
        AndroidNetworking.get(URL_DISTANCE)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ListDistance.getInstance().addDistanceFromJSONArray(response);
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    private void loadStyle() {
        ListStyle.getInstance().newInstance();
        final List<String> listStyle = ListStyle.getInstance().getListStyle();
        String URL_TYPE = URLManage.getInstance().getMainURL() + "/public/style";
        AndroidNetworking.get(URL_TYPE)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ListStyle.getInstance().addStyleFromJSONArray(response);

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    private void loadDataUser() {
        String accessToken = "Bearer " + UserProfile.getAccessToken();
        final String username = edtUsername.getText().toString();
        AndroidNetworking.get(URLManage.getInstance().getMainURL() +"/api/profile/" +username+"/view")
                .addHeaders("Authorization", accessToken)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        User user = null;
                        Log.e("ABC","D");
                        try {
                            JSONObject jsonObject = response.getJSONObject("values").getJSONObject("user");
                            if (jsonObject.getInt("role_id") == 0){
                                user = new Coach();
                                Log.e("ABC","E");
                                user.addDataFromJSONObject(jsonObject);
                            }else{
                                user = new Swimmer();
                                user.addDataFromJSONObject(jsonObject);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        user.setUsername(username);
                        UserProfile.getInstance().getData(user);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(LoginActivity.this,"MEOOOOOO", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void loadListTeam() {
        String accessToken = "Bearer " + UserProfile.getAccessToken();
        final String username = edtUsername.getText().toString();
        ListTeam.newInstance();
        AndroidNetworking.get(URL_TEAM +username + "/view")
                .addHeaders("Authorization", accessToken)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("ABCD","MEO");
                        try {
                            JSONArray listTeamJson = response.getJSONObject("values").getJSONArray("team");
                            for(int i = 0; i < listTeamJson.length(); i++){
                                ListTeam.getInstance().addTeamFromJSONObject(listTeamJson.getJSONObject(i));
                                Log.e("ABCD","BRUHHH");
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
    private void loadListAge() {
        AndroidNetworking.get(URLManage.getInstance().getMainURL() +"/public/age")
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i < response.length(); i++){
                            try {
                                ListAge.getInstance().getListAge().add(response.getJSONObject(i).getString("range_age"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
}
