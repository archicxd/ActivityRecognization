package com.example.tian.activityrecognization.activities;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.tian.activityrecognization.music.AudioPlayer;
import com.example.tian.activityrecognization.services.ActivityRecognizedService;
import com.example.tian.activityrecognization.R;
import com.example.tian.activityrecognization.sqlite.DBHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public GoogleApiClient mApiClient;

    ListView listView;
    DBHelper dbHelper;

    private AudioPlayer mPlayer = new AudioPlayer();

    //amt of hours you want to get results for
    int hours = 24;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mApiClient = new GoogleApiClient.Builder(this)
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        Button summaryBtn = (Button) findViewById(R.id.get_summary_btn);
        summaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, NewScreenActivity.class);
                intent.putExtra("start_time", String.valueOf(System.currentTimeMillis()));
                intent.putExtra("activity_name", "Still");
                startActivity(intent);
            }
        });


        Button playMusicBtn = (Button) findViewById(R.id.play_music_btn);
        playMusicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.play(MainActivity.this);
            }
        });

        Button stopMusicBtn = (Button) findViewById(R.id.stop_music_btn);
        stopMusicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.stop();
            }
        });


//        Button summaryBtn = (Button) findViewById(R.id.buttonSummary);
//        summaryBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                TextView startTimeTextView = (TextView) findViewById(R.id.start_time);
//                TextView endTimeTextView = (TextView) findViewById(R.id.end_time);
//
//
//                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
//
//                long endTime = System.currentTimeMillis();
//                Date endResultDate = new Date(endTime);
//                String date = sdf.format(endResultDate);
//                endTimeTextView.setText("End Time: " + date);
//
//
//                long DAY = hours * 60 * 60 * 1000;
//                long past = System.currentTimeMillis() - DAY;
//                Date startResultDate = new Date(past);
//                String startDate = sdf.format(startResultDate);
//                startTimeTextView.setText("Start Time: " + startDate);
//
//                //get database reference
//                dbHelper = new DBHelper(MainActivity.this);
//
//                final Cursor cursor = dbHelper.getAllActivitiesFor(hours);
//
//
//                String[] columns = new String[] {
//                        dbHelper.ACTIVITY_ID,
//                        dbHelper.ACTIVITY_NAME,
//                        dbHelper.ACTIVITY_TIME
//                };
//                int [] widgets = new int[] {
//                        R.id._id,
//                        R.id.a_name,
//                        R.id.a_time
//                };
//
//
//                SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(MainActivity.this, R.layout.activity_list_item, cursor, columns, widgets, 0);
//
//                cursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
//                    @Override
//                    public boolean setViewValue(View view, Cursor cursor, int column) {
//                        if( column == 2 ){ // let's suppose that the column 0 is the date
//                            TextView tv = (TextView) view;
//                            long time = cursor.getLong(cursor.getColumnIndex("a_time"));
//                            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
//                            Date resultDate = new Date(time);
//                            String date = sdf.format(resultDate);
//                            tv.setText(date);
//                            return true;
//                        }
//                        return false;
//                    }
//                });
//
//                listView = (ListView) findViewById(R.id.listView);
//                listView.setAdapter(cursorAdapter);
//            }
//        });


    }

    @Override
    protected void onStart() {
        mApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mApiClient.disconnect();
        super.onStop();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {


        Intent intent = new Intent( this, ActivityRecognizedService.class );
        PendingIntent pendingIntent = PendingIntent.getService( this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT );
        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates( mApiClient, 3000, pendingIntent );
    }

        @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
