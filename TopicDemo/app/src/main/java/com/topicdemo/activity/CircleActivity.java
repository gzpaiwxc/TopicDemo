package com.topicdemo.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.topicdemo.R;

public class CircleActivity extends Activity implements View.OnClickListener{
    private TextView text1,textA, textB, textC;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);
        mContext = this;
        text1 = (TextView) findViewById(R.id.text1);
        textA = (TextView) findViewById(R.id.textA);
        textB = (TextView) findViewById(R.id.textB);
        textC = (TextView) findViewById(R.id.textC);

        text1.setOnClickListener(this);
        textA.setOnClickListener(this);
        textB.setOnClickListener(this);
        textC.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text1:
                text1.setBackgroundResource(R.drawable.circle_red);
                break;
            case R.id.textA:
                Toast.makeText(mContext, "this is circleText A!", Toast.LENGTH_SHORT).show();
                textA.setBackgroundResource(R.drawable.circle_red);
                break;
            case R.id.textB:
                Toast.makeText(mContext, "this is circleText B!", Toast.LENGTH_SHORT).show();
                textB.setBackgroundResource(R.drawable.circle_green);
                break;
            case R.id.textC:
                textC.setTextColor(getResources().getColor(R.color.white));
                textC.setBackgroundResource(R.drawable.circle_red);
                break;

        }

    }
}
