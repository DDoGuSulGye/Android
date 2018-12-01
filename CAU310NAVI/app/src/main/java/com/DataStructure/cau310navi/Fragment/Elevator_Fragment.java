package com.DataStructure.cau310navi.Fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.DataStructure.cau310navi.R;

/**
 * Created by dkdk6 on 2018-11-18.
 */

public class Elevator_Fragment extends Fragment {
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_elevator, container, false);
        LinearLayout topLL = (LinearLayout)view.findViewById(R.id.dynamicArea); //parent view

        for(int i=0; i<3; i++){
            TextView topTV1 = new TextView(view.getContext());
            topTV1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            topTV1.setBackgroundColor(Color.parseColor("#00FFFFFF"));
            topTV1.setPadding(20, 10, 10, 10);
            topTV1.setTextColor(Color.parseColor("#FF7200"));
            topTV1.setTextSize(13);
            topTV1.setText("텍스트");
            topLL.addView(topTV1);
            LinearLayout parentLL = new LinearLayout(view.getContext()); //child view
            parentLL.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            parentLL.setOrientation(LinearLayout.HORIZONTAL);
            topLL.addView(parentLL);
        }
        return view;
    }


}
