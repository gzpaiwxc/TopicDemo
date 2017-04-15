package com.topicdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.topicdemo.R;

/**
 * Created by THL on 2017/4/11.
 */

public class ProgressBarActivity extends Activity implements View.OnClickListener {

    private ProgressBar progressBar;
    private ImageView imgPlay;
    private boolean flag;
    private int i = 0;
    private int j = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progressbar);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        imgPlay = (ImageView) findViewById(R.id.play_pause);
        imgPlay.setOnClickListener(this);
    }

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
}
