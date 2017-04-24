package com.topicdemo.utils;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ProgressBar;

import com.topicdemo.R;

import org.xutils.common.util.LogUtil;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by THL on 2017/4/24.
 */

public class PlayUtils implements MediaPlayer.OnBufferingUpdateListener,MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {

    private ProgressBar mProgressBar;
    public MediaPlayer mediaPlayer;
    private Timer mTimer = new Timer();

    public PlayUtils(ProgressBar progressBar) {
        this.mProgressBar = progressBar;
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnBufferingUpdateListener(this);
            mediaPlayer.setOnPreparedListener(this);
            LogUtils.e("----------new MediaPlayer()------------");

        } catch (Exception e) {
            Log.e("topicdemo", "error:" + e);
        }
        mTimer.schedule(timerTask, 0, 50);
    }


    /**通过定时器和Handler来更新进度条*/
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            if (mediaPlayer == null) {
                return;
            }
            if (mediaPlayer.isPlaying() && mProgressBar.isPressed() == false) {
                mHandler.sendEmptyMessage(0);
            }
        }
    };

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    int position=0;
                    int time = 0;
                    int max = 0;
//                    if (mediaPlayer != null) {
                        position = mediaPlayer.getCurrentPosition();
                        time = mediaPlayer.getDuration();
                        max = mProgressBar.getMax();
                        mProgressBar.setProgress(position * max / time);
//                    }
                    LogUtils.d( "position==>" + position + "  time==>" + time + "  max==>" + max);
                    break;
                case 1:
                    mProgressBar.setProgress(0);
                    break;
            }
        }
    };

    public void playUrl(String url) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            mediaPlayer.setOnCompletionListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //播放
    public void play() {
        mediaPlayer.start();
    }

    //暂停
    public void pause() {
        mediaPlayer.pause();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        LogUtils.d("onBufferingUpdate");
//        mProgressBar.setSecondaryProgress(percent);
        int currentProgress = mProgressBar.getMax()
                * mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration();
//        mProgressBar.setProgress(currentProgress);
        Log.e(currentProgress + "% play", percent + "% buffer");
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
//        Message msg = mHandler.obtainMessage();
//        msg.what = 1;
//        mHandler.sendMessage(msg);
        LogUtils.d("onCompletion");
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        LogUtils.d("onPrepared");
    }
}
