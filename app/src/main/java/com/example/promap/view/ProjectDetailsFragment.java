package com.example.promap.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.promap.R;
import com.example.promap.model.Projects;

public class ProjectDetailsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    static Projects projects = new Projects();

    public ProjectDetailsFragment() {
        // Required empty public constructor
    }

    public static ProjectDetailsFragment newInstance(Projects proj) {
        ProjectDetailsFragment fragment = new ProjectDetailsFragment();
        Bundle args = new Bundle();
        projects = proj;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_project_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView name = view.findViewById(R.id.project_name);
        name.setText(projects.getProject_name());
        TextView detail = view.findViewById(R.id.details);
        detail.setText(projects.getDescription());
        TextView per = view.findViewById(R.id.percentage);
        per.setText(projects.getCompletion_percentage());
        TextView cata = view.findViewById(R.id.category);
        cata.setText(projects.getCategory());
        TextView st = view.findViewById(R.id.start_date);
        st.setText(projects.getProject_start_time().toString());
        TextView ed = view.findViewById(R.id.end_date);
        ed.setText(projects.getProject_completion_time().toString());
    }
}