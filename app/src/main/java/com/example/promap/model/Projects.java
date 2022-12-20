package com.example.promap.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;

public class Projects {
    String project_name, category, affiliated_agency,
            description, total_budget;
    String completion_percentage;
    ArrayList<LatLng> location_coordinates;
    Date project_start_time, project_completion_time;
    public Projects(){

    }
    public Projects(String project_name,String category,String affiliated_agency,String description){
        this.project_name = project_name;
        this.category=category;
        this.affiliated_agency=affiliated_agency;
        this.description = description;
    }
    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAffiliated_agency() {
        return affiliated_agency;
    }

    public void setAffiliated_agency(String affiliated_agency) {
        this.affiliated_agency = affiliated_agency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getProject_start_time() {
        return project_start_time;
    }

    public void setProject_start_time(Date project_start_time) {
        this.project_start_time = project_start_time;
    }

    public Date getProject_completion_time() {
        return project_completion_time;
    }

    public void setProject_completion_time(Date project_completion_time) {
        this.project_completion_time = project_completion_time;
    }

    public String getTotal_budget() {
        return total_budget;
    }

    public void setTotal_budget(String total_budget) {
        this.total_budget = total_budget;
    }

    public String getCompletion_percentage() {
        return completion_percentage;
    }

    public void setCompletion_percentage(String completion_percentage) {
        this.completion_percentage = completion_percentage;
    }

    public ArrayList<LatLng> getLocation_coordinates() {
        return location_coordinates;
    }
    public LatLng getLatLng(int pos){
        return  location_coordinates.get(pos);
    }

    public void setLocation_coordinates(ArrayList<LatLng> location_coordinates) {
        this.location_coordinates = new ArrayList<LatLng>();
        for (LatLng lt : location_coordinates) {
            this.location_coordinates.add(lt);
        }
    }

    @Override
    public String toString() {
        return "Projects{" +
                "project_name='" + project_name + '\'' +
                ", category='" + category + '\'' +
                ", affiliated_agency='" + affiliated_agency + '\'' +
                ", description='" + description + '\'' +
                ", total_budget='" + total_budget + '\'' +
                ", completion_percentage='" + completion_percentage + '\'' +
                ", project_start_time=" + project_start_time +
                ", project_completion_time=" + project_completion_time +
                '}';
    }
}
