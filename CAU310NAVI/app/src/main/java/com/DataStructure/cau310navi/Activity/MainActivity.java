package com.DataStructure.cau310navi.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.DataStructure.cau310navi.Fragment.Elevator_Fragment;
import com.DataStructure.cau310navi.Fragment.Stair_Fragment;
import com.DataStructure.cau310navi.Fragment.Time_Fragment;
import com.DataStructure.cau310navi.Fragment.Wait_Fragment;
import com.DataStructure.cau310navi.R;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by dkdk6 on 2018-11-18.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ImageButton bt1,bt2,bt3, change, search;
    Button start, end;
    FragmentManager fm;
    FragmentTransaction tran;
    Elevator_Fragment elevator;
    Stair_Fragment stair;
    Time_Fragment time;
    Wait_Fragment wait;
    ExpandableListView expand_start, expand_end;
    FrameLayout expand_frame_start, expand_frame_end;
    ArrayList<ParentData> data = new ArrayList<ParentData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt1 = (ImageButton) findViewById(R.id.bt1);
        bt2 = (ImageButton) findViewById(R.id.bt2);
        bt3 = (ImageButton) findViewById(R.id.bt3);
        change = (ImageButton) findViewById(R.id.change_btn);
        search = (ImageButton) findViewById(R.id.search_btn);
        start = (Button) findViewById(R.id.button_start);
        end = (Button) findViewById(R.id.button_end);
        expand_start = (ExpandableListView) findViewById(R.id.expand_start);
        expand_end = (ExpandableListView) findViewById(R.id.expand_end);
        expand_frame_start = (FrameLayout) findViewById(R.id.expand_frame_start);
        expand_frame_end = (FrameLayout) findViewById(R.id.expand_frame_end);

        expand_start.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                start.setText(parent.getExpandableListAdapter().getChild(groupPosition, childPosition).toString());
                expand_frame_start.setVisibility(View.GONE);
                return true;
            }
        });

        expand_end.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                end.setText(parent.getExpandableListAdapter().getChild(groupPosition, childPosition).toString());
                expand_frame_end.setVisibility(View.GONE);
                return false;
            }
        });

        ParentData data1 = new ParentData("First");
        data1.child.add("726");
        data1.child.add("727");

        ParentData data2 = new ParentData("Second");
        data2.child.add("726");
        data2.child.add("727");

        data.add(data1);
        data.add(data2);

        ExpandAdapter expandAdapter = new ExpandAdapter(this, data);
        expand_start.setAdapter(expandAdapter);
        expand_end.setAdapter(expandAdapter);

        start.setOnClickListener(this);
        end.setOnClickListener(this);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        change.setOnClickListener(this);
        elevator = new Elevator_Fragment();
        stair = new Stair_Fragment();
        time = new Time_Fragment();
        wait = new Wait_Fragment();
        setFrag(3);
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
            case R.id.bt3:
                setFrag(2);
                break;
            case R.id.change_btn:
                //입력 Text 바꾸어주기
                if(start.getText().toString() == null || end.getText().toString() == null){
                    Toast d = Toast.makeText(getApplicationContext(), "모든 장소를 입력하세요", Toast.LENGTH_LONG); d.show();
                }else{
                    String s_temp = start.getText().toString();
                    String e_temp = end.getText().toString();
                    start.setText(e_temp);
                    end.setText(s_temp);
                }
                break;
            case R.id.button_start:
                expand_frame_start.setVisibility(View.VISIBLE);
                break;
            case R.id.button_end:
                expand_frame_end.setVisibility(View.VISIBLE);
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

    @Override
    public void onBackPressed() {
        if(expand_frame_start.getVisibility() == View.VISIBLE || expand_frame_end.getVisibility() == View.VISIBLE){
            expand_frame_start.setVisibility(View.GONE);
            expand_frame_end.setVisibility(View.GONE);
        } else {
            moveTaskToBack(true);
        }
    }
}
