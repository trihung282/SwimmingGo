package com.example.swimtracker.coach.workout_manage;

import org.json.JSONException;
import org.json.JSONObject;

public class Workout {
    private String workoutName, date, teamName;
    private int coachID, workoutID, checkRecord;
    private Exercise startExercise, MainExercise, subExercise, RelaxExercise;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Workout(String workoutName, String date, int coachID, int workoutID) {
        this.workoutName = workoutName;
        this.date = date;
        this.coachID = coachID;
        this.workoutID = workoutID;
    }

    public Exercise getStartExercise() {
        return startExercise;
    }

    public void setStartExercise(Exercise startExercise) {
        this.startExercise = startExercise;
    }

    public Workout(){
        startExercise = new Exercise();
        MainExercise = new Exercise();
        subExercise = new Exercise();
        RelaxExercise = new Exercise();
    }

    public int getWorkoutID() {
        return workoutID;
    }

    public void setWorkoutID(int workoutID) {
        this.workoutID = workoutID;
    }

    public int getCheckRecord() {
        return checkRecord;
    }

    public void setCheckRecord(int checkRecord) {
        this.checkRecord = checkRecord;
    }

    public int getCoachID() {
        return coachID;
    }

    public void setCoachID(int coachID) {
        this.coachID = coachID;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void getWorkoutFromJSONObject(JSONObject jsonObject){
        try {
            this.workoutName = jsonObject.getString("lesson_name");
            this.workoutID = jsonObject.getInt("lesson_id");
            this.teamName = jsonObject.getString("team_name");
            this.date = jsonObject.getString("date");
            this.checkRecord = jsonObject.getInt("check_record");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
