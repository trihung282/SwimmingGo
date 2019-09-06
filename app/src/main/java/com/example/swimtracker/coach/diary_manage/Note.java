package com.example.swimtracker.coach.diary_manage;

import com.example.swimtracker.user_manage.Swimmer;

import org.json.JSONException;
import org.json.JSONObject;

public class Note {
    private Swimmer swimmer;

    private String note, rank;

    public Note(){
        note = "";
        rank = "Giỏi";
        swimmer = new Swimmer();
    }

    public Swimmer getSwimmer() {
        return swimmer;
    }

    public void setSwimmer(Swimmer swimmer) {
        this.swimmer = swimmer;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getRank() {
        if (rank.equals("null"))
            return "Chưa xếp loại";
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public JSONObject addToJSONObject(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id",swimmer.getId());
            jsonObject.put("note",note);
            jsonObject.put("rank_id", getIdRank());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    public int getIdRank(){
        if (rank.equals("Giỏi"))
            return 1;
        if (rank.equals("Khá"))
            return 2;
        return 3;
    }

}
