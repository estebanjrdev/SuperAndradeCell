package com.servicios.andrade.view.fragments;

import android.os.Bundle;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import com.servicios.andrade.R;

public class Fragment_Inicio extends Fragment {

    View vista;
    public Fragment_Inicio() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_inicio, container, false);

        return vista;
    }
}