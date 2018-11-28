package com.DataStructure.cau310navi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.DataStructure.cau310navi.Activity.MainActivity;

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
