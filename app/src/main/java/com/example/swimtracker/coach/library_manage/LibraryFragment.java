package com.example.swimtracker.coach.library_manage;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.swimtracker.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class LibraryFragment extends Fragment {
    View view;
    ImageButton btnFreeStyle;
    ImageButton btnButterflyStyle;
    ImageButton btnBreastStyle;
    ImageButton btnBackStyle;


    public LibraryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_library, container, false);
        btnFreeStyle = (ImageButton) view.findViewById(R.id.btn_freestyle);
        btnFreeStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), FreeStyleVideo.class);
                startActivity(intent);
            }
        });

        btnButterflyStyle = (ImageButton) view.findViewById(R.id.btn_butterfly);
        btnButterflyStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), ButterflyStyleVideo.class);
                startActivity(intent);
            }
        });

        btnBreastStyle = (ImageButton) view.findViewById(R.id.btn_breast);
        btnBreastStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LibraryFragment.this.getActivity(), BreastStyleVideo.class);
                LibraryFragment.this.startActivity(intent);
            }
        });

        btnBackStyle = (ImageButton) view.findViewById(R.id.btn_back);
        btnBackStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LibraryFragment.this.getActivity(), BackStyleVideo.class);
                LibraryFragment.this.startActivity(intent);
            }
        });

        return view;
    }

}
