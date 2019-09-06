package com.example.swimtracker.coach.workout_manage;

import com.example.swimtracker.user_manage.Swimmer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Record {
    private  Swimmer swimmer;
    private int minute, sec, millisec;

    public Record(){
        millisec = minute = sec = 0;
    }

    public Swimmer getSwimmer() {
        return swimmer;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSec() {
        return sec;
    }

    public void setSec(int sec) {
        this.sec = sec;
    }

    public int getMillisec() {
        return millisec;
    }

    public void setMillisec(int millisec) {
        this.millisec = millisec;
    }

    public void setSwimmer(Swimmer swimmer) {
        this.swimmer = swimmer;
    }

    public JSONObject addToJSONObject(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", swimmer.getId());
            jsonObject.put("millisec", millisec);
            jsonObject.put("min", minute);
            jsonObject.put("sec", sec);
            jsonObject.put("heart_beat_id",1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
