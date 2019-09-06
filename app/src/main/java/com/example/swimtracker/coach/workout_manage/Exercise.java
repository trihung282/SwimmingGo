package com.example.swimtracker.coach.workout_manage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Exercise {
    private int id, repetition,distance, style_id, distance_id, type_id;
    private String style, description, type;

    public Exercise(int id, String style, int distance, int repetition, String description, String type) {
        setId(id);
        setStyle(style);
        setDistance(distance);
        setRepetition(repetition);
        setDescription(description);
        setType(type);
        setStyle_id();
        setDistance_id();
    }

    public JSONObject ConvertToJSONObject(){

        JSONObject result = new JSONObject();
        try {
            result.put("style", style);
            result.put("distance", distance);
            result.put("repetition", repetition);
            result.put("description",description);
            result.put("type_id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int getStyle_id() {
        return style_id;
    }

    public void setStyle_id() {
        List<Integer> listStyleID = ListStyle.getInstance().getListIdStyle();
        this.style_id = listStyleID.get(ListStyle.getInstance().getListStyle().indexOf(style));
    }

    public int getDistance_id() {
        return distance_id;
    }

    public void setDistance_id() {
        this.distance_id = ListDistance.getInstance().getListDistance().indexOf(distance);
    }

    public Exercise(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getRepetition() {
        return repetition;
    }

    public void setRepetition(int repetition) {
        this.repetition = repetition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }
}
