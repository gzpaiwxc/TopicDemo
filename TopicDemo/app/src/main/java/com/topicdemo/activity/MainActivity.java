package com.topicdemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.topicdemo.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener{
    private RelativeLayout rlCancel;
    private ListView listView;
    private String[] strings = {"progressbar", "practice","circleTextView"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {

        rlCancel = (RelativeLayout) findViewById(R.id.rl_cancel);
        rlCancel.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(this);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, strings);
        listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent;
        switch (position) {
            case 0:
                Toast.makeText(MainActivity.this, strings[position], Toast.LENGTH_SHORT).show();
                intent = new Intent(MainActivity.this, ProgressBarActivity.class);
                startActivity(intent);
                break;
            case 1:
                Toast.makeText(MainActivity.this, strings[position], Toast.LENGTH_SHORT).show();
                intent = new Intent(MainActivity.this, PracticeActivity.class);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(MainActivity.this, CircleActivity.class);
                startActivity(intent);
                break;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_cancel:
                this.finish();
                break;
        }
    }
}
