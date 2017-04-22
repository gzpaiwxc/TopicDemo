package com.topicdemo.activity;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.topicdemo.R;

import java.io.IOException;

import static android.R.attr.path;

/**
 * Created by THL on 2017/4/11.
 */

public class ProgressBarActivity extends Activity implements View.OnClickListener {

    private ProgressBar progressBar;
    private ImageView imgPlay;
    private boolean flag;
    private int i = 0;
    private int j = 0;

    private MediaPlayer mediaPlayer;
    private String path = "";

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (progressBar.getProgress() < 100) {
                progressBar.setProgress(i++);
            } else {
                Log.d("MainActivity", "progress==>" + progressBar.getProgress());
                imgPlay.setImageResource(R.mipmap.play);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progressbar);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        imgPlay = (ImageView) findViewById(R.id.play_pause);
        imgPlay.setOnClickListener(this);
        init();
    }

    private void init() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepareAsync();
            final int millisecond = 100;

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(millisecond);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mHandler.sendEmptyMessage(0);
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_pause:
                imgPlay.setImageResource(R.mipmap.pause);
                progressBar.setProgress(0);
                i = 0;
                Log.d("MainActivity", "progress==>" + progressBar.getProgress());
                flag = true;
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            while (flag) {
                                if (i < 110) {
                                    Thread.sleep(60);
                                    i++;
                                    mHandler.sendEmptyMessage(0);
                                } else {
                                    flag = false;
                                }
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
        }
    }


    //播放
    private void play() {
        mediaPlayer.start();
    }

    //暂停
    private void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
