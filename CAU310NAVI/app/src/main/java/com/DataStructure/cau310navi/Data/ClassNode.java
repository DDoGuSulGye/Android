package com.DataStructure.cau310navi.Data;

import java.io.Serializable;
import java.util.ArrayList;

public class ClassNode implements Serializable {

    String ClassName;
    ArrayList<Integer> monday = new ArrayList<>();
    ArrayList<Integer> tuesday  = new ArrayList<>();
    ArrayList<Integer> wednesday  = new ArrayList<>();
    ArrayList<Integer> thursday  = new ArrayList<>();
    ArrayList<Integer> friday  = new ArrayList<>();
    String nearbyElevator;
    String nearbyStair;
    String floor;

    public String getNearbyStair() { return nearbyStair; }

    public void setNearbyStair(String nearbyStair) { this.nearbyStair = nearbyStair; }

    public String getClassName(){
        return ClassName;
    }

    public ArrayList<Integer> getMonday(){
        return monday;
    }

    public ArrayList<Integer> getTuesday(){
        return tuesday;
    }

    public ArrayList<Integer> getWednesday(){
        return wednesday;
    }

    public ArrayList<Integer> getThursday(){
        return thursday;
    }

    public ArrayList<Integer> getFriday(){
        return friday;
    }

    public String getNearbyElevator(){
        return nearbyElevator;
    }

    public String getFloor() {
        return floor;
    }



    public void setClassName(String ClassName){
        this.ClassName = ClassName;
    }

    public void setMonday(ArrayList<Integer> monday){
        //this.monday = monday;
        this.monday.addAll(monday);
    }

    public void setTuesday(ArrayList<Integer> tuesday){
        this.tuesday.addAll(tuesday);
    }

    public void setWednesday(ArrayList<Integer> wednesday){
        this.wednesday.addAll(wednesday);
    }

    public void setThursday(ArrayList<Integer> thursday){
        this.thursday.addAll(thursday);
    }

    public void setFriday(ArrayList<Integer> friday){
        this.friday.addAll(friday);
    }

    public void setNearbyElevator(String nearbyElevator){
        this.nearbyElevator = nearbyElevator;
    }

    public void setFloor(String floor){
        this.floor = floor;
    }

}