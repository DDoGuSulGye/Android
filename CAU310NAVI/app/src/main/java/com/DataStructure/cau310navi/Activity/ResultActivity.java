package com.DataStructure.cau310navi.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.DataStructure.cau310navi.Data.DataHelper;
import com.DataStructure.cau310navi.Data.DataPool;
import com.DataStructure.cau310navi.Data.ElevatorNode;
import com.DataStructure.cau310navi.Data.HorizontalAlgorithm;
import com.DataStructure.cau310navi.Data.StairNode;
import com.DataStructure.cau310navi.Fragment.Elevator_Fragment;
import com.DataStructure.cau310navi.Fragment.Stair_Fragment;
import com.DataStructure.cau310navi.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by dkdk6 on 2018-11-18.
 */

public class ResultActivity extends AppCompatActivity implements View.OnClickListener{
    ImageButton bt1,bt2,bt3;
    FragmentManager fm;
    FragmentTransaction tran;
    Elevator_Fragment elevator;
    Stair_Fragment stair;


    DataPool dataPool;
    HorizontalAlgorithm horizontalAlgorithm;
    ArrayList<ArrayList<String>> startToMiddleElevator = null;
    ArrayList<ArrayList<String>> middleToEndElevator = null;

    ArrayList<ArrayList<String>> startToMiddleStair = null;
    ArrayList<ArrayList<String>> middleToEndStair = null;

    String day ;
    int hour ;
    int minute ;
    boolean boomTime ;
    int classTime ;

    String start ;
    String middle ;
    String end ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        bt1 = (ImageButton) findViewById(R.id.bt1);
        bt2 = (ImageButton) findViewById(R.id.bt2);


        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        elevator = new Elevator_Fragment();
        stair = new Stair_Fragment();


        Intent intent = getIntent();
        setData(intent);

        if(middle.equals("")){ // 경유지 없을 때
            startToMiddleElevator = ddoGorithm_Elevator(start, end);
            startToMiddleStair = ddoGorithm_Stair(start, end);
        } else{
            startToMiddleElevator = ddoGorithm_Elevator(start, middle);
            middleToEndElevator = ddoGorithm_Elevator(middle, end);

            startToMiddleStair = ddoGorithm_Stair(start, middle);
            middleToEndStair = ddoGorithm_Stair(middle, end);
        }

