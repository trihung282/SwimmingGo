package com.example.swimtracker.coach.workout_manage;

import java.util.ArrayList;
import java.util.List;

public class DataManage {
    private List<Integer> listDistance;
    private List<String> listStyle;
    private static DataManage ourInstance;

    public static DataManage getInstance() {
        if (ourInstance == null)
            ourInstance = new DataManage();
        return ourInstance;
    }

    private DataManage() {
        listStyle = new ArrayList<>();
        listDistance = new ArrayList<>();
    }

    public List<String> getListStyle(){
        return listStyle;
    }

    public List<Integer> getListDistance(){
        return listDistance;
    }
}
