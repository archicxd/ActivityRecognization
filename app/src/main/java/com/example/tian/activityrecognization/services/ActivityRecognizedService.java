package com.example.tian.activityrecognization.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.tian.activityrecognization.activities.MapsActivity;
import com.example.tian.activityrecognization.activities.NewScreenActivity;
import com.example.tian.activityrecognization.sqlite.DBHelper;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.List;

/**
 * Created by tian on 11/26/16.
 */

public class ActivityRecognizedService extends IntentService {

    public static final String TAG = ActivityRecognizedService.class.getSimpleName();
    DBHelper dbHelper;

    public ActivityRecognizedService() {
        super("ActivityRecognizedService");
    }

    public ActivityRecognizedService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(ActivityRecognitionResult.hasResult(intent)) {
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            handleDetectedActivities( result.getProbableActivities() );
        }
    }

    private void handleDetectedActivities(List<DetectedActivity> probableActivities) {

        dbHelper = new DBHelper(this);

        for( DetectedActivity activity : probableActivities ) {
            switch( activity.getType() ) {
                case DetectedActivity.IN_VEHICLE: {
                    String activity_name = "In Vehicle";
                    Log.e(TAG, activity_name + ": " + activity.getConfidence() );
                    insertAcitivity(dbHelper.getID(), activity_name,activity.getConfidence());

                    if (MapsActivity.getInstance() != null){
                        MapsActivity.getInstance().finish();
                    }

                    Intent intent = new Intent(getApplicationContext(), NewScreenActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Bundle bundle = new Bundle();
                    bundle.putString("start_time", String.valueOf(System.currentTimeMillis()));
                    bundle.putString("activity_name", activity_name);
                    startActivity(intent);

                    break;
                }
                case DetectedActivity.ON_BICYCLE: {
                    String activity_name = "On Bicycle";
                    Log.e(TAG, activity_name + ": " + activity.getConfidence() );
                    insertAcitivity(dbHelper.getID(), activity_name,activity.getConfidence());

                    if (MapsActivity.getInstance() != null){
                        MapsActivity.getInstance().finish();
                    }

                    Intent intent = new Intent(getApplicationContext(), NewScreenActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Bundle bundle = new Bundle();
                    bundle.putString("start_time", String.valueOf(System.currentTimeMillis()));
                    bundle.putString("activity_name", activity_name);
                    startActivity(intent);
                    break;
                }
                case DetectedActivity.ON_FOOT: {
                    String activity_name = "On Foot";
                    Log.e(TAG, activity_name + ": " + activity.getConfidence() );
                    insertAcitivity(dbHelper.getID(), activity_name,activity.getConfidence());

                    if (MapsActivity.getInstance() != null){
                        MapsActivity.getInstance().finish();
                    }

                    Intent intent = new Intent(getApplicationContext(), NewScreenActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Bundle bundle = new Bundle();
                    bundle.putString("start_time", String.valueOf(System.currentTimeMillis()));
                    bundle.putString("activity_name", activity_name);
                    startActivity(intent);
                    break;
                }
                case DetectedActivity.RUNNING: {
                    String activity_name = "Running";
                    Log.e(TAG, activity_name + ": " + activity.getConfidence() );
                    insertAcitivity(dbHelper.getID(), activity_name,activity.getConfidence());

                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                    break;
                }
                case DetectedActivity.STILL: {
                    String activity_name = "Still";
                    Log.e(TAG, activity_name + ": " + activity.getConfidence() );
                    insertAcitivity(dbHelper.getID(), activity_name,activity.getConfidence());

                    if (MapsActivity.getInstance() != null){
                        MapsActivity.getInstance().finish();
                    }

                    Intent intent = new Intent(getApplicationContext(), NewScreenActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Bundle bundle = new Bundle();
                    bundle.putString("start_time", String.valueOf(System.currentTimeMillis()));
                    bundle.putString("activity_name", activity_name);
                    startActivity(intent);
                    break;
                }
                case DetectedActivity.TILTING: {
                    String activity_name = "Tilting";
                    Log.e(TAG, activity_name + ": " + activity.getConfidence() );
                    insertAcitivity(dbHelper.getID(), activity_name,activity.getConfidence());

                    if (MapsActivity.getInstance() != null){
                        MapsActivity.getInstance().finish();
                    }

                    Intent intent = new Intent(getApplicationContext(), NewScreenActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Bundle bundle = new Bundle();
                    bundle.putString("start_time", String.valueOf(System.currentTimeMillis()));
                    bundle.putString("activity_name", activity_name);
                    startActivity(intent);
                    break;
                }
                case DetectedActivity.WALKING: {
                    String activity_name = "Walking";
                    Log.e(TAG, activity_name + ": " + activity.getConfidence() );
                    insertAcitivity(dbHelper.getID(), activity_name,activity.getConfidence());

                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

//                    if( activity.getConfidence() >= 75 ) {
//                        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
//                        builder.setContentText( "Are you walking?" );
//                        builder.setSmallIcon( R.mipmap.ic_launcher );
//                        builder.setContentTitle( getString( R.string.app_name ) );
//                        NotificationManagerCompat.from(this).notify(0, builder.build());
//
//
//                    }

                    break;
                }
                case DetectedActivity.UNKNOWN: {
                    Log.e(TAG, "Unknown: " + activity.getConfidence() );
                    break;
                }
            }
        }
    }

    private void insertAcitivity(int id, String actName, int confidence) {

        //insert activity into the database
        dbHelper.insertActivity(id,
                actName,
                confidence,
                System.currentTimeMillis());
    }
}
