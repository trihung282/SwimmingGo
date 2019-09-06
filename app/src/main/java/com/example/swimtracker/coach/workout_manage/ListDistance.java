package com.example.swimtracker.coach.workout_manage;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ListDistance {
    private List<Integer> listDistance;
    private List<Integer> listIdDistance;
    private static ListDistance ourInstance;

    public static ListDistance getInstance() {
        if (ourInstance == null)
            ourInstance = new ListDistance();
        return ourInstance;
    }

    public ListDistance() {
        listDistance = new ArrayList<>();
        listIdDistance = new ArrayList<>();
    }

    public static ListDistance newInstance() {
        ourInstance = new ListDistance();
        return ourInstance;
    }

    public List<Integer> getListDistance(){
        return listDistance;
    }

    public void addDistance(Integer distance){
        listDistance.add(distance);
    }

    public void addDistanceFromJSONArray(JSONArray jsonArray){
        Log.d("listdistance","success");
        for(int i = 0 ; i < jsonArray.length(); i++) {
            try {
                listDistance.add(jsonArray.getJSONObject(i).getInt("swim_distance"));
                listIdDistance.add(jsonArray.getJSONObject(i).getInt("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d("listdistance",listDistance.size()+"");
    }
}
