package com.DataStructure.cau310navi;

import android.content.Intent;
import android.os.Handler;
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

        Intent i = new Intent(LoadingActivity.this, MainActivity.class);
        startActivity(i);
        finish();

    }
}
