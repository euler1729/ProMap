package com.example.promap.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.promap.R;
import com.example.promap.databinding.FragmentLoginBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LoginFragment extends Fragment {
    private Navigator navigator = new Navigator();
    private FragmentLoginBinding binding;
    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button loginBtn = view.findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(v->{
            BottomNavigationView navView =(BottomNavigationView) requireActivity().findViewById(R.id.bottomNavBar);
            navView.setVisibility(View.VISIBLE);
            navigator.navToDashboard(requireActivity());
        });
        Button logToReg = view.findViewById(R.id.login_register_btn);
        logToReg.setOnClickListener(v->{
            navigator.navToRegister(requireActivity());
        });

    }
}