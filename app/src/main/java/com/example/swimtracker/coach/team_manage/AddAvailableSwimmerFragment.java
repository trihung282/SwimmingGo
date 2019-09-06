package com.example.swimtracker.coach.team_manage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swimtracker.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddAvailableSwimmerFragment extends Fragment {


    public AddAvailableSwimmerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_available_swimmer, container, false);
    }

}
