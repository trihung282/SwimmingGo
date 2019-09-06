package com.example.swimtracker.coach.workout_manage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.androidnetworking.AndroidNetworking;
import com.example.swimtracker.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddWorkoutFragment extends Fragment {
    View view;
    EditText edtWorkoutName;
    List<String> listTeamName = new ArrayList<>();
    public AddWorkoutFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_workout, container, false);
        init();
        return view;
    }

    public void init(){
        AndroidNetworking.initialize(getContext());
        loadFragment();
        loadData();
    }

    public void loadData(){

    }


    public void loadFragment(){
//        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.layout_workout, new StartWorkoutFragment());
//        transaction.addToBackStack(null);
//        transaction.commit();
    }

}
