package com.xw.interviewapp.ui.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 作者：XW
 * 邮箱：xw_appdev@163.com
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
