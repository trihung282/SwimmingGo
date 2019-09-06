package com.example.swimtracker.coach.workout_manage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.swimtracker.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewFragment extends Fragment {
    View view;
    TextView txtMain, txtSub, txtStart, txtRelax;
    RecyclerView recyclerView;
    CreateRecord workout = CreateRecord.getInstance();
    Exercise startExercise, subExercise, mainExercise, relaxedExercise;
    public ReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_review, container, false);
        init();
        return view;
    }

    public void init(){
        //recyclerView = view.findViewById(R.id.recycler_view);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        startExercise = workout.getStartExercise();
        subExercise = workout.getSubExercise();
        mainExercise = workout.getMainExercise();
        relaxedExercise = workout.getRelaxExercise();

        txtMain = view.findViewById(R.id.txt_main);
        txtStart = view.findViewById(R.id.txt_start);
        txtSub = view.findViewById(R.id.txt_sub);
        txtRelax = view.findViewById(R.id.txt_relax);

        txtMain.setText(mainExercise.getRepetition()+"x"+mainExercise.getDistance() + " " + mainExercise.getStyle());
        txtStart.setText(startExercise.getRepetition()+"x"+startExercise.getDistance() + " " + startExercise.getStyle());
        txtSub.setText(subExercise.getRepetition()+"x"+subExercise.getDistance() + " " + subExercise.getStyle());
        txtRelax.setText(relaxedExercise.getRepetition()+"x"+relaxedExercise.getDistance() + " " + relaxedExercise.getStyle());

    }

}
