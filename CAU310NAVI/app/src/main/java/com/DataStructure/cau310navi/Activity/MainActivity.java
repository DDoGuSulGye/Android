package com.DataStructure.cau310navi.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.DataStructure.cau310navi.R;

import java.util.ArrayList;
import java.util.Arrays;
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
        expand_start.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if(groupPosition == 0) {
                    start.setText("출발지 선택");
                    expand_frame_start.setVisibility(View.GONE);
                    return true;
                } else
                return false;
            }
        });
        expand_start.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                start.setText(parent.getExpandableListAdapter().getChild(groupPosition, childPosition).toString());
                expand_frame_start.setVisibility(View.GONE);
                return true;
            }
        });

        expand_middle.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if(groupPosition == 0) {
                    middle.setText("경유지 선택");
                    expand_frame_middle.setVisibility(View.GONE);
                    return true;
                }
                return false;
            }
        });
        expand_middle.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                middle.setText(parent.getExpandableListAdapter().getChild(groupPosition, childPosition).toString());
                expand_frame_middle.setVisibility(View.GONE);
                return true;
            }
        });

        expand_end.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if(groupPosition == 0) {
                    end.setText("도착지 선택");
                    expand_frame_end.setVisibility(View.GONE);
                    return true;
                }
                return false;
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

        setData();

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
                Calendar cal = Calendar.getInstance();
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                String day = new String("");
                Integer hour = new Integer(0);
                Integer minute = new Integer(0);
                Integer classTime = new Integer(0);
                boolean boomTime = false;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    hour = timePicker.getHour();
                    minute = timePicker.getMinute();
                }

                switch (cal.get(Calendar.DAY_OF_WEEK)){
                    case 2 :
                        day = "mon";
                        break;
                    case 3 :
                        day = "tue";
                        break;
                    case 4 :
                        day = "wed";
                        break;
                    case 5 :
                        day = "thr";
                        break;
                    case 6 :
                        day = "fri";
                        break;
                    default :
                        day = "fri"; // default는 friday로
                }

                classTime = hour - 8;

                if(classTime < 1 || classTime > 9){
                    classTime = 9; // default는 9
                }

                if(minute >= 0 && minute <= 10){
                    boomTime = true;
                } else if(minute >= 40 && minute <60){
                    classTime++;
                    boomTime = true;
                } else {
                    boomTime = false;
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    intent.putExtra("DAY_OF_WEEK", day);
                    intent.putExtra("HOUR", timePicker.getHour());
                    intent.putExtra("MINUTE", timePicker.getMinute());
                    intent.putExtra("BOOMTIME", boomTime);
                    intent.putExtra("CLASSTIME", classTime);
                }
                intent.putExtra("START", start.getText());

                if(middle.getText().equals("경유지 선택")) {
                    intent.putExtra("MIDDLE", "");
                } else{
                    intent.putExtra("MIDDLE", middle.getText());
                }

                intent.putExtra("END", end.getText());
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

    private void setData() {
        ParentData underground6 = new ParentData("지하6층");
        underground6.child = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.underground6)));

        ParentData underground5 = new ParentData("지하5층");
        underground5.child = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.underground5)));

        ParentData underground4 = new ParentData("지하4층");
        underground4.child = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.underground4)));

        ParentData underground3 = new ParentData("지하3층");
        underground3.child = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.underground3)));

        ParentData underground2 = new ParentData("지하2층");
        underground2.child = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.underground2)));

        ParentData underground1 = new ParentData("지하1층");
        underground1.child = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.underground1)));

        ParentData ground1 = new ParentData("지상1층");
        ground1.child = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.ground1)));

        ParentData ground3 = new ParentData("지상3층");
        ground3.child = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.ground3)));

        ParentData ground4 = new ParentData("지상4층");
        ground4.child = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.ground4)));

        ParentData ground5 = new ParentData("지상5층");
        ground5.child = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.ground5)));

        ParentData ground6 = new ParentData("지상6층");
        ground6.child = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.ground6)));

        ParentData ground7 = new ParentData("지상7층");
        ground7.child = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.ground7)));

        ParentData ground8 = new ParentData("지상8층");
        ground8.child = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.ground8)));

        ParentData ground9 = new ParentData("지상9층");
        ground9.child = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.ground9)));

        ParentData cancel = new ParentData("취소");
        data.add(cancel);
        data.add(underground6);
        data.add(underground5);
        data.add(underground4);
        data.add(underground3);
        data.add(underground2);
        data.add(underground1);
        data.add(ground1);
        data.add(ground3);
        data.add(ground4);
        data.add(ground5);
        data.add(ground6);
        data.add(ground7);
        data.add(ground8);
        data.add(ground9);
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
