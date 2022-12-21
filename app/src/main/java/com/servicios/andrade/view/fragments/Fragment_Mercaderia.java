package com.servicios.andrade.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.servicios.andrade.R;
import com.servicios.andrade.util.Captura;

public class Fragment_Mercaderia extends Fragment {
    Button btnMercaderia;
    EditText txtQR;
    Spinner spinner;
    ImageButton qr;
    View vista;

    public Fragment_Mercaderia() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_mercaderia, container, false);
        spinner = vista.findViewById(R.id.spinnerEstado);
        txtQR = vista.findViewById(R.id.codigoQR);
        qr = vista.findViewById(R.id.btnQR);
        String[] valores = {"Bueno", "Dañado"};
        spinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, valores));

        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanFromFragment();
            }
        });
        return vista;
    }

    public void scanFromFragment() {
        IntentIntegrator.forSupportFragment(this)
                .setPrompt("Escanear el código, Vol + para activar flash")
                .setBeepEnabled(false)
                .setCaptureActivity(Captura.class)
                .initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                //Log.d("FragmentB", "Cancelled scan");
                Toast.makeText(getContext(), "Cancelado", Toast.LENGTH_LONG).show();
            } else {
                //Log.d("FragmentB", "Scanned");
                txtQR.setText("");
                txtQR.setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}