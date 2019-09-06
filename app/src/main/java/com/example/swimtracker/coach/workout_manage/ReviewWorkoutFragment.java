package com.example.swimtracker.coach.workout_manage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.swimtracker.R;
import com.example.swimtracker.user_manage.URLManage;
import com.example.swimtracker.user_manage.UserProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewWorkoutFragment extends Fragment {
    View view;
    TextView txtMain, txtSub, txtStart, txtRelax, txtWorkoutName, txtStartDescription, txtMainDescription, txtSubDescription, txtRelaxDescription;
    Button btnCancel, btnAddWorkout;
    RecyclerView recyclerView;
    CreateRecord workout = CreateRecord.getInstance();
    Exercise startExercise, subExercise, mainExercise, relaxedExercise;
    String URL_ADDWORKOUT = URLManage.getInstance().getMainURL() + "/workout/"+ UserProfile.getInstance().getUser().getUsername() + "/lesson/add";
    public ReviewWorkoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_review, container, false);
        init();
        action();
        return view;
    }

    private void action() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.layout_container, new WorkoutFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btnAddWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWorkout();
            }
        });

    }

    private void addWorkout() {
        JSONObject result = addToJSONObject();
        AndroidNetworking.post(URL_ADDWORKOUT)
                .addJSONObjectBody(result)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success")){
                                Workout workout = new Workout();
                                workout.getWorkoutFromJSONObject(response.getJSONObject("values"));
                                ListWorkout.getInstance().getListWorkout().add(workout);
                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.layout_container, new WorkoutFragment());
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    private JSONObject addToJSONObject(){

        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try {
            result.put("lesson_name", CreateRecord.getInstance().getWorkoutName());
            jsonArray.put(CreateRecord.getInstance().getStartExercise().ConvertToJSONObject());
            jsonArray.put(CreateRecord.getInstance().getMainExercise().ConvertToJSONObject());
            jsonArray.put(CreateRecord.getInstance().getSubExercise().ConvertToJSONObject());
            jsonArray.put(CreateRecord.getInstance().getRelaxExercise().ConvertToJSONObject());
            result.put("exercise",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void init(){
        //recyclerView = view.findViewById(R.id.recycler_view);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        AndroidNetworking.initialize(getContext());
        startExercise = workout.getStartExercise();
        subExercise = workout.getSubExercise();
        mainExercise = workout.getMainExercise();
        relaxedExercise = workout.getRelaxExercise();

        txtMain = view.findViewById(R.id.txt_main);
        txtStart = view.findViewById(R.id.txt_start);
        txtSub = view.findViewById(R.id.txt_sub);
        txtRelax = view.findViewById(R.id.txt_relax);
        txtWorkoutName = view.findViewById(R.id.txt_workout_name);
        txtStartDescription = view.findViewById(R.id.txt_start_description);
        txtMainDescription = view.findViewById(R.id.txt_main_description);
        txtSubDescription = view.findViewById(R.id.txt_sub_description);
        txtRelaxDescription = view.findViewById(R.id.txt_relax_description);


        txtMain.setText(mainExercise.getRepetition()+"x"+mainExercise.getDistance() + " " + mainExercise.getStyle());
        txtStart.setText(startExercise.getRepetition()+"x"+startExercise.getDistance() + " " + startExercise.getStyle());
        txtSub.setText(subExercise.getRepetition()+"x"+subExercise.getDistance() + " " + subExercise.getStyle());
        txtRelax.setText(relaxedExercise.getRepetition()+"x"+relaxedExercise.getDistance() + " " + relaxedExercise.getStyle());
        txtWorkoutName.setText(CreateRecord.getInstance().getWorkoutName());

        txtStartDescription.setText(startExercise.getDescription());
        txtMainDescription.setText(mainExercise.getDescription());
        txtSubDescription.setText(subExercise.getDescription());
        txtRelaxDescription.setText(relaxedExercise.getDescription());


        btnAddWorkout = view.findViewById(R.id.btn_add_workout);
        btnCancel = view.findViewById(R.id.btn_cancel);
    }

}
