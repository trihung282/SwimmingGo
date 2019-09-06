package com.example.swimtracker.coach.workout_manage;

import java.util.ArrayList;
import java.util.List;

public class ListRecord {
    private static  ListRecord ourInstance;
    private List<Record> listRecord;

    public static ListRecord getInstance() {
        if (ourInstance == null)
            ourInstance = new ListRecord();
        return ourInstance;
    }

    private ListRecord() {
        listRecord = new ArrayList<>();
    }

    public List<Record> getListRecord() {
        return listRecord;
    }

    public void setListRecord(List<Record> listRecord) {
        this.listRecord = listRecord;
    }

    public static ListRecord getOurInstance() {
        return ourInstance;
    }

    public static void setOurInstance(ListRecord ourInstance) {
        ListRecord.ourInstance = ourInstance;
    }
}
