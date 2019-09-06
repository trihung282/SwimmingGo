package com.example.swimtracker.coach.team_manage;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.swimtracker.R;
import com.example.swimtracker.user_manage.URLManage;
import com.example.swimtracker.user_manage.UserProfile;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TeamFragment extends Fragment {
    View view;
    RecyclerView mRecyclerView;
    TeamRecyclerViewAdapter adapter;
    Button btnAddTeam, btnAddWorkout;
    String URL_TEAM = URLManage.getInstance().getMainURL() + "/team/" +  UserProfile.getInstance().getUser().getUsername() ;
    String accessToken = ("Bearer " + UserProfile.getAccessToken());
    List<String> listAge = ListAge.getInstance().getListAge();
    public TeamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_team, container, false);
        AndroidNetworking.initialize(getContext());
        initRecyclerView();
        btnAddTeam = view.findViewById(R.id.btn_add_team);
        action();
        return view;
    }

    public void action(){
        btnAddTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddTeamDialog();
            }
        });
    }


    public void initRecyclerView(){
        loadTeamAgeData();

        mRecyclerView = view.findViewById(R.id.recycler_view_team);
        adapter = new TeamRecyclerViewAdapter();
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    public void showAddTeamDialog(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.setTitle("Thêm nhóm");
        dialog.setContentView(R.layout.custom_dialog);
        dialog.show();

        final EditText edtTeamName = dialog.findViewById(R.id.edt_team_name);
        final Spinner spnTeamAge = dialog.findViewById(R.id.spn_age);
        Button btnAdd = dialog.findViewById(R.id.btn_add_team);
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, listAge);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnTeamAge.setAdapter(adapterSpinner);

        Button btnCancel = dialog.findViewById(R.id.btn_cancel);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String teamName = edtTeamName.getText().toString();
                final String teamAge = spnTeamAge.getSelectedItem().toString();
                JSONObject jsonObject = new JSONObject();
                JSONObject result = new JSONObject();
                try {
                    jsonObject.put("name", teamName);
                    jsonObject.put("age", teamAge);
                    result.put("team", jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AndroidNetworking.post(URL_TEAM+"/add")
                        .addHeaders("Authorization", accessToken)
                        .addJSONObjectBody(result)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (response.getBoolean("success")){
                                        int teamID = response.getInt("team_id");
                                        ListTeam.getInstance().addTeam(new Team(teamName, teamAge, teamID));
                                        adapter.notifyDataSetChanged();
                                        dialog.dismiss();
                                    }else if (response.getString("result").equals("team name exist"))
                                        Toast.makeText(getContext(), "Ten nhom da ton tai", Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {

                            }
                        });
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    public void loadTeamAgeData(){

    }

}
