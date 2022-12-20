package com.example.promap.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.promap.R;
import com.example.promap.databinding.FragmentMapBinding;
import com.example.promap.model.Projects;
import com.example.promap.viewmodel.MapViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private FragmentMapBinding binding;
    private FragmentActivity activity;
    private View view;
    private GoogleMap map;
    private MapViewModel viewModel;
    SupportMapFragment mapFragment;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public MapFragment() {
    }

    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = requireActivity();
        activity.setTitle("Map View");
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_map, container,false);
        view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MapViewModel.class);
        try {
            viewModel.getData(requireContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        observeViewModel();
    }
    private void observeViewModel(){
        viewModel.projectsLiveData.observe(getViewLifecycleOwner(),projects -> {
            mapFragment.getMapAsync(googleMap -> {
                map = googleMap;
                int i = 0;
                for(Projects p:projects){
                    for(LatLng lt:p.getLocation_coordinates()){
                        map.addMarker(new MarkerOptions()
                                .title(p.getProject_name())
                                .position(lt)).setTag(i);
                    }
                    ++i;
                }
                map.setOnMarkerClickListener(marker -> {
                    Toast.makeText(activity,marker.getTitle(), Toast.LENGTH_SHORT).show();
                    return false;
                });
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(projects.get(0).getLatLng(0), 15 ));
                map.setOnMarkerClickListener(marker -> {
                    Toast.makeText(activity,marker.getTag().toString(), Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder dialogueBuilder = new AlertDialog.Builder(requireContext());
                    final View popup_view =getLayoutInflater().inflate(R.layout.popup_marker_detail,null);

                    TextView projectNameView = popup_view.findViewById(R.id.project_name);
                    TextView category = popup_view.findViewById(R.id.category);
                    TextView start_date = popup_view.findViewById(R.id.start_date);
                    TextView end_date = popup_view.findViewById(R.id.end_date);
                    ImageButton btn = popup_view.findViewById(R.id.imageButton);
                    int pos = Integer.parseInt(marker.getTag().toString());

                    Projects project = viewModel.projectsLiveData.getValue().get(pos);

                    projectNameView.setText(project.getProject_name());
                    category.setText(project.getCategory());
                    start_date.setText(project.getProject_start_time().toString());
                    end_date.setText(project.getProject_completion_time().toString());

                    dialogueBuilder.setView(popup_view);
                    AlertDialog dialog = dialogueBuilder.create();
                    dialog.show();

                    btn.setOnClickListener(v->{
                        Fragment fragment = ProjectDetailsFragment.newInstance(project);
                        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.id_fragment_controller,fragment,"fasfasd");
                        transaction.addToBackStack(null);
                        transaction.commit();
                        dialog.hide();
                    });

                    return false;
                });
            });
        });
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        LatLng dhaka = new LatLng(23.8103,90.4125);
        map.addMarker(new MarkerOptions().position(dhaka).title("Dhaka"));
        map.moveCamera(CameraUpdateFactory.newLatLng(dhaka));

        map.setOnMarkerClickListener(marker -> {
            Toast.makeText(activity,marker.getId(), Toast.LENGTH_SHORT).show();
            return false;
        });
    }

}