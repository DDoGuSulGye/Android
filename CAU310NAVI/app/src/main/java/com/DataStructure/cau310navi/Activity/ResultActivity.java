package com.DataStructure.cau310navi.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.DataStructure.cau310navi.Fragment.Elevator_Fragment;
import com.DataStructure.cau310navi.Fragment.Stair_Fragment;
import com.DataStructure.cau310navi.Fragment.Time_Fragment;
import com.DataStructure.cau310navi.Fragment.Wait_Fragment;
import com.DataStructure.cau310navi.R;

import java.util.ArrayList;

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
