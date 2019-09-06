package com.example.swimtracker.coach.diary_manage;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class ListNote {
    public List<Note> getListNote() {
        return listNote;
    }

    public void setListNote(List<Note> listNote) {
        this.listNote = listNote;
    }

    private List<Note> listNote;
    private static ListNote ourInstance;

    public static ListNote newInstace(){
        ourInstance = new ListNote();
        return ourInstance;
    }
    public static ListNote getInstance() {
        if (ourInstance == null)
            ourInstance = new ListNote();
        return ourInstance;
    }

    private ListNote() {
        listNote = new ArrayList<>();
    }

    public JSONArray addToJSONArray(){
        JSONArray jsonArray = new JSONArray();
        for(int i = 0; i < listNote.size(); i++)
            jsonArray.put(listNote.get(i).addToJSONObject());
        return jsonArray;
    }
}
