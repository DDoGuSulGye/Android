package com.DataStructure.cau310navi.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.DataStructure.cau310navi.Data.DataHelper;
import com.DataStructure.cau310navi.Data.DataPool;
import com.DataStructure.cau310navi.Data.ElevatorNode;
import com.DataStructure.cau310navi.Data.HorizontalAlgorithm;
import com.DataStructure.cau310navi.Data.StairNode;
import com.DataStructure.cau310navi.Fragment.Elevator_Fragment;
import com.DataStructure.cau310navi.Fragment.Stair_Fragment;
import com.DataStructure.cau310navi.Fragment.Time_Fragment;
import com.DataStructure.cau310navi.Fragment.Wait_Fragment;
import com.DataStructure.cau310navi.R;

import java.util.ArrayList;
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
    Time_Fragment time;
    Wait_Fragment wait;

    DataPool dataPool;
    HorizontalAlgorithm horizontalAlgorithm;
    ArrayList<ArrayList<String>> startToMiddle;
    ArrayList<ArrayList<String>> middleToEnd;

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
        bt3 = (ImageButton) findViewById(R.id.bt3);

        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        elevator = new Elevator_Fragment();
        stair = new Stair_Fragment();
        time = new Time_Fragment();
        wait = new Wait_Fragment();

        Intent intent = getIntent();
        setData(intent);

        if(middle.equals("")){ // 경유지 없을 때
            startToMiddle = ddoGorithm_Elevator(start, end);
        } else{
            startToMiddle = ddoGorithm_Elevator(start, middle);
            middleToEnd = ddoGorithm_Elevator(middle, end);
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable("LIST", new DataHelper(startToMiddle));
        elevator.setArguments(bundle);

        setFrag(3);
    }

    public int floorParser(String area){
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


    public ArrayList<ArrayList<String>> ddoGorithm_Elevator(String start, String end) {
        ArrayList<ArrayList<String>> returnArray = new ArrayList<ArrayList<String>>();
        if(floorParser(start) != floorParser(end)){ // 층수 다르면 수직이동이 포함된다.

            ElevatorNode A = dataPool.setElevatorNode("A", day, -3, 9, 1, classTime);
            ElevatorNode B = dataPool.setElevatorNode("B", day, -3, 9, 1, classTime);
            ElevatorNode C = dataPool.setElevatorNode("C", day, -6, 9, 1, classTime);

            Map<String,Map<String,Double>> cityMapStart = horizontalAlgorithm.getFloorCityMap(floorParser(start));
            ElevatorNode optimalNode = dataPool.getOptimaleElevatorNode(A, B, C, start, floorParser(start), boomTime, horizontalAlgorithm, cityMapStart);

            Map<String,Map<String,Double>> cityMapEnd = horizontalAlgorithm.getFloorCityMap(floorParser(end));
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

//    public ArrayList<ArrayList<String>> ddoGorithm_Stair(String start, String end) {
//        ArrayList<ArrayList<String>> returnArray = new ArrayList<ArrayList<String>>();
//        if(floorParser(start) != floorParser(end)){ // 층수 다르면 수직이동이 포함된다.
//
//            StairNode A = dataPool.setStairNode("A", day, -3, 9);
//            StairNode B = dataPool.setStairNode("B", day, -3, 9);
//            StairNode C = dataPool.setStairNode("C", day, -6, 9);
//
//            Map<String,Map<String,Double>> cityMapStart = horizontalAlgorithm.getFloorCityMap(floorParser(start));
//            ElevatorNode optimalNode = dataPool.getOptimaleElevatorNode(A, B, C, start, floorParser(start), boomTime, horizontalAlgorithm, cityMapStart);
//
//            Map<String,Map<String,Double>> cityMapEnd = horizontalAlgorithm.getFloorCityMap(floorParser(end));
//            ArrayList<String> startPath = horizontalAlgorithm.dijkstraReturnPath(start, optimalNode.getArea(), cityMapStart);
//            ArrayList<String> endPath = horizontalAlgorithm.dijkstraReturnPath(optimalNode.getArea(), end, cityMapEnd);
//
//            returnArray.add(startPath);
//            returnArray.add(endPath);
//
//            return returnArray;
//        } else { // 같은 층수일 때 : 수직이동 없을 때
//            Map<String,Map<String,Double>> cityMap = horizontalAlgorithm.getFloorCityMap(floorParser(start));
//            ArrayList<String> path = horizontalAlgorithm.dijkstraReturnPath(start, end, cityMap);
//
//            returnArray.add(path);
//
//            return returnArray;
//        }
//    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.bt1:
                setFrag(0);
                break;
            case R.id.bt2:
                setFrag(1);
                break;
            case R.id.bt3:
                setFrag(2);
                break;
        }
    }

    public void setFrag(int n){    //프래그먼트를 교체하는 작업을 하는 메소드를 만들었습니다
        fm = getFragmentManager();
        tran = fm.beginTransaction();
        switch (n){
            case 0:
                Toast toast = Toast.makeText(getApplicationContext(), "Fra1", Toast.LENGTH_LONG); toast.show();
                tran.replace(R.id.main_frame, elevator);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                tran.commit();
                break;
            case 1:
                Toast toast2 = Toast.makeText(getApplicationContext(), "Fra2", Toast.LENGTH_LONG); toast2.show();
                tran.replace(R.id.main_frame, stair);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                tran.commit();
                break;
            case 2:
                Toast toast3 = Toast.makeText(getApplicationContext(), "Fra3", Toast.LENGTH_LONG); toast3.show();
                tran.replace(R.id.main_frame, time);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                tran.commit();
                break;
            case 3:
                Toast toast4 = Toast.makeText(getApplicationContext(), "Fra4", Toast.LENGTH_LONG); toast4.show();
                tran.replace(R.id.main_frame, wait);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                tran.commit();
                break;
        }
    }
}
