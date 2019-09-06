package com.example.swimtracker.coach.team_manage;

import com.example.swimtracker.user_manage.Swimmer;

import java.util.ArrayList;
import java.util.List;

public class ListSwimmer {

    private List<Swimmer> listSwimmer;
    private static ListSwimmer ourInstance;

    public static ListSwimmer getInstance() {
        if (ourInstance == null)
            ourInstance = new ListSwimmer();
        return ourInstance;
    }



    public static ListSwimmer newInstance(){
        ourInstance = new ListSwimmer();
        return ourInstance;
    }

    public void addSwimmer(Swimmer swimmer){
        listSwimmer.add(swimmer);
    }

    public void removeSwimmer(int position){
        listSwimmer.remove(position);
    }

    public Swimmer getSwimmer(int position){
        return listSwimmer.get(position);
    }
    public List<Swimmer> getListSwimmer(){
        return listSwimmer;
    }
    private ListSwimmer() {
        listSwimmer = new ArrayList<>();
    }
}
