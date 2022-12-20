package com.example.promap.view;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.promap.R;

public class Navigator {
    private Fragment fragment;

    public void navToDashboard(FragmentActivity activity){
        fragment = new DashboardFragment();
        commit(activity,fragment);
    }
    public void navToMapView(FragmentActivity activity){
        fragment = new MapFragment();
        commit(activity,fragment);
    }
    public void navToRegister(FragmentActivity activity){
        fragment = new RegistrationFragment();
        commit(activity,fragment);
    }
    public void navToLogin(FragmentActivity activity){
        fragment = new LoginFragment();
        commit(activity,fragment);
    }

    private void commit(FragmentActivity activity, Fragment fragment){
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.id_fragment_controller, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
