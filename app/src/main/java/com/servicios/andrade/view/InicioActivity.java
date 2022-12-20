package com.servicios.andrade.view;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.servicios.andrade.R;
import com.servicios.andrade.model.Usuario;
import com.servicios.andrade.view.fragments.Fragment_Inicio;
import com.servicios.andrade.view.fragments.Fragment_Register;

import androidx.drawerlayout.widget.DrawerLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InicioActivity extends AppCompatActivity {

    private Toolbar appbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;
    boolean fragmentTransaction = false;
    Fragment fragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        appbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(appbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setFragment();
        navView = (NavigationView) findViewById(R.id.navview);
        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem arg0) {
                        switch (arg0.getItemId()) {
                            case R.id.menu_ingresados:
                                fragment = new Fragment_Inicio();
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_usuarios:
                                fragment = new Fragment_Register();
                                fragmentTransaction = true;
                                break;
                        }

                        if (fragmentTransaction) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.content_frame, fragment)
                                    .commit();

                            arg0.setChecked(true);
                            getSupportActionBar().setTitle(arg0.getTitle());
                        }

                        drawerLayout.closeDrawers();
                        return true;
                    }

                });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem arg0) {
        switch (arg0.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.changePass:
                AlertDialog.Builder build = new AlertDialog.Builder(InicioActivity.this);
                build.setTitle("Cambiar Contraseña");
                LayoutInflater inflater = InicioActivity.this.getLayoutInflater();
                View v = inflater.inflate(R.layout.change_password, null);
                build.setView(v);
                build.show();
                return true;
            case R.id.logout:
                InicioActivity.this.finish();
                return true;

        }
        return super.onOptionsItemSelected(arg0);
    }

    public void setFragment() {

        Fragment f = new Fragment_Inicio();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, f)
                .commit();

    }
}