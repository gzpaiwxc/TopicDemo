package com.topicdemo.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.topicdemo.R;
import com.topicdemo.adapter.PracticeFragmentAdapter;
import com.topicdemo.fragment.BlankFragment;
import com.topicdemo.fragment.ListenSortFragment;
import com.topicdemo.fragment.SingleChooseFragment;
import com.topicdemo.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;
import static android.R.attr.fragment;
import static android.R.attr.type;
import static com.topicdemo.R.id.viewpager;

public class PracticeActivity extends AppCompatActivity implements View.OnClickListener{
    /**返回*/
    private RelativeLayout rlCancel;
    /**收藏*/
    private RelativeLayout rlCollection;
    /**知识目标*/
    private RelativeLayout rlTip;
    /**答题卡*/
    private RelativeLayout rlAnswerSheet;
    private TextView tvCurrentTitle, tvTitleCount, tvTItle;


    private ViewPager viewPager;

    private PracticeFragmentAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        initView();
    }

    private void initView() {

        rlCancel = (RelativeLayout) findViewById(R.id.rl_cancel);
        rlCollection = (RelativeLayout) findViewById(R.id.rl_collection);
        rlTip = (RelativeLayout) findViewById(R.id.rl_tip);
        rlAnswerSheet = (RelativeLayout) findViewById(R.id.rl_answer_sheet);
        rlCancel.setOnClickListener(this);
        rlCollection.setOnClickListener(this);
        rlTip.setOnClickListener(this);
        rlAnswerSheet.setOnClickListener(this);
        tvCurrentTitle = (TextView) findViewById(R.id.text_title_curr);
        tvTitleCount = (TextView) findViewById(R.id.text_title_count);
        tvTItle = (TextView) findViewById(R.id.text_title);
        viewPager = (ViewPager) findViewById(viewpager);
        initViewPager();
    }

    private void initViewPager() {
        mAdapter = new PracticeFragmentAdapter(getSupportFragmentManager(),getFragment());
        tvCurrentTitle.setText(1 + "");
        tvTitleCount.setText("/"+fragments.size());
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setOnPageChangeListener(pageListener);
    }

    private List<Fragment> fragments = new ArrayList<>();
    private List<Fragment> getFragment() {

        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                SingleChooseFragment singleChooseFragment = new SingleChooseFragment();
                fragments.add(singleChooseFragment);
            } else if (i == 1) {
                ListenSortFragment listenSortFragment = new ListenSortFragment();
                fragments.add(listenSortFragment);
            }
        }
        return fragments;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_cancel:
                this.finish();
                break;
            case R.id.rl_collection:
               ToastUtils.showToast(this,"收藏");
                break;
            case R.id.rl_tip:
                ToastUtils.showToast(this,"知识目标");
                break;
            case R.id.rl_answer_sheet:
                ToastUtils.showToast(this,"答题卡");
                break;
        }
    }


    /**
     * ViewPager切换监听方法.
     */
    private ViewPager.OnPageChangeListener pageListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int position) {
            slideMove(position);
        }
    };

    private void slideMove(int position) {
        viewPager.setCurrentItem(position);
        tvCurrentTitle.setText((position + 1)+"");

    }


}
