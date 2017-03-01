package com.xw.interviewapp.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.xw.interviewapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 底部导航按钮组合
 * <br/>
 * 作者：XW <br/>
 * 邮箱：xw_appdev@163.com <br/>
 * 时间：2017-02-24
 */

public class FooterIndicatorGroup extends LinearLayout implements View.OnClickListener {

    private FooterSlideGradualnessView fsgv_home, fsgv_map, fsgv_media, fsgv_me;

    private List<FooterSlideGradualnessView> mFooterSlideGradualnessViews = new ArrayList<>();

    private OnClickSwitchListener mOnClickSwitchListener;

    public FooterIndicatorGroup(Context context) {
        this(context, null);
    }

    public FooterIndicatorGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.layout_footer_indicator_group, this, true);
        initViews();
    }

    /**
     * 初始化子控件
     */
    private void initViews() {
        fsgv_home = (FooterSlideGradualnessView) findViewById(R.id.fsgv_home);
        fsgv_map = (FooterSlideGradualnessView) findViewById(R.id.fsgv_map);
        fsgv_media = (FooterSlideGradualnessView) findViewById(R.id.fsgv_media);
        fsgv_me = (FooterSlideGradualnessView) findViewById(R.id.fsgv_me);

        fsgv_home.setOnClickListener(this);
        fsgv_map.setOnClickListener(this);
        fsgv_media.setOnClickListener(this);
        fsgv_me.setOnClickListener(this);

        mFooterSlideGradualnessViews.add(fsgv_home);
        mFooterSlideGradualnessViews.add(fsgv_map);
        mFooterSlideGradualnessViews.add(fsgv_media);
        mFooterSlideGradualnessViews.add(fsgv_me);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fsgv_home:
                if (mOnClickSwitchListener != null) {
                    mOnClickSwitchListener.onClickSwitch(0);
                }
                break;
            case R.id.fsgv_map:
                if (mOnClickSwitchListener != null) {
                    mOnClickSwitchListener.onClickSwitch(1);
                }
                break;
            case R.id.fsgv_media:
                if (mOnClickSwitchListener != null) {
                    mOnClickSwitchListener.onClickSwitch(2);
                }
                break;
            case R.id.fsgv_me:
                if (mOnClickSwitchListener != null) {
                    mOnClickSwitchListener.onClickSwitch(3);
                }
                break;
        }
    }

    /**
     * 获取底部切换按钮集
     * @return 底部切换按钮集
     */
    public List<FooterSlideGradualnessView> getFooterSlideGradualnessViews() {
        return mFooterSlideGradualnessViews;
    }

    /**
     * 设置点击监听
     * @param onClickSwitchListener 点击切换监听
     */
    public void setOnClickSwitchListener(OnClickSwitchListener onClickSwitchListener) {
        mOnClickSwitchListener = onClickSwitchListener;
    }

    public interface OnClickSwitchListener {
        void onClickSwitch(int position);
    }
}

























