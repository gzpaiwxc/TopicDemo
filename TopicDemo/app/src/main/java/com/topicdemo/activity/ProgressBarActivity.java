package com.topicdemo.activity;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.topicdemo.R;
import com.topicdemo.utils.PlayUtils;

import java.io.IOException;

/**
 * Created by THL on 2017/4/11.
 */

public class ProgressBarActivity extends Activity implements View.OnClickListener {

    private static final int IS_COMPLETE = 1;
    private ProgressBar progressBar;
    private ImageView imgPlay;
    private boolean flag;
    private int i = 0;
    private int j = 0;

    private MediaPlayer mediaPlayer;
//    private String path = "Brave.mp3";
//    private String path = "AA.mp3";
    private String path = "https://atest1.bafangtang.com/v1.0/resource/test-bank/file/group1/M00/00/10/L11QMljtwUyATdqfAADMFQahpuQ1014777.mp3";
//    private String path = "http://file.kuyinyun.com/group1/M00/90/B7/rBBGdFPXJNeAM-nhABeMElAM6bY151.mp3";

    private boolean isPlaying;
    final int millisecond = 100;

    /**
     * -----------------------------------------------
     */
    private PlayUtils player;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            if (progressBar.getProgress() < 100) {
//                progressBar.setProgress(i++);
//            } else {
//                Log.d("MainActivity", "progress==>" + progressBar.getProgress());
//                imgPlay.setImageResource(R.mipmap.play);
//            }
            switch (msg.what) {
                case 0:
                    int position=0;
                    int time = 0;
                    int max = 0;
                    if (mediaPlayer != null) {
                        position = mediaPlayer.getCurrentPosition();
                        time = mediaPlayer.getDuration();
                        max = progressBar.getMax();
                        progressBar.setProgress(position * max / time);
                    }
                    Log.d("topicdemo", "position==>" + position + "  time==>" + time + "  max==>" + max);
                    break;
                case IS_COMPLETE:
                    isPlaying = false;
                    progressBar.setProgress(0);
                    imgPlay.setImageResource(R.mipmap.play);
                    break;
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
        mediaPlayer = new MediaPlayer();
        player = new PlayUtils(progressBar);
//        init();

    }

    private void init() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        try {
//            AssetFileDescriptor assetFileDescriptor = getAssets().openFd(path);
            if (mediaPlayer != null) {
                mediaPlayer.reset();
            }
//            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
//            mediaPlayer.prepare();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepareAsync();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initMediaOne( String path, final Handler mHandler) {
        try {
            if (path == null || path.equals("")) {
                return;
            }
            if (mediaPlayer == null) {
                mediaPlayer = new MediaPlayer();
            }
            AssetFileDescriptor assetFileDescriptor = getAssets().openFd(path);
            mediaPlayer.reset();
            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (mediaPlayer != null) {
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    Message msg = mHandler.obtainMessage();
                    msg.what = IS_COMPLETE;
                    mHandler.sendMessage(msg);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.play_pause:
//                imgPlay.setImageResource(R.mipmap.pause);
//                progressBar.setProgress(0);
//                i = 0;
//                Log.d("MainActivity", "progress==>" + progressBar.getProgress());
//                flag = true;
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        try {
//                            while (flag) {
//                                if (i < 110) {
//                                    Thread.sleep(60);
//                                    i++;
//                                    mHandler.sendEmptyMessage(0);
//                                } else {
//                                    flag = false;
//                                }
//                            }
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();
//
//                break;
//        }
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_pause:
//                if (!isPlaying) {
//                    play();
//                } else {
//                    pause();
//                }
//
//                final int millisecond = 100;
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        while (isPlaying) {
//                            try {
//                                Thread.sleep(millisecond);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                            Log.d("topicdemo", "一直在循环");
//                            mHandler.sendEmptyMessage(0);
//                        }
//                    }
//                }).start();
                if (!isPlaying) {
                    play();
                } else {
                    pause();
                }


                break;
        }
    }

    private Runnable playRunnable = new Runnable() {
        @Override
        public void run() {
            while (isPlaying) {
                try {
                    Thread.sleep(millisecond);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(0);
            }
        }
    };

//    //播放
//    private void play() {
//        if (progressBar.getProgress() != 0) {
//            mediaPlayer.start();
//            isPlaying = true;
//            imgPlay.setImageResource(R.mipmap.pause);
//            return;
//        }
//        initMediaOne(path, mHandler);
//        ToastUtils.showToast(this, "播放");
//        Log.d("topicdemo", "isPlaying==>" + mediaPlayer.isPlaying());
//        isPlaying = true;
//        imgPlay.setImageResource(R.mipmap.pause);
//    }
//
//    //暂停
//    private void pause() {
//        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
//            mediaPlayer.pause();
//            Log.d("topicdemo", "isPlaying==>" + mediaPlayer.isPlaying());
//            ToastUtils.showToast(this, "暂停");
//            isPlaying = false;
//            imgPlay.setImageResource(R.mipmap.play);
//        }
//    }

    //播放
    private void play() {
//        if (progressBar.getProgress() != 0) {
//            mediaPlayer.start();
//            isPlaying = true;
//            imgPlay.setImageResource(R.mipmap.pause);
//            return;
//        }
//        initMediaOne(path, mHandler);
//        ToastUtils.showToast(this, "播放");
//        Log.d("topicdemo", "isPlaying==>" + mediaPlayer.isPlaying());
//        isPlaying = true;
//        imgPlay.setImageResource(R.mipmap.pause);
        player.playUrl(path);
        player.play();
        isPlaying = true;
        imgPlay.setImageResource(R.mipmap.pause);

    }

    //暂停
    private void pause() {
//        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
//            mediaPlayer.pause();
//            Log.d("topicdemo", "isPlaying==>" + mediaPlayer.isPlaying());
//            ToastUtils.showToast(this, "暂停");
//            isPlaying = false;
//            imgPlay.setImageResource(R.mipmap.play);
//        }
        player.pause();
        isPlaying = false;
        imgPlay.setImageResource(R.mipmap.play);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            isPlaying = false;
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
