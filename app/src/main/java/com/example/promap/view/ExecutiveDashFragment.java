package com.example.promap.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.promap.R;
import com.example.promap.databinding.FragmentProjectListBinding;
import com.example.promap.model.Projects;
import com.example.promap.utils.ProjectListAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class ExecutiveDashFragment extends Fragment {
    private View view;
    public ExecutiveDashFragment() {
        // Required empty public constructor
    }

    public static ExecutiveDashFragment newInstance(String param1, String param2) {
        ExecutiveDashFragment fragment = new ExecutiveDashFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getChildFragmentManager().beginTransaction().replace(R.id.project_list,new ProjectListFragment()).commit();
    }

    public static class ProjectListFragment extends Fragment{
        private static final int PERMISSION_REQUEST_CODE = 100;
        private FragmentProjectListBinding binding = null;
        private FragmentActivity activity;
        private View view;
        private ProjectListAdapter adapter;
        private RecyclerView recyclerView;
        private ArrayList<Projects> list = new ArrayList<>();
        private StringBuilder data;
        ProgressDialog progressDialog;
        String URL = "https://www.sample-videos.com/csv/Sample-Spreadsheet-10-rows.csv";
        String tag = null;

        public ProjectListFragment() {
            // Required empty public constructor
        }
        public ProjectListFragment(String str) {
            // Required empty public constructor
            tag = str;
        }

        public static ProjectListFragment newInstance(String param1, String param2) {
            ProjectListFragment fragment = new ProjectListFragment();
            Bundle args = new Bundle();
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
            binding = DataBindingUtil.inflate(inflater,R.layout.fragment_project_list, container,false);
            if(binding==null){
                System.out.println("Here");
            }
            view = binding.getRoot();
            adapter = new ProjectListAdapter(new ArrayList<Projects>());
            return view;
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            for(int i=0; i<3; ++i){
                Projects projects = new Projects();
                projects.setProject_name("Project name");
                projects.setAffiliated_agency("Agency");
                list.add(projects);
            }
            recyclerView = binding.cardlist;
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            recyclerView.setAdapter(adapter);
            observe();
            progressDialog = new ProgressDialog(requireContext());
            progressDialog.setMessage("Fetching Data...");
            progressDialog.setCancelable(false);
            binding.csvDownBtn.setOnClickListener(v->{
                if(checkPermission()){
                    FetchData(URL);
                }else{
                    requestPermission();
                }
            });
        }

        private void observe(){
            adapter.updateList(list);
        }

        // fetch data from server
        private void FetchData(String url)
        {
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //we get the successful in String response
                            Log.e("MY_DATA",response);
                            try{
                                progressDialog.dismiss();
                                if(response.equals("NONE"))
                                {
                                    Toast.makeText(requireContext(),"NO Data Found",Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                }else{
                                    progressDialog.dismiss();
                                    // In String response we get full data in a form of list
                                    splitdata(response);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                progressDialog.dismiss();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                }
            })
            {
                @Override
                protected Map<String, String> getParams() {
                    Map<String,String> params = new HashMap<>();
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
            requestQueue.add(stringRequest);
        }
        private void splitdata(String response) {
            System.out.println("GET DATA IS "+response);

            // response will have a @ symbol so that we can split individual user data
            String res_data[] = response.split("@");
            //StringBuilder  to store the data
            data = new StringBuilder();

            //row heading to store in CSV file
            data.append("id,firstname,lastname,phone");

            for(int i = 0; i<res_data.length;i++){
                //then we split each user data using # symbol as we have in the response string
                final String[] each_user =res_data[i].split("#");
//                System.out.println("Splited # ID: "+ each_user[0]);
//                System.out.println("Splited # Firstname? : "+ each_user[1]);
//                System.out.println("Splited # Lastname? : "+ each_user[2]);
//                System.out.println("Splited # Phone ? : "+ each_user[3]);
                StringBuilder tmp = new StringBuilder();
                for(String s:each_user){
                    tmp.append(s+",");
                }
                tmp = new StringBuilder("\n"+tmp.substring(0, tmp.length() - 1));
                // then add each user data in data string builder
                data.append(tmp);
            }
            CreateCSV(data);
        }
        private void CreateCSV(StringBuilder data) {
            Calendar calendar = Calendar.getInstance();
            long time= calendar.getTimeInMillis();
            try {
                //
                FileOutputStream out = requireContext().openFileOutput("CSV_Data_"+time+".csv", Context.MODE_PRIVATE);

                //store the data in CSV file by passing String Builder data
                out.write(data.toString().getBytes());
                out.close();
                Context context = requireContext();
                final File newFile = new File(Environment.getExternalStorageDirectory(),"SimpleCVS");
                if(!newFile.exists())
                {
                    newFile.mkdir();
                }
                File file = new File(context.getFilesDir(),"CSV_Data_"+time+".csv");
                Uri path = FileProvider.getUriForFile(context,"com.example.dataintocsvformat",file);

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/csv");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Data");
                intent.putExtra(Intent.EXTRA_STREAM, path);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(intent,"Excel Data"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // checking permission To WRITE
        private boolean checkPermission() {
            int result = ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (result == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        }
        // request permission for WRITE Access
        private void requestPermission() {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(requireContext(), "Write External Storage permission allows us to save files. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_CODE);
            }
        }
        @Override
        public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
            switch (requestCode) {
                case PERMISSION_REQUEST_CODE:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Log.e("value", "Permission Granted, Now you can use local drive .");
                    } else {
                        Log.e("value", "Permission Denied, You cannot use local drive .");
                    }
                    break;
            }
        }
    }
}