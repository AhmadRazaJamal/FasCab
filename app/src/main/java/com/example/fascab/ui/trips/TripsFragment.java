package com.example.fascab.ui.trips;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.fascab.R;

public class TripsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_trips, container, false);

        return root;
    }

    // When fragment for trips is displaying on the home page, the buttons are hidden away

    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        Button b1 = getActivity().findViewById(R.id.rideNow);
        b1.setVisibility(View.GONE);
        Button b2 = getActivity().findViewById(R.id.later);
        b2.setVisibility(View.GONE);
        getActivity().findViewById(R.id.to_txt).setVisibility(View.GONE);
        getActivity().findViewById(R.id.destination_entry).setVisibility(View.GONE);

    }
    
}