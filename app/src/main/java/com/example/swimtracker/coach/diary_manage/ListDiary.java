package com.example.swimtracker.coach.diary_manage;

import android.os.Bundle;

import java.util.List;

public class ListDiary {
    List<Diary> listDiary;
    private static ListDiary ourInstance;

    public static ListDiary getInstance() {
        if (ourInstance == null)
            ourInstance = new ListDiary();
        return ourInstance;
    }

    public static ListDiary newInstance() {
        ourInstance = new ListDiary();
        return ourInstance;
    }

    public List<Diary> getListDiary() {
        return listDiary;
    }

    public void setListDiary(List<Diary> listDiary) {
        this.listDiary = listDiary;
    }

    private ListDiary() {
    }
}
