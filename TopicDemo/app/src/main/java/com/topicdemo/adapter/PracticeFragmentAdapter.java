package com.topicdemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.topicdemo.fragment.BlankFragment;
import com.topicdemo.fragment.SingleChooseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by THL on 2017/4/12.
 */

public class PracticeFragmentAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments;

    public PracticeFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
