package com.example.swimtracker.coach.workout_manage;

import com.example.swimtracker.coach.team_manage.Team;

public class CreateRecord {
    private Team team;
    private String  workoutName, date;

    public String getLessonID() {
        return lessonID;
    }

    public void setLessonID(String lessonID) {
        this.lessonID = lessonID;
    }

    private String lessonID;
    private Exercise startExercise, mainExercise, relaxExercise, subExercise;
    private static CreateRecord ourInstance;

    public static CreateRecord getInstance() {
        if (ourInstance == null)
            ourInstance = new CreateRecord();
        return ourInstance;
    }

    public static CreateRecord newInstance(){
        ourInstance = new CreateRecord();
        return ourInstance;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    private CreateRecord() {
        this.startExercise = new Exercise();
        this.mainExercise = new Exercise();
        this.relaxExercise = new Exercise();
        this.subExercise = new Exercise();
    }

    public Exercise getStartExercise() {
        return startExercise;
    }

    public Exercise getMainExercise() {
        return mainExercise;
    }


    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public void setStartExercise(Exercise startExercise) {
        this.startExercise = startExercise;
    }

    public void setMainExercise(Exercise mainExercise) {
        this.mainExercise = mainExercise;
    }

    public void setRelaxExercise(Exercise relaxExercise) {
        this.relaxExercise = relaxExercise;
    }

    public void setSubExercise(Exercise subExercise) {
        this.subExercise = subExercise;
    }


    public Exercise getRelaxExercise() {
        return relaxExercise;
    }

    public Exercise getSubExercise() {
        return subExercise;
    }
}
