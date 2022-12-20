package com.example.promap.viewmodel;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.promap.R;
import com.example.promap.model.Projects;
import com.google.android.gms.maps.model.LatLng;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MapViewModel extends AndroidViewModel {
    public MutableLiveData<List<Projects>> projectsLiveData = new MutableLiveData<>();

    public MapViewModel(@NonNull Application application) {
        super(application);
    }

    public void getData(Context context) throws IOException {
        ArrayList<Projects> data = new ArrayList<Projects>();
        try{
            CSVReader reader = new CSVReader(new InputStreamReader(context.getAssets().open("projects.csv")));
            String[]line;
            line = reader.readNext();
            while ((line= reader.readNext())!=null){
                Projects prjkt = new Projects(line[0],line[1],line[2],line[3]);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    prjkt.setProject_start_time(new SimpleDateFormat("YYYY-MM-DD").parse(line[4]));
                    prjkt.setProject_completion_time(new SimpleDateFormat("YYYY-MM-DD").parse(line[5]));
                }
                prjkt.setTotal_budget(line[6]);
                prjkt.setCompletion_percentage(line[7]);
                String [] cord = line[8].split("\\),",0);
                ArrayList<LatLng> coordinate = new ArrayList<>();
                for(String s: cord){
                   String[] latLng = (s.substring(1,s.length()-1)).split(", ",0);
                   coordinate.add(new LatLng(Double.parseDouble(latLng[0]),Double.parseDouble(latLng[1])));
                }
                prjkt.setLocation_coordinates(coordinate);
//                for(LatLng lt:coordinate){
//                    System.out.println(lt);
//                }
//                System.out.println(prjkt);
                data.add(prjkt);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        projectsLiveData.setValue(data);
    }

}
