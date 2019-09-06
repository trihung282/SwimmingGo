package com.example.swimtracker.user_manage;

import org.json.JSONException;
import org.json.JSONObject;

public class Swimmer extends User {
    private String parent_name, parent_phone;
    private float weight, height;

    public Swimmer(){
        super();
    }
    public Swimmer(String username, String address, String dob, String email, String first_name, String last_name, String phone, int gender, int id, int role_id, float height, float weight, String parent_name, String parent_phone) {
        super(username, address, dob, email, first_name, last_name, phone, gender, id, role_id);
        this.weight = weight;
        this.height = height;
        this.parent_name = parent_name;
        this.parent_phone = parent_phone;
    }

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    public String getParent_phone() {
        return parent_phone;
    }

    public void setParent_phone(String parent_phone) {
        this.parent_phone = parent_phone;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    @Override
    public void addDataFromJSONObject(JSONObject jsonObject) {
        try {
            super.setUser(jsonObject.getString("address"),
                    jsonObject.getString("dob"),
                    jsonObject.getString("email"),
                    jsonObject.getString("first_name"),
                    jsonObject.getString("last_name"),
                    jsonObject.getString("phone"),
                    jsonObject.getInt("gender"),
                    jsonObject.getInt("id"),
                    jsonObject.getInt("role_id")
                    );
            setHeight((float)jsonObject.getDouble("height"));
            setWeight((float)jsonObject.getDouble("weight"));
            setParent_name(jsonObject.getString("parent_name"));
            setParent_phone(jsonObject.getString("parent_phone"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject convertToJSONObject() {
        return null;
    }

    public void getSwimmerFromJSONObject(JSONObject jsonObject){
        try{
            super.setDob(jsonObject.getString("dob"));
            super.setFirst_name(jsonObject.getString("first_name"));
            super.setLast_name(jsonObject.getString("last_name"));
            super.setId(jsonObject.getInt("user_id"));
            super.setUsername(jsonObject.getString("username"));
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
