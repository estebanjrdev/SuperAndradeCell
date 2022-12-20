package com.servicios.andrade.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.servicios.andrade.R;
import com.servicios.andrade.model.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    private EditText mUsername,mPasswordView;
    private Button login;
    private  String username,password;
    JsonObjectRequest jsonObjectRequest;
    StringRequest stringRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnLogin = findViewById(R.id.signin_button);
        mUsername = findViewById(R.id.user);
        mPasswordView = findViewById(R.id.password);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Guardando...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this, InicioActivity.class);
                startActivity(intent);
                finish();
              /*  username = mUsername.getText().toString();
                password = mPasswordView.getText().toString();
                if(!username.equals("") && !password.equals("")) {
                  progressDialog.create();
                    login("http://andrade.125mb.com/login.php", username, password);
                }else {
                    Toast.makeText(MainActivity.this, "No puede dejar campos vacios", Toast.LENGTH_SHORT);
                }*/
            }

        });
    }
    private void login (String url,String username,String password) {

        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Log.i("tagconvertstr", "["+response.toString()+"]");
                Usuario miUsuario = new Usuario();
                Boolean logeado = null;
                String msj = "";
                JSONArray json = response.optJSONArray("user");
                JSONObject jsonObject = null;
                try {
                    jsonObject = json.getJSONObject(0);
                    Log.i("tagconvertstr", "["+json.getJSONObject(0).toString()+"]");
                    logeado = jsonObject.optBoolean("success");
                    msj = jsonObject.optString("message");
                    miUsuario.setId(Integer.toString(jsonObject.optInt("id")));
                    miUsuario.setUsuario(jsonObject.optString("usuario"));
                    miUsuario.setRoll(jsonObject.optString("roll"));
                    miUsuario.setIslogin(logeado);
                } catch (JSONException e) {
                    Log.i("tagconvertstr", "" + e.toString());
                }
                if (logeado) {
                    progressDialog.dismiss();
                    if(miUsuario.getRoll().equals("1")){
                    Toast.makeText(MainActivity.this, "Sessión Iniciada", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, InicioActivity.class);
                    startActivity(intent);
                    finish();
                    } else {
                        Toast.makeText(MainActivity.this, miUsuario.getRoll(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, msj, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("tagconvertstr", "" + error.toString());
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("username", username);
                parametros.put("password", password);
                return parametros;
            }
        };


        //   VolleySingleton.getIntanciaVolley(LoginActivity.this).addToRequestQueue(jsonObjectRequest);
        RequestQueue rq = Volley.newRequestQueue(MainActivity.this);
        rq.add(jsonObjectRequest);
    }

}