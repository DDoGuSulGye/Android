package com.DataStructure.cau310navi.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.DataStructure.cau310navi.R;

/**
 * Created by dkdk6 on 2018-11-18.
 */

public class Time_Fragment extends Fragment {
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_time, container, false);
        return view;
    }
}
