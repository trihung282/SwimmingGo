package com.example.swimtracker.coach.team_manage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListTeam {

    private List<Team> listTeam;
    private static ListTeam instance;

    public ListTeam() {
        listTeam = new ArrayList<>();
    }

    public static ListTeam getInstance() {
        if (instance == null)
            instance = new ListTeam();
        return instance;
    }

    public static ListTeam newInstance() {
        instance = new ListTeam();
        return instance;
    }

    public List<Team> getListTeam() {
        return listTeam;
    }

    public void addTeam(Team team) {
        listTeam.add(team);
    }

    public Team getTeam(int position) {
        return listTeam.get(position);
    }

    public void removeTeam(int position) {
        listTeam.remove(position);
    }

    public void setTeam(int position, Team team) {
        listTeam.set(position, team);
    }

    public void addTeamFromJSONObject(JSONObject jsonObject) {

        try {
            String teamName = jsonObject.getString("name");
            String age = jsonObject.getString("age");
            Team team = new Team(teamName, age, jsonObject.getInt("team_id"));
            listTeam.add(team);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
