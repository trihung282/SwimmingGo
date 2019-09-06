package com.example.swimtracker.coach.team_manage;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

public class TeamRecyclerViewAdapter extends RecyclerView.Adapter<TeamRecyclerViewAdapter.ViewHolder> {
    private View view;
    private String URL_TEAM = URLManage.getInstance().getMainURL() + "/team/" + UserProfile.getInstance().getUser().getUsername()+"/";
    private String accessToken = ("Bearer " + UserProfile.getAccessToken());
    private List<String> listAge = ListAge.getInstance().getListAge();
    public TeamRecyclerViewAdapter(){


    }
    @NonNull
    @Override


    public TeamRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        final Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        view = inflater.inflate(R.layout.item_team, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final TeamRecyclerViewAdapter.ViewHolder viewHolder, final int i) {
        final Team team = ListTeam.getInstance().getTeam(i);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), ListSwimmerActivity.class);
                intent.putExtra("teamName", team.getTeamName());
                intent.putExtra("teamAge", team.getTeamAge());
                intent.putExtra("teamID",team.getTeamID());
                view.getContext().startActivity(intent);
            }
        });

        viewHolder.txtTeamName.setText(team.getTeamName());
        viewHolder.txtTeamAge.setText("Độ tuổi : " + team.getTeamAge());
        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidNetworking.get(URLManage.getInstance().getMainURL()+"/team/" + UserProfile.getInstance().getUser().getUsername() + "/" + team.getTeamID() + "/delete")
                        .addHeaders("Authorization", accessToken)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (response.getBoolean("success")) {
                                        ListTeam.getInstance().removeTeam(i);
                                        notifyDataSetChanged();
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
        });
        viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.custom_dialog);
                dialog.setTitle("Edit");
                final EditText edtTeamName = dialog.findViewById(R.id.edt_team_name);
                edtTeamName.setText(team.getTeamName());

                final Spinner spnTeamAge = dialog.findViewById(R.id.spn_age);


                ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, listAge);
                adapterSpinner.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                spnTeamAge.setAdapter(adapterSpinner);
                Button btnAdd, btnCancel;
                btnAdd = dialog.findViewById(R.id.btn_add_team);
                btnCancel = dialog.findViewById(R.id.btn_cancel);

                btnAdd.setText("Sửa");

                TextView txtTitle = dialog.findViewById(R.id.txt_title);
                txtTitle.setText("Chỉnh sửa");

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
                        AndroidNetworking.post(URL_TEAM+ team.getTeamID() +"/edit")
                                .addHeaders("Authorization", accessToken)
                                .addJSONObjectBody(result)
                                .build()
                                .getAsJSONObject(new JSONObjectRequestListener() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            if (response.getBoolean("success")){
                                                ListTeam.getInstance().setTeam(i, new Team(teamName, teamAge, team.getTeamID()));
                                                notifyDataSetChanged();
                                                dialog.dismiss();
                                            }else if (response.getString("result").equals("team name exist"))
                                                Toast.makeText(view.getContext(), "Ten nhom da ton tai", Toast.LENGTH_SHORT).show();
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

                dialog.show();
            }
        });


    }



    @Override
    public int getItemCount() {
        return ListTeam.getInstance().getListTeam().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTeamName, txtTeamAge;
        Button btnDelete, btnEdit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTeamAge = itemView.findViewById(R.id.txt_team_age);
            txtTeamName = itemView.findViewById(R.id.txt_team_name);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            btnEdit = itemView.findViewById(R.id.btn_edit);
        }
    }
}
