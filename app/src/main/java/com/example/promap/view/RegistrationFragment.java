package com.example.promap.view;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.promap.R;
import com.example.promap.databinding.FragmentRegistrationBinding;

public class RegistrationFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private FragmentRegistrationBinding binding;
    private FragmentActivity activity;
    private View view;
    private Spinner spinner;
    private Navigator navigator=new Navigator();
    Button loginBtn;
    Button registerBtn;
    String role;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    String[] roles = {"ECNEC", "MOP", "EXEC", "APP"};

    public RegistrationFragment() {
        // Required empty public constructor
    }

    public static RegistrationFragment newInstance(String param1, String param2) {
        RegistrationFragment fragment = new RegistrationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = requireActivity();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_registration, container, false);
        view = binding.getRoot();

        spinner = view.findViewById(R.id.spinner2);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter ad = new ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, roles);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registerBtn = view.findViewById(R.id.registration_btn);
        loginBtn = view.findViewById(R.id.registration_login_btn);

        // On login button click navigate to login page
        loginBtn.setOnClickListener(View-> navigator.navToLogin(activity));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(requireContext(), roles[position], Toast.LENGTH_LONG)
                .show();
        role = roles[position];
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}