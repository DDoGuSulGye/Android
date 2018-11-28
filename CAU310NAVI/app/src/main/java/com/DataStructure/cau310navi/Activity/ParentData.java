package com.DataStructure.cau310navi.Activity;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by leesd on 2018-11-25.
 */

public class ParentData {
    private String name;
    public ArrayList<String> child;

    public ParentData(String name) {
        this.name = name;
        child = new ArrayList<String>();
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }
}
