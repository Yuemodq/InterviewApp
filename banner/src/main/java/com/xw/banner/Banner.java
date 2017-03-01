package com.xw.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xw.banner.view.BannerViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 当选择标题显示时，只支持指示器靠右显示。<br/>
 * 指示器靠左或者居中显示时，不显示标题。
 * <br/>
 * 作者：XW <br/>
 * 邮箱：xw_appdev@163.com <br/>
 * 时间：2017-03-02 01:05
 */

public class Banner extends FrameLayout {

    private String tag = "Banner";

    private Context context;

    //ViewPager
    private BannerViewPager bvp_banner;

    //banner 标题
    private TextView tv_banner_title;

    //banner指示器
    private LinearLayout ll_banner_indicator;

    //banner 图片资源
    private List mBannerResoures;

    //banner 标题
    private List<String> mBannerTitles;

    //banner 指示器
    private List<ImageView> mBannerIndicators;

    //banner 视图View(ImageView)
    private List<ImageView> mBannerViews;

    //屏幕度量
    private DisplayMetrics mDisplayMetrics;

    //指示器宽度
    private int mIndicatorWidth;

    //指示器高度
    private int mIndicatorHeight;

    private int mIndicatorSize;

    private int mIndicatorMargin = BannerConfig.PADDING_SIZE;

    private int mIndicatorSelectedResId = BannerConfig.INDICATOR_SELECTED_RESOURCE_ID;

    private int mIndicatorUnSelectedResId = BannerConfig.INDICATOR_UNSELECTED_RESOURCE_ID;

    private int mScaleType = 1;

    private int mDelayTime = BannerConfig.DELAY_TIME;

    private int mScrollTime = BannerConfig.DURATION;

    private boolean isAutoPlay = BannerConfig.IS_AUTO_PLAY;

    private int mTitleHeight;

    private int mTitleBackground;

    private int mTitleTextColor;

    private int mTitleTextSize = BannerConfig.INDICATOR_DEFAULT_SIZE;

    private int mCount;

    private int mCurrentPosition;

    private int mLastPosition;

    private int mIndicatorGravity;

    public Banner(Context context) {
        this(context, null);
    }

    public Banner(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        mBannerViews = new ArrayList<>();
        mBannerTitles = new ArrayList<>();
        mBannerResoures = new ArrayList<>();
        mBannerIndicators = new ArrayList<>();
        mDisplayMetrics = context.getResources().getDisplayMetrics();
        mIndicatorSize = (int) (mIndicatorSize * mDisplayMetrics.density);
        init(context);
        initTypedArray(context, attrs);
    }

    /**
     * 初始化
     * @param context 上下文
     */
    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.lib_layout_banner, this, true);
        bvp_banner = (BannerViewPager) view.findViewById(R.id.bvp_banner);
        tv_banner_title = (TextView) view.findViewById(R.id.tv_banner_title);
        ll_banner_indicator = (LinearLayout) view.findViewById(R.id.ll_banner_indicator);
    }

    /**
     * 初始化自定义属性
     * @param context 上下文
     * @param attrs 属性集
     */
    private void initTypedArray(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Banner);
        mDelayTime = a.getInteger(R.styleable.Banner_delay_time, BannerConfig.DELAY_TIME);
        // TODO: 2017-03-02 02:55
        a.recycle();
    }
}


























