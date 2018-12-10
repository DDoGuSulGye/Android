package com.DataStructure.cau310navi;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.DataStructure.cau310navi.Activity.MainActivity;
import com.DataStructure.cau310navi.Activity.ResultActivity;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        Handler handler = new Handler(){
          public void handleMessage(Message msg){
              super.handleMessage(msg);
              Intent i = new Intent(LoadingActivity.this, MainActivity.class);
              startActivity(i);
              finish();
          }
        };
        handler.sendEmptyMessageDelayed(0,3000);
    }
}