        setFrag(0);
    }

    public int floorParser(String area){
        if(area.equals("후문입구")){
            return 1;
        }

        if(area.charAt(0)=='B'){
            return -(Integer.parseInt(area.charAt(1)+""));
        }else{
            return Integer.parseInt(area.charAt(0)+"");
        }
    }

    public void setData(Intent intent) {
        day = intent.getStringExtra("DAY_OF_WEEK");
        hour = intent.getIntExtra("HOUR", 0);
        minute = intent.getIntExtra("MINUTE", 0);
        boomTime = intent.getBooleanExtra("BOOMTIME", false);
        classTime = intent.getIntExtra("CLASSTIME", 0);
        int myFloor = 0;

        start = intent.getStringExtra("START");
        middle = intent.getStringExtra("MIDDLE");
        end = intent.getStringExtra("END");

        horizontalAlgorithm = new HorizontalAlgorithm(getApplicationContext());
        dataPool = new DataPool(getApplicationContext());
        dataPool.fillClassNode();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<ArrayList<String>> ddoGorithm_Elevator(String start, String end) {
        ArrayList<ArrayList<String>> returnArray = new ArrayList<ArrayList<String>>();
        if(floorParser(start) != floorParser(end)){ // 층수 다르면 수직이동이 포함된다.

            ElevatorNode A = dataPool.setElevatorNode("A", day, -6, 9, 1, classTime);
            ElevatorNode B = dataPool.setElevatorNode("B", day, -3, 9, 1, classTime);
            ElevatorNode C = dataPool.setElevatorNode("C", day, -3, 9, 1, classTime);

            Map<String,Map<String,Double>> cityMapStart = horizontalAlgorithm.getFloorCityMap(floorParser(start));
            Map<String,Map<String,Double>> cityMapEnd = horizontalAlgorithm.getFloorCityMap(floorParser(end));
            HashMap<Integer, String> sameElevator = new HashMap<>();

            for(Iterator iterator1 = cityMapStart.keySet().iterator() ; iterator1.hasNext();){
                String keyName = (String)iterator1.next();
                for(Iterator iterator2 = cityMapEnd.keySet().iterator() ; iterator2.hasNext();){
                    if(keyName.equals((String)iterator2.next())){
                        if(keyName.equals("A"))
                            sameElevator.put(0, keyName);
                        if(keyName.equals("B"))
                            sameElevator.put(1, keyName);
                        if(keyName.equals("C"))
                            sameElevator.put(2, keyName);
                    }
                }
            }

            ElevatorNode optimalNode = dataPool.getOptimaleElevatorNode(A, B, C, start, sameElevator, boomTime, horizontalAlgorithm, cityMapStart);
            ArrayList<String> startPath = horizontalAlgorithm.dijkstraReturnPath(start, optimalNode.getArea(), cityMapStart);
            ArrayList<String> endPath = horizontalAlgorithm.dijkstraReturnPath(optimalNode.getArea(), end, cityMapEnd);

            returnArray.add(startPath);
            returnArray.add(endPath);

            return returnArray;
        } else { // 같은 층수일 때 : 수직이동 없을 때
            Map<String,Map<String,Double>> cityMap = horizontalAlgorithm.getFloorCityMap(floorParser(start));
            ArrayList<String> path = horizontalAlgorithm.dijkstraReturnPath(start, end, cityMap);

            returnArray.add(path);

            return returnArray;
        }
    }

    public ArrayList<ArrayList<String>> ddoGorithm_Stair(String start, String end) {
        ArrayList<ArrayList<String>> returnArray = new ArrayList<ArrayList<String>>();
        if(floorParser(start) != floorParser(end)){ // 층수 다르면 수직이동이 포함된다.

            StairNode D = dataPool.setStairNode("D", day, -3, 9);
            StairNode E = dataPool.setStairNode("E", day, -3, 9);
            StairNode F = dataPool.setStairNode("F", day, 1, 9);
            StairNode G = dataPool.setStairNode("G", day, -6, 1);

            Map<String,Map<String,Double>> cityMapStart = horizontalAlgorithm.getFloorCityMap(floorParser(start));
            Map<String,Map<String,Double>> cityMapEnd = horizontalAlgorithm.getFloorCityMap(floorParser(end));
            HashMap<Integer, String> sameStair = new HashMap<>();

            for(Iterator iterator1 = cityMapStart.keySet().iterator() ; iterator1.hasNext();){
                String keyName = (String)iterator1.next();
                for(Iterator iterator2 = cityMapEnd.keySet().iterator() ; iterator2.hasNext();){
                    if(keyName.equals((String)iterator2.next())){
                        if(keyName.equals("D"))
                            sameStair.put(0, keyName);
                        if(keyName.equals("E"))
                            sameStair.put(1, keyName);
                        if(keyName.equals("F"))
                            sameStair.put(2, keyName);
                        if(keyName.equals("G"))
                            sameStair.put(3, keyName);
                    }
                }
            }

            if(sameStair.size() == 0){
                Map<String,Map<String,Double>> cityMapMiddle = horizontalAlgorithm.getFloorCityMap(1);
                ArrayList<String> startPath = horizontalAlgorithm.dijkstraReturnPath(start, "G", cityMapStart);

                for(Iterator iterator1 = cityMapMiddle.keySet().iterator() ; iterator1.hasNext();){
                    String keyName = (String)iterator1.next();
                    for(Iterator iterator2 = cityMapEnd.keySet().iterator() ; iterator2.hasNext();){
                        if(keyName.equals((String)iterator2.next())){
                            if(keyName.equals("D"))
                                sameStair.put(0, keyName);
                            if(keyName.equals("E"))
                                sameStair.put(1, keyName);
                            if(keyName.equals("F"))
                                sameStair.put(2, keyName);
                            if(keyName.equals("G"))
                                sameStair.put(3, keyName);
                        }
                    }
                }

                StairNode optimalNode = dataPool.getOptimalStairNode(D, E, F, G, "G", sameStair, horizontalAlgorithm, cityMapMiddle);

                ArrayList<String> middlePath = horizontalAlgorithm.dijkstraReturnPath("G", optimalNode.getArea(), cityMapMiddle);
                ArrayList<String> endPath = horizontalAlgorithm.dijkstraReturnPath(optimalNode.getArea(), end, cityMapEnd);
                returnArray.add(startPath);
                returnArray.add(middlePath);
                returnArray.add(endPath);
            } else {
                StairNode optimalNode = dataPool.getOptimalStairNode(D, E, F, G, start, sameStair, horizontalAlgorithm, cityMapStart);

                ArrayList<String> startPath = horizontalAlgorithm.dijkstraReturnPath(start, optimalNode.getArea(), cityMapStart);
                ArrayList<String> endPath = horizontalAlgorithm.dijkstraReturnPath(optimalNode.getArea(), end, cityMapEnd);
                returnArray.add(startPath);
                returnArray.add(endPath);
            }

            return returnArray;
        } else { // 같은 층수일 때 : 수직이동 없을 때
            Map<String,Map<String,Double>> cityMap = horizontalAlgorithm.getFloorCityMap(floorParser(start));
            ArrayList<String> path = horizontalAlgorithm.dijkstraReturnPath(start, end, cityMap);

            returnArray.add(path);

            return returnArray;
        }
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.bt1:
                setFrag(0);
                break;
            case R.id.bt2:
                setFrag(1);
                break;
        }
    }

    public void setFrag(int n){
        fm = getFragmentManager();
        tran = fm.beginTransaction();
        switch (n){
            case 0:
                Bundle bundle1 = new Bundle();
                if(middleToEndElevator==null){
                    //경유지 없는 경우
                    bundle1.putInt("Type",0);
                    bundle1.putSerializable("LIST1", new DataHelper(startToMiddleElevator));
                }else{
                    bundle1.putInt("Type",1);
                    bundle1.putSerializable("LIST1", new DataHelper(startToMiddleElevator));
                    bundle1.putSerializable("LIST2", new DataHelper(middleToEndElevator));
                }
                elevator.setArguments(bundle1);
                tran.replace(R.id.main_frame, elevator);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                tran.commit();
                break;
            case 1:
                Bundle bundle2 = new Bundle();
                if(middleToEndStair==null){
                    //경유지 없는 경우
                    bundle2.putInt("Type",0);
                    bundle2.putSerializable("LIST1", new DataHelper(startToMiddleStair));
                }else{
                    bundle2.putInt("Type",1);
                    bundle2.putSerializable("LIST1", new DataHelper(startToMiddleStair));
                    bundle2.putSerializable("LIST2", new DataHelper(middleToEndStair));
                }
                stair.setArguments(bundle2);
                tran.replace(R.id.main_frame, stair);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                tran.commit();
                break;
        }
    }
}