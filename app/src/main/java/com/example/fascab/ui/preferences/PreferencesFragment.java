package com.example.fascab.ui.preferences;

import android.os.Binder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.fascab.R;

public class PreferencesFragment extends Fragment {

    private PreferencesViewModel preferencesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        LayoutInflater lf = getActivity().getLayoutInflater();

        View root = lf.inflate(R.layout.fragment_preferences, container, false);

        return root ;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
         Button b1 = getActivity().findViewById(R.id.rideNow);
         b1.setVisibility(View.GONE);
         Button b2 = getActivity().findViewById(R.id.later);
         b2.setVisibility(View.GONE);

        Spinner spinner = getActivity().findViewById(R.id.musicGenre);
        ArrayAdapter<CharSequence> adapter =  ArrayAdapter.createFromResource(getActivity(),R.array.musicGenre,R.layout.spinner_item);
        spinner.setAdapter(adapter);

    }

}