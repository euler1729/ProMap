package com.example.promap.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.promap.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity{
    public static Menu menu;
    Fragment selectedFragment;
    Navigator navigator = new Navigator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavBar);

        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }

        SharedPreferences preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        String token = preferences.getString("token",null);
        if(token==null){
            bottomNavigationView.setVisibility(View.INVISIBLE);
            getSupportFragmentManager().beginTransaction().replace(R.id.id_fragment_controller,new LoginFragment()).commit();
        }
        else{
            getSupportFragmentManager().beginTransaction().replace(R.id.id_fragment_controller,
                    new DashboardFragment()).commit();
        }
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.dashboardFragment:
                    selectedFragment = new DashboardFragment();
                    break;
                case R.id.mapFragment:
                    selectedFragment = new MapFragment();
                    break;
                case R.id.accountFragment:
                    selectedFragment = new AccountFragment();
                    break;
                default:
                    selectedFragment = null;
                    break;
            }
            assert selectedFragment != null;
            getSupportFragmentManager().beginTransaction().replace(R.id.id_fragment_controller,
                    selectedFragment).commit();
            return true;
        });
    }
}