package com.example.swimtracker.coach.team_manage;

public class Team {
    private int teamID;
    private String teamName;
    private String teamAge;

    public Team() {
        this.teamID = 0;
        this.teamAge = this.teamName = "";
    }

    public Team(String teamName, String teamAge, int teamID) {
        this.teamID = teamID;
        this.teamName = teamName;
        this.teamAge = teamAge;

    }

    public int getTeamID() {
        return teamID;
    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamAge() {
        return teamAge;
    }

    public void setTeamAge(String teamAge) {
        this.teamAge = teamAge;
    }


}
