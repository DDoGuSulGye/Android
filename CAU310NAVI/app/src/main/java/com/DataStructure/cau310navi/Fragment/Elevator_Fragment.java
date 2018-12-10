package com.DataStructure.cau310navi.Fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.DataStructure.cau310navi.Data.DataHelper;
import com.DataStructure.cau310navi.R;

import java.util.ArrayList;

/**
 * Created by dkdk6 on 2018-11-18.
 */

public class Elevator_Fragment extends Fragment {
    View view;
    int resultType = 0;
    ArrayList<ArrayList<String>> data1;
    ArrayList<ArrayList<String>> data2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_elevator, container, false);
        LinearLayout topLL = (LinearLayout)view.findViewById(R.id.elevator_main_layout); //parent view
        Double distance = 0.0;

        resultType= getArguments().getInt("Type");
        if(resultType==0){
            data1 = ((DataHelper)getArguments().getSerializable("LIST1")).getList(); //startToMiddleElevator
            for(int a = 0; a<data1.size(); a++){
                distance+=Double.parseDouble(data1.get(a).get(data1.get(a).size()-1));
            }
            TextView totalDistance = new TextView(view.getContext());
            totalDistance.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            totalDistance.setPadding(10, 10, 10, 10);
            totalDistance.setTextSize(30);
            distance = distance*7;
            distance = distance/60;
            totalDistance.setText("총 소요시간 : 약" +distance.intValue()+"분");
            totalDistance.setTextColor(Color.parseColor("#ffffff"));
            topLL.addView(totalDistance);
            for(int i=0; i<data1.size(); i++) {
                for(int j=0; j<data1.get(i).size()-1; j++){
                    LinearLayout mainLL = new LinearLayout(view.getContext());
                    TextView topTV1 = new TextView(view.getContext());
                    ImageView mainImage = new ImageView(view.getContext());
                    mainLL.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    mainLL.setOrientation(LinearLayout.HORIZONTAL);
                    topTV1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    topTV1.setPadding(10, 10, 10, 10);

                    topTV1.setTextColor(Color.parseColor("#ffffff"));
                    if((j==0)&&(i==0)||(i== data1.size()-1)&&(j==data1.get(i).size()-2)){
                        mainImage.setLayoutParams(new LinearLayout.LayoutParams(70, 70));
                        mainImage.setImageDrawable(view.getResources().getDrawable(R.drawable.mainnode));
                        mainImage.setPadding(10,5,5,5);
                        topTV1.setTextSize(20);
                        topTV1.setText(data1.get(i).get(j));
                    }else{
                        mainImage.setLayoutParams(new LinearLayout.LayoutParams(60, 60));
                        mainImage.setImageDrawable(view.getResources().getDrawable(R.drawable.subroot));
                        mainImage.setPadding(30,5,5,5);
                        topTV1.setTextSize(15);
                        topTV1.setText(data1.get(i).get(j)+" 구역");
                    }
                    mainLL.addView(mainImage);
                    mainLL.addView(topTV1);
                    topLL.addView(mainLL);

                }
            }
        }else{
            ArrayList<String> routeList = new ArrayList<>();
            data1 = ((DataHelper)getArguments().getSerializable("LIST1")).getList(); //startToMiddleElevator
            data2 = ((DataHelper)getArguments().getSerializable("LIST2")).getList(); //middleToEndElevator
            //1. 시간 계산
            for(int a = 0; a<data1.size(); a++){
                distance+=Double.parseDouble(data1.get(a).get(data1.get(a).size()-1));
            }
            for(int b = 0; b<data2.size(); b++){
                distance+= Double.parseDouble(data2.get(b).get(data2.get(b).size()-1));
            }
            TextView totalDistance = new TextView(view.getContext());
            totalDistance.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            totalDistance.setPadding(10, 10, 10, 10);
            totalDistance.setTextSize(30);
            distance = distance*7;
            totalDistance.setTextColor(Color.parseColor("#ffffff"));
            distance = distance/60;
            totalDistance.setText("총 소요시간 : 약 "+distance.intValue()+"분");
            topLL.addView(totalDistance);
            //2. 경로 넣기 start to middle
            for(int i=0; i<data1.size(); i++) {
                for(int j=0; j<data1.get(i).size()-1; j++){
                    LinearLayout mainLL = new LinearLayout(view.getContext());
                    TextView topTV1 = new TextView(view.getContext());
                    ImageView mainImage = new ImageView(view.getContext());
                    mainLL.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    mainLL.setOrientation(LinearLayout.HORIZONTAL);
                    topTV1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    topTV1.setPadding(10, 10, 10, 10);

                    topTV1.setTextColor(Color.parseColor("#ffffff"));
                    if((j==0)&&(i==0)||(i== data1.size()-1)&&(j==data1.get(i).size()-2)){
                        mainImage.setLayoutParams(new LinearLayout.LayoutParams(70, 70));
                        mainImage.setImageDrawable(view.getResources().getDrawable(R.drawable.mainnode));
                        mainImage.setPadding(10,5,5,5);
                        topTV1.setTextSize(20);
                        topTV1.setText(data1.get(i).get(j));
                    }else{
                        mainImage.setLayoutParams(new LinearLayout.LayoutParams(60, 60));
                        mainImage.setImageDrawable(view.getResources().getDrawable(R.drawable.subroot));
                        mainImage.setPadding(30,5,5,5);
                        topTV1.setTextSize(15);
                        topTV1.setText(data1.get(i).get(j)+" 구역");
                    }
                    mainLL.addView(mainImage);
                    mainLL.addView(topTV1);
                    topLL.addView(mainLL);
                }
            }
            //midle to end
            for(int i=0; i<data2.size(); i++) {
                for(int j=1; j<data2.get(i).size()-1; j++){
                    LinearLayout mainLL = new LinearLayout(view.getContext());
                    TextView topTV1 = new TextView(view.getContext());
                    ImageView mainImage = new ImageView(view.getContext());
                    mainLL.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    mainLL.setOrientation(LinearLayout.HORIZONTAL);
                    topTV1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    topTV1.setPadding(10, 10, 10, 10);
                    topTV1.setTextColor(Color.parseColor("#ffffff"));
                    if((j==0)&&(i==0)||(i== data2.size()-1)&&(j==data2.get(i).size()-2)){
                        mainImage.setLayoutParams(new LinearLayout.LayoutParams(70, 70));
                        mainImage.setImageDrawable(view.getResources().getDrawable(R.drawable.mainnode));
                        mainImage.setPadding(10,5,5,5);
                        topTV1.setTextSize(20);
                        topTV1.setText(data2.get(i).get(j));
                    }else{
                        mainImage.setLayoutParams(new LinearLayout.LayoutParams(60, 60));
                        mainImage.setImageDrawable(view.getResources().getDrawable(R.drawable.subroot));
                        mainImage.setPadding(30,5,5,5);
                        topTV1.setTextSize(15);
                        topTV1.setText(data2.get(i).get(j)+" 구역");
                    }

                    mainLL.addView(mainImage);
                    mainLL.addView(topTV1);
                    topLL.addView(mainLL);
                }
            }
        }
        return view;
    }
}
