package com.xw.interviewapp.ui.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.xw.interviewapp.R;
import com.xw.interviewapp.ui.adapter.HomePagerFragmentAdapter;
import com.xw.interviewapp.ui.fragment.HomeFragment;
import com.xw.interviewapp.ui.fragment.MapFragment;
import com.xw.interviewapp.ui.fragment.MeFragment;
import com.xw.interviewapp.ui.fragment.MediaFragment;
import com.xw.interviewapp.ui.view.FooterSlideGradualnessView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-02-21.<br/>
 * 主活动类，承载4个功能分区碎片（fragment）
 */

public class HomeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ViewPager vp_content;

    private FooterSlideGradualnessView fsgv_home, fsgv_map, fsgv_media, fsgv_me;

    private List<FooterSlideGradualnessView> mFooterSlideGradualnessViews = new ArrayList<>();

    private List<Fragment> mFragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initFragments();
        initViews();
    }

    private void initFragments() {
        mFragments = new ArrayList<>();
        HomeFragment homeFragment = new HomeFragment();
        MapFragment mapFragment = new MapFragment();
        MediaFragment mediaFragment = new MediaFragment();
        MeFragment meFragment = new MeFragment();
        mFragments.add(homeFragment);
        mFragments.add(mapFragment);
        mFragments.add(mediaFragment);
        mFragments.add(meFragment);
    }

    private void initViews() {
        fsgv_home = (FooterSlideGradualnessView) findViewById(R.id.fsgv_home);
        fsgv_map = (FooterSlideGradualnessView) findViewById(R.id.fsgv_map);
        fsgv_media = (FooterSlideGradualnessView) findViewById(R.id.fsgv_media);
        fsgv_me = (FooterSlideGradualnessView) findViewById(R.id.fsgv_me);
        mFooterSlideGradualnessViews.add(fsgv_home);
        mFooterSlideGradualnessViews.add(fsgv_map);
        mFooterSlideGradualnessViews.add(fsgv_media);
        mFooterSlideGradualnessViews.add(fsgv_me);
        fsgv_home.setGradualAlpha(1.0f);
        vp_content = (ViewPager) findViewById(R.id.vp_content);
        vp_content.setAdapter(new HomePagerFragmentAdapter(getFragmentManager(), mFragments));
        vp_content.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset > 0) {
            FooterSlideGradualnessView left = mFooterSlideGradualnessViews.get(position);
            FooterSlideGradualnessView right = mFooterSlideGradualnessViews.get(position + 1);
            left.setGradualAlpha(1 - positionOffset);
            right.setGradualAlpha(positionOffset);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}


















