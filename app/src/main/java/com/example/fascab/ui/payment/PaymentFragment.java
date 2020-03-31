package com.example.fascab.ui.payment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.fascab.R;

public class PaymentFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_payment, container, false);

        return root;
    }

    // When fragment for payments is displaying on the home page, the buttons are hidden away

    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        Button b1 = getActivity().findViewById(R.id.rideNow);
        b1.setVisibility(View.GONE);
        Button b2 = getActivity().findViewById(R.id.later);
        b2.setVisibility(View.GONE);

    }
}
