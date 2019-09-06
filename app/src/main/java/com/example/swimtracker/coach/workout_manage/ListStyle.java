package com.example.swimtracker.coach.workout_manage;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ListStyle {
    private List<String> listStyle;
    private List<Integer> listIdStyle;
    private static ListStyle ourInstance;

    public static ListStyle getInstance() {
        if (ourInstance == null)
            ourInstance = new ListStyle();
        return ourInstance;
    }
    public static ListStyle newInstance(){
        ourInstance = new ListStyle();
        return ourInstance;
    }

    private ListStyle() {
        listStyle = new ArrayList<>();
        listIdStyle = new ArrayList<>();
    }

    public List<String> getListStyle(){
        return listStyle;
    }

    public List<Integer> getListIdStyle(){return listIdStyle;}

    public void addStyleFromJSONArray(JSONArray jsonArray){
        for(int i = 0 ; i < jsonArray.length(); i++) {
            try {
                listStyle.add(jsonArray.getJSONObject(i).getString("swim_name"));
                listIdStyle.add(jsonArray.getJSONObject(i).getInt("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
