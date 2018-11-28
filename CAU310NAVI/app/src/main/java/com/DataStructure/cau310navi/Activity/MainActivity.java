package com.DataStructure.cau310navi.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.DataStructure.cau310navi.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by leesd on 2018-11-28.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton search, change;
    Button start, middle, end;
    ExpandableListView expand_start, expand_middle, expand_end;
    FrameLayout expand_frame_start, expand_frame_middle, expand_frame_end;
    ArrayList<ParentData> data = new ArrayList<ParentData>();
    TimePicker timePicker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = (Button) findViewById(R.id.button_start);
        middle = (Button) findViewById(R.id.button_middle);
        end = (Button) findViewById(R.id.button_end);
        expand_start = (ExpandableListView) findViewById(R.id.expand_start);
        expand_middle = (ExpandableListView) findViewById(R.id.expand_middle);
        expand_end = (ExpandableListView) findViewById(R.id.expand_end);
        expand_frame_start = (FrameLayout) findViewById(R.id.expand_frame_start);
        expand_frame_middle = (FrameLayout) findViewById(R.id.expand_frame_middle);
        expand_frame_end = (FrameLayout) findViewById(R.id.expand_frame_end);
        change = (ImageButton) findViewById(R.id.change_btn);
        search = (ImageButton) findViewById(R.id.search_btn);
        timePicker = (TimePicker) findViewById(R.id.timePicker);

        Date date = Calendar.getInstance().getTime();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setHour(date.getHours());
            timePicker.setMinute(date.getMinutes());
        }
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
        expand_middle.setAdapter(expandAdapter);
        expand_end.setAdapter(expandAdapter);

        start.setOnClickListener(this);
        middle.setOnClickListener(this);
        change.setOnClickListener(this);
        search.setOnClickListener(this);
        end.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
            case R.id.search_btn :
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    intent.putExtra("HOUR", timePicker.getHour());
                    intent.putExtra("MINUTE", timePicker.getMinute());
                }
                startActivity(intent);
                break;
            case R.id.button_start:
                expand_frame_start.setVisibility(View.VISIBLE);
                break;
            case R.id.button_middle:
                expand_frame_middle.setVisibility(View.VISIBLE);
                break;
            case R.id.button_end:
                expand_frame_end.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(expand_frame_start.getVisibility() == View.VISIBLE || expand_frame_middle.getVisibility() == View.VISIBLE || expand_frame_end.getVisibility() == View.VISIBLE){
            expand_frame_start.setVisibility(View.GONE);
            expand_frame_middle.setVisibility(View.GONE);
            expand_frame_end.setVisibility(View.GONE);
        } else {
            moveTaskToBack(true);
        }
    }
}
