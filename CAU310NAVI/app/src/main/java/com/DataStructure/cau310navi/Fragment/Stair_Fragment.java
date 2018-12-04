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

import com.DataStructure.cau310navi.Data.DataHelper;
import com.DataStructure.cau310navi.R;

import java.util.ArrayList;

/**
 * Created by dkdk6 on 2018-11-18.
 */

public class Stair_Fragment extends Fragment {

    int resultType = 0;
    ArrayList<ArrayList<String>> data1;
    ArrayList<ArrayList<String>> data2;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_stair, container, false);
        LinearLayout topLL = (LinearLayout)view.findViewById(R.id.stair_main_layout); //parent view
        LinearLayout subLL = (LinearLayout)view.findViewById(R.id.dynamicArea_stair);
        resultType= getArguments().getInt("Type");
        if(resultType==0){
            //경유지 없는 경우
            data1 = ((DataHelper)getArguments().getSerializable("LIST1")).getList();
            data1 = ((DataHelper)getArguments().getSerializable("LIST1")).getList(); //startToMiddleElevator
            data2 = ((DataHelper)getArguments().getSerializable("LIST2")).getList(); //middleToEndElevator
            for(int i=0; i<data1.size(); i++) {
                TextView topTV1 = new TextView(view.getContext());
                topTV1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                topTV1.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                topTV1.setPadding(20, 10, 10, 10);
                topTV1.setTextColor(Color.parseColor("#FF7200"));
                topTV1.setTextSize(20);
                if(i==data1.size()-1){
                    topTV1.setText(data1.get(i).get(data1.get(i).size()-2)); //END
                }else{
                    topTV1.setText(data1.get(i).get(0)); //Start
                }
                topLL.addView(topTV1);
                LinearLayout parentLL = new LinearLayout(view.getContext());
                parentLL.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                parentLL.setOrientation(LinearLayout.HORIZONTAL);
                for(int j=1; j<data1.get(i).size()-1; j++){
                    TextView topTV2 = new TextView(view.getContext());
                    topTV2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    topTV2.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                    topTV2.setPadding(20, 10, 10, 10);
                    topTV2.setTextColor(Color.parseColor("#dddddd"));
                    topTV2.setTextSize(13);
                    if(data1.get(i).get(j).length()==1){
                        topTV2.setText(data1.get(0).get(j)+"구역"); //Start
                    }else{
                        topTV2.setText(data1.get(0).get(j)); //Start
                    }
                    parentLL.addView(topTV2);
                }
                topLL.addView(parentLL);
            }
        }else{
            data1 = ((DataHelper)getArguments().getSerializable("LIST1")).getList(); //startToMiddleElevator
            data2 = ((DataHelper)getArguments().getSerializable("LIST2")).getList(); //middleToEndElevator
            for(int i=0; i<data1.size(); i++) {
                TextView topTV1 = new TextView(view.getContext());
                topTV1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                topTV1.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                topTV1.setPadding(20, 10, 10, 10);
                topTV1.setTextColor(Color.parseColor("#FF7200"));
                topTV1.setTextSize(20);
                if(i==data1.size()-1){
                    topTV1.setText(data1.get(i).get(data1.get(i).size()-2)); //END
                }else{
                    topTV1.setText(data1.get(i).get(0)); //Start
                }
                topLL.addView(topTV1);
                LinearLayout parentLL = new LinearLayout(view.getContext());
                parentLL.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                parentLL.setOrientation(LinearLayout.HORIZONTAL);
                for(int j=1; j<data1.get(i).size()-1; j++){
                    TextView topTV2 = new TextView(view.getContext());
                    topTV2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    topTV2.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                    topTV2.setPadding(20, 10, 10, 10);
                    topTV2.setTextColor(Color.parseColor("#dddddd"));
                    topTV2.setTextSize(13);
                    if(data1.get(i).get(j).length()==1){
                        topTV2.setText(data1.get(0).get(j)+"구역"); //Start
                    }else{
                        topTV2.setText(data1.get(0).get(j)); //Start
                    }
                    parentLL.addView(topTV2);
                }
                topLL.addView(parentLL);
            }
            for(int i=0; i<data2.size(); i++) {
                TextView topTV1 = new TextView(view.getContext());
                topTV1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                topTV1.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                topTV1.setPadding(20, 10, 10, 10);
                topTV1.setTextColor(Color.parseColor("#FF7200"));
                topTV1.setTextSize(20);
                if(i==data2.size()-1){
                    topTV1.setText(data2.get(i).get(data2.get(i).size()-2)); //END
                }else{
                    topTV1.setText(data2.get(i).get(0)); //Start
                }
                topLL.addView(topTV1);
                LinearLayout parentLL = new LinearLayout(view.getContext());
                parentLL.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                parentLL.setOrientation(LinearLayout.HORIZONTAL);
                for(int j=1; j<data2.get(i).size()-1; j++){
                    TextView topTV2 = new TextView(view.getContext());
                    topTV2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    topTV2.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                    topTV2.setPadding(20, 10, 10, 10);
                    topTV2.setTextColor(Color.parseColor("#dddddd"));
                    topTV2.setTextSize(13);
                    if(data2.get(i).get(j).length()==1){
                        topTV2.setText(data2.get(0).get(j)+"구역"); //Start
                    }else{
                        topTV2.setText(data2.get(0).get(j)); //Start
                    }
                    parentLL.addView(topTV2);
                }
                topLL.addView(parentLL);
            }
        }
        return view;
    }
}
