package com.DataStructure.cau310navi.Data;

import java.io.Serializable;
import java.util.ArrayList;

public class DataHelper implements Serializable {

    private ArrayList<ArrayList<String>> floors;

    public DataHelper(ArrayList<ArrayList<String>> floors) {
        this.floors = floors;
    }

    public ArrayList<ArrayList<String>> getList() {
        return this.floors;
    }
}