package com.example.tian.activityrecognization.music;

import android.content.Context;
import android.media.MediaPlayer;
import android.provider.MediaStore;

import com.example.tian.activityrecognization.R;

/**
 * Created by tupac on 12/8/2016.
 */

public class AudioPlayer {

    private MediaPlayer mediaPlayer;

    public void stop(){
        if (mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void play(Context c){
        mediaPlayer = MediaPlayer.create(c, R.raw.beat_02);
        mediaPlayer.start();
    }
}
