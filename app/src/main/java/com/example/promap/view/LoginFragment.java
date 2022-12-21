package com.example.promap.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.promap.R;
import com.example.promap.databinding.FragmentLoginBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
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


        try {
            // Send a GET request to the API endpoint
            URL url = new URL("https://44d7-103-221-253-171.in.ngrok.io/projects");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Read the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parse the JSON response
            JSONObject json = new JSONObject(response.toString());
            Toast.makeText(requireContext(), json.toString(), Toast.LENGTH_SHORT).show();
            Log.d("test12", json.toString());

            // Do something with the JSON data...
        } catch (Exception e) {
            e.printStackTrace();
        }



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