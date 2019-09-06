package com.example.swimtracker.coach.team_manage;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.swimtracker.R;
import com.example.swimtracker.user_manage.Swimmer;
import com.example.swimtracker.user_manage.URLManage;
import com.example.swimtracker.user_manage.UserProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewSwimmerFragment extends Fragment {
    Button btnAddNewSwimmer, btnRepsPlus, btnRepsMinus, btnFinish;
    EditText edtReps;
    LinearLayout layoutAddNewSwimmer;
    ListView lvNewSwimmer;
    ArrayAdapter<String> adapter;
    View view;
    Team team;
    List<String> list = new ArrayList<>();
    List<Account> listAccount = new ArrayList<>();

    public AddNewSwimmerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_new_swimmer, container, false);
        initView();
        action();
        return view;
    }


    private void initView() {
        AndroidNetworking.initialize(getContext());

        Intent intent = getActivity().getIntent();
        team = new Team(intent.getStringExtra("teamName"), intent.getStringExtra("teamAge"), intent.getIntExtra("teamID", 10));


        lvNewSwimmer = view.findViewById(R.id.lv_swimmer);


        btnAddNewSwimmer = view.findViewById(R.id.btn_add_new_swimmer);
        btnRepsPlus = view.findViewById(R.id.btn_reps_plus);
        btnRepsMinus = view.findViewById(R.id.btn_reps_minus);
        btnFinish = view.findViewById(R.id.btn_finish);

        edtReps = view.findViewById(R.id.edt_rep);
        getData();
        adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, list);
        lvNewSwimmer.setAdapter(adapter);
        layoutAddNewSwimmer = view.findViewById(R.id.layout_add_new_swimmer);
    }

    private void action() {
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAddSwimmer();
            }
        });

        btnAddNewSwimmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewSwimmer();
                layoutAddNewSwimmer.setVisibility(View.VISIBLE);
            }
        });

        edtReps.setText("1");
        btnRepsMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(edtReps.getText().toString()) > 0)
                    edtReps.setText("" + (Integer.parseInt(edtReps.getText().toString()) - 1));
                else {
                    edtReps.setText("" + 0);
                }
            }
        });

        btnRepsPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(edtReps.getText().toString()) < 50)
                    edtReps.setText("" + (Integer.parseInt(edtReps.getText().toString()) + 1));
                else {
                    edtReps.setText("50");
                }
            }
        });
    }

    private void finishAddSwimmer() {
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try {
            for (int i = 0; i < listAccount.size(); i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("username", listAccount.get(i).getUsername());
                jsonObject.put("password", listAccount.get(i).getPassword());
                jsonArray.put(jsonObject);
            }
            result.put("swimmers", jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(URLManage.getInstance().getMainURL() + "/api/" + UserProfile.getInstance().getUser().getUsername() + "/addswimmer")
                .addHeaders("Authorization", "Bearer " + UserProfile.getAccessToken())
                .addJSONObjectBody(result)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success")){
                                for(int i = 0; i < listAccount.size(); i++){
                                    Swimmer swimmer = new Swimmer();
                                    swimmer.setUsername(listAccount.get(i).getUsername());
                                    ListSwimmer.getInstance().getListSwimmer().add(swimmer);
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
        JSONObject email = new JSONObject();
        try {
            email.put("email", UserProfile.getUser().getEmail());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(URLManage.getInstance().getMainURL() +"/team/" + UserProfile.getUser().getUsername() + "/"
                + team.getTeamName() +"/add")
                .addHeaders("Authorization", "Bearer " + UserProfile.getAccessToken())
                .addJSONObjectBody(email)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success"))
                                startActivity(new Intent(getContext(), ListSwimmerActivity.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    private void addNewSwimmer() {
        Integer reps = Integer.parseInt(edtReps.getText().toString());
        AndroidNetworking.get(URLManage.getInstance().getMainURL() + "/api/" + UserProfile.getInstance().getUser().getUsername() + "/createswimmer/" + reps)
                .addHeaders("Authorization", "Bearer " + UserProfile.getAccessToken())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            listAccount = new ArrayList<>();
                            Log.d("swimmer", "success");
                            JSONArray jsonArray = response.getJSONObject("values").getJSONArray("swimmers");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                String password = jsonArray.getJSONObject(i).getString("password");
                                String username = jsonArray.getJSONObject(i).getString("username");
                                listAccount.add(new Account(username, password));
                                list.add("Username : " + username + ", " + "Password : " + password);
                                adapter.notifyDataSetChanged();
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

    public void getData() {


    }

}
