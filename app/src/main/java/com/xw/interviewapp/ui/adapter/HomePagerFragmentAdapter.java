package com.xw.interviewapp.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * <br/>
 * 作者：XW <br/>
 * 邮箱：xw_appdev@163.com <br/>
 * 时间：2017-02-23 23:59
 */

public class HomePagerFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;

    public HomePagerFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
