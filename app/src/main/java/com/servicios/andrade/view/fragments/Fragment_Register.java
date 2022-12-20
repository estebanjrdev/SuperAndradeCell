package com.servicios.andrade.view.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.servicios.andrade.R;
import com.servicios.andrade.util.VolleySingleton;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fragment_Register extends Fragment {
    ProgressDialog progressDialog;
    EditText txtNombre, txtUsuario, txtPhone, txtCorreo, txtPassword, txtCedula;
    Button btnRegister;
    Spinner spinner;
    View vista;
    String spinnerSelected;

    public Fragment_Register() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_register, container, false);
        spinner = vista.findViewById(R.id.spinner);
        String[] valores = {"Seleccionar Rol", "Gerente", "Empleado"};
        spinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, valores));
        txtNombre = vista.findViewById(R.id.nombre_apellido);
        txtUsuario = vista.findViewById(R.id.user);
        txtCedula = vista.findViewById(R.id.cedula);
        txtPhone = vista.findViewById(R.id.celular);
        txtCorreo = vista.findViewById(R.id.correo);
        txtPassword = vista.findViewById(R.id.password);
        btnRegister = vista.findViewById(R.id.signin_button);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Guardando...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                spinnerSelected = (String) adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final   String  strNombre = txtNombre.getText().toString();
                final  String  strUsername = txtUsuario.getText().toString();
                final  String  strPhone = txtPhone.getText().toString();
                final String  strCorreo = txtCorreo.getText().toString();
                final String strPassword = txtPassword.getText().toString();
                final String strCedula = txtCedula.getText().toString();
                if(strNombre.equals("") || strUsername.equals("") || strPhone.equals("") || strCorreo.equals("") || strPassword.equals("")){
                    Toast.makeText(getContext(), "No puede dejar Campos Vacios", Toast.LENGTH_SHORT).show();
                }else if(!isValidUsername(strUsername)){
                    Toast.makeText(getContext(), "Usuario incorrecto", Toast.LENGTH_SHORT).show();
                }else if(!isValidPhone(strPhone)){
                    Toast.makeText(getContext(), "Teléfono incorrecto", Toast.LENGTH_SHORT).show();
                }else if (!isValidEmail(strCorreo)){
                    Toast.makeText(getContext(), "Email incorrecto", Toast.LENGTH_SHORT).show();
                }else if(!isValidPassword(strPassword)){
                    Toast.makeText(getContext(), "Contraseña debe tener mínimo 6 caracteres", Toast.LENGTH_SHORT).show();
                }else if(!isValidRoll(spinnerSelected)){
                    Toast.makeText(getContext(), "Debe seleccionar el rol", Toast.LENGTH_SHORT).show();
                }else{
                  progressDialog.create();
                    insertarUsuario("http://andrade.125mb.com/signup.php",strNombre,strUsername,strPhone,strCorreo,strCedula,numRoll(spinnerSelected),strPassword);

                }
            }
        });
        return vista;
    }

    private void insertarUsuario(String url, String nombre, String usuario, String telefono, String correo, String cedula, String roll, String password) {
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (s.equals("Sign Up Success")) {
                   // progressDialog.dismiss();
                    txtNombre.setText("");
                    txtCedula.setText("");
                    txtCorreo.setText("");
                    txtPassword.setText("");
                    txtPhone.setText("");
                    txtUsuario.setText("");
                    Toast.makeText(getContext(), "Registro exitoso", Toast.LENGTH_SHORT).show();
                } else {
                   System.out.println(s);
                    Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), volleyError.toString(), Toast.LENGTH_SHORT);
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("correo", correo);
                parametros.put("celular", telefono);
                parametros.put("roll", roll);
                parametros.put("cedula", cedula);
                parametros.put("password", password);
                parametros.put("nombre_apellidos", nombre);
                parametros.put("usuario", usuario);
                return parametros;
            }
        };
        sr.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
       // VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(sr);
        RequestQueue rq = Volley.newRequestQueue(getContext());
        rq.add(sr);
    }

    private boolean isValidUsername(String username) {
        String regex = "^[A-Za-z]\\w{5,29}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(username);
        return m.matches();
    }

    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    private boolean isValidPassword(String password) {
        return password.length() > 5;
    }

    private boolean isValidPhone(String phone) {
        return phone.length() == 8;
    }
    private boolean isValidRoll(String roll) {
        return !roll.equals("Seleccionar Rol");
    }
    private String numRoll(String roll) {
       if (roll.equals("Gerente")){
           return "1";
       }else {
           return "2";
       }
    }
}