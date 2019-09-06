package com.example.swimtracker.coach.workout_manage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.example.swimtracker.R;
import com.example.swimtracker.user_manage.URLManage;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RelaxedWorkoutFragment extends Fragment {
    View view;
    Button btnRepsMinus, btnRepsPlus, btnDistanceMinus, btnDistancePlus, btnReview, btnAddWorkout, btnCancel, btnBack;
    Spinner spnStyle;
    TextView txtDistance;
    EditText edtReps, edtWorkoutName, edtDescription;
    List<String> listTeamName = new ArrayList<>();
    List<Integer> listDistance = ListDistance.getInstance().getListDistance();
    List<String> listStyle = ListStyle.getInstance().getListStyle();
    ArrayAdapter<String> styleAdapter;
    Exercise relaxedExercise = CreateRecord.getInstance().getRelaxExercise();
    String URL_DISTANCE = URLManage.getInstance().getMainURL() + "/public/distance";
    String URL_TYPE = URLManage.getInstance().getMainURL() + "/public/style";

    public RelaxedWorkoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_workout_relaxed, container, false);
        init();
        action();
        return view;
    }

    public void init() {
        AndroidNetworking.initialize(getContext());
        spnStyle = view.findViewById(R.id.spn_style);

        btnRepsPlus = view.findViewById(R.id.btn_reps_plus);
        btnRepsMinus = view.findViewById(R.id.btn_reps_minus);
        btnDistancePlus = view.findViewById(R.id.btn_distance_plus);
        btnDistanceMinus = view.findViewById(R.id.btn_distance_minus);
        btnCancel = view.findViewById(R.id.btn_cancel);
        btnAddWorkout = view.findViewById(R.id.btn_add_workout);
        btnReview = view.findViewById(R.id.btn_review);
        btnBack = view.findViewById(R.id.btn_back);

        edtReps = view.findViewById(R.id.edt_rep);
        edtWorkoutName = view.findViewById(R.id.edt_workout_name);
        edtWorkoutName.setText(CreateRecord.getInstance().getWorkoutName());
        edtDescription = view.findViewById(R.id.edt_description);

        txtDistance = view.findViewById(R.id.txt_distance);
        txtDistance.setText(listDistance.get(0)+"");

        loadData();

        styleAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,listStyle);
        styleAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnStyle.setAdapter(styleAdapter);
    }

    private void loadData() {
//        loadListDistance();
//        loadStyle();
    }

    public void action() {
        edtReps.setText("1");
        btnRepsMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(edtReps.getText().toString()) > 0)
                    edtReps.setText("" + (Integer.parseInt(edtReps.getText().toString()) - 1));
                else {
                    edtReps.setText("" + 0);
                }
            }
        });

        btnRepsPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(edtReps.getText().toString()) < 50)
                    edtReps.setText("" + (Integer.parseInt(edtReps.getText().toString()) + 1));
                else {
                    edtReps.setText("50");
                }
            }
        });

        btnDistanceMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = listDistance.indexOf(Integer.parseInt(txtDistance.getText().toString()));
                Log.e("hungpro",""+index);
                if (index > 0){
                    txtDistance.setText(""+(listDistance.get(index - 1)));
                }
                else
                    txtDistance.setText(""+Integer.parseInt(txtDistance.getText().toString()));
            }
        });
        btnDistancePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = listDistance.indexOf(Integer.parseInt(txtDistance.getText().toString()));
                Log.e("hungpro",""+index);
                if (index < listDistance.size()-1){
                    txtDistance.setText(""+(listDistance.get(index + 1)));
                }

            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.layout_container, new WorkoutFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.layout_workout, new MainWorkoutFragment());
//                transaction.addToBackStack(null);
//                transaction.commit();
//            }
//        });

        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateRecord.getInstance().setWorkoutName(edtWorkoutName.getText().toString());
                relaxedExercise.setRepetition(Integer.parseInt(edtReps.getText().toString()));
                relaxedExercise.setDistance(Integer.parseInt(txtDistance.getText().toString()));
                relaxedExercise.setStyle(spnStyle.getSelectedItem().toString());
                relaxedExercise.setDescription(edtDescription.getText().toString());
                relaxedExercise.setType("Bài tập chính");
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.layout_container, new ReviewWorkoutFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    }

}
