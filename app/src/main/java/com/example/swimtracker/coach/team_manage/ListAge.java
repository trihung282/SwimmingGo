package com.example.swimtracker.coach.team_manage;

import java.util.ArrayList;
import java.util.List;

public class ListAge {
    private List<String> listAge;
    private static ListAge ourInstance;

    public static ListAge getInstance() {
        if (ourInstance == null)
            ourInstance = new ListAge();
        return ourInstance;
    }
    public static ListAge newInstace(){
        ourInstance = new ListAge();
        return ourInstance;
    }
    private ListAge() {
        listAge = new ArrayList<>();
    }

    public List<String> getListAge(){return listAge;}
}
