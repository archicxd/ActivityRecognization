package com.example.tian.activityrecognization.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.tian.activityrecognization.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewScreenActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        String startTime  = i.getStringExtra("start_time");
        String activityName = i.getStringExtra("activity_name");


        TextView timeView = (TextView) findViewById(R.id.timeTextView);

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date endStartDate = new Date(Long.parseLong(startTime));
        String date = sdf.format(endStartDate);
        timeView.setText("Start Time: " + date);

        TextView nameView = (TextView) findViewById(R.id.nameTextView);
        nameView.setText("Activity Name: " + activityName);



    }

}
