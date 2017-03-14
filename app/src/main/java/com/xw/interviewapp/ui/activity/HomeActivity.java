package com.xw.interviewapp.ui.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.xw.interviewapp.R;
import com.xw.interviewapp.ui.adapter.HomePagerFragmentAdapter;
import com.xw.interviewapp.ui.fragment.HomeFragment;
import com.xw.interviewapp.ui.fragment.MapFragment;
import com.xw.interviewapp.ui.fragment.MeFragment;
import com.xw.interviewapp.ui.fragment.MediaFragment;
import com.xw.interviewapp.ui.view.FooterIndicatorGroup;
import com.xw.interviewapp.ui.view.FooterSlideGradualnessView;

import java.util.ArrayList;
import java.util.List;

/**
 * 主活动类，承载4个功能分区碎片（fragment）
 * <br/>
 * 作者：XW <br/>
 * 邮箱：xw_appdev@163.com <br/>
 * 时间：2017-02-21
 */

public class HomeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener,
        FooterIndicatorGroup.OnClickSwitchListener {
    
    private ViewPager vp_content;

    private FooterIndicatorGroup fig_footer_group;

    private List<Fragment> mFragments;
    
    private int mLastPosition = 0;
    
    private int mCurrentPosition = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
            );
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
//                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.BLACK);
        }
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
        fig_footer_group = (FooterIndicatorGroup) findViewById(R.id.fig_footer_group);
        fig_footer_group.setOnClickSwitchListener(this);
        fig_footer_group.getFooterSlideGradualnessViews().get(0).setGradualAlpha(1.0f);
        vp_content = (ViewPager) findViewById(R.id.vp_content);
        vp_content.setAdapter(new HomePagerFragmentAdapter(getSupportFragmentManager(), mFragments));
        vp_content.addOnPageChangeListener(this);
        
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset > 0) {
            FooterSlideGradualnessView left = fig_footer_group.getFooterSlideGradualnessViews().get(position);
            FooterSlideGradualnessView right = fig_footer_group.getFooterSlideGradualnessViews().get(position + 1);
            left.setGradualAlpha(1 - positionOffset);
            right.setGradualAlpha(positionOffset);
        }
    }

    @Override
    public void onPageSelected(int position) {
        mCurrentPosition = position;
        fig_footer_group.getFooterSlideGradualnessViews().get(mLastPosition).setGradualAlpha(0);
        fig_footer_group.getFooterSlideGradualnessViews().get(mCurrentPosition).setGradualAlpha(1);
        mLastPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClickSwitch(int position) {
        vp_content.setCurrentItem(position);
    }
}


















