package com.example.swimtracker.coach.workout_manage;

import java.util.ArrayList;
import java.util.List;

public class ListWorkout {
    List<Workout> listWorkout;

    private static ListWorkout ourInstance;

    public static ListWorkout getInstance() {
        if (ourInstance == null)
            ourInstance = new ListWorkout();
        return ourInstance;
    }
    public static ListWorkout newInstance(){
        ourInstance =  new ListWorkout();
        return ourInstance;
    }
    private ListWorkout() {
        listWorkout = new ArrayList<>();
    }

    public List<Workout> getListWorkout(){
        return listWorkout;
    }
}
