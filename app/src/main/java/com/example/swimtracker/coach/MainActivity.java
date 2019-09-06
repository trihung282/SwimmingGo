package com.example.swimtracker.coach;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.swimtracker.coach.diary_manage.DiaryFragment;
import com.example.swimtracker.coach.library_manage.LibraryFragment;
import com.example.swimtracker.coach.setting_manage.SettingFragment;
import com.example.swimtracker.coach.team_manage.TeamFragment;
import com.example.swimtracker.coach.workout_manage.DataManage;
import com.example.swimtracker.coach.workout_manage.WorkoutFragment;
import com.example.swimtracker.R;
import com.example.swimtracker.user_manage.UserProfile;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    String URL_VIEWTEAM = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_coach);
        initView();
        initDatabase();

    }
    public void initView(){
//        toolbar = findViewById(R.id.toolbar_main);
//        setSupportActionBar(toolbar);
        Log.e("list", DataManage.getInstance().getListDistance().size() +"");
        bottomNavigationView = findViewById(R.id.bottom_bar);
        loadFragment(new WorkoutFragment());
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.item_team:
                        loadFragment(new TeamFragment());
                        return true;
                    case R.id.item_diary:
                        loadFragment(new DiaryFragment());
                        return true;
                    case R.id.item_setting:
                        loadFragment(new SettingFragment());
                        return true;
                    case R.id.item_workout:
                        loadFragment(new WorkoutFragment());
                        return true;
                    case R.id.item_library:
                        loadFragment(new LibraryFragment());
                        return true;
                }
                return false;
            }
        });

    }
    
    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.layout_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void initDatabase(){
        AndroidNetworking.initialize(getApplication());

        AndroidNetworking.get(URL_VIEWTEAM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

}
