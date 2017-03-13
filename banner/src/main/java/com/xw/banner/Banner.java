package com.xw.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xw.banner.listener.OnBannerListener;
import com.xw.banner.loader.ImageLoader;
import com.xw.banner.view.BannerViewPager;

import java.lang.reflect.Field;
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

public class Banner extends FrameLayout implements ViewPager.OnPageChangeListener {

    private String tag = "Banner";

    private Context context;

    //ViewPager
    private BannerViewPager bvp_banner;

    //banner 标题
    private TextView tv_title;

    //banner指示器
    private LinearLayout ll_indicator_no_title;
    
    private LinearLayout ll_title_container;
    
    private LinearLayout ll_indicator_with_title;

//    private FrameLayout fl_title_and_indicator;
//
//    private FrameLayout.LayoutParams mFrameLayoutParams;

    //banner 图片资源
    private List mBannerResources;

    //banner 标题
    private List<String> mBannerTitles;

    //banner 指示器
    private List<ImageView> mBannerIndicators;

    //banner 视图View(ImageView)
    private List<ImageView> mBannerViews;

    //屏幕度量
    private DisplayMetrics mDisplayMetrics;

    //指示器宽度
    private int mIndicatorWidth = 20;

    //指示器高度
    private int mIndicatorHeight = 20;

    private int mIndicatorSize;

    private int gravity = -1;

    private int mIndicatorMargin = BannerConfig.PADDING_SIZE;

    private int mIndicatorSelectedResId = BannerConfig.INDICATOR_SELECTED_RESOURCE_ID;

    private int mIndicatorUnSelectedResId = BannerConfig.INDICATOR_UNSELECTED_RESOURCE_ID;

    private int mScaleType = 1;

    private int mDelayTime = BannerConfig.DELAY_TIME;

    private int mScrollTime = BannerConfig.DURATION;

    private boolean isAutoPlay = BannerConfig.IS_AUTO_PLAY;

//    private int mTitleHeight;
    private boolean isShowTitle = BannerConfig.IS_SHOW_TITLE;

    private boolean isScroll = BannerConfig.IS_SCROLL;

    private int mTitleBackground;

    private int mTitleTextColor;

    private int mTitleTextSize = BannerConfig.INDICATOR_DEFAULT_SIZE;

    private int mCount;

    private int mCurrentPosition;

    private int mLastPosition = 1;

    private int mIndicatorGravity = BannerConfig.INDICATOR_GRAVITY_CENTER;

    private BannerPagerAdapter adapter;

    private ImageLoader mImageLoader;

    private ViewPager.OnPageChangeListener mOnPageChangeListener;

    private OnBannerListener mOnBannerListener;

    private Handler handler = new Handler();

    private Runnable task = new Runnable() {
        @Override
        public void run() {
            if (mCount > 1 && isAutoPlay) {
                mCurrentPosition = mCurrentPosition % (mCount + 1) + 1;
                if (mCurrentPosition == 1) {
                    bvp_banner.setCurrentItem(mCurrentPosition, false);
                    handler.post(task);
                } else {
                    bvp_banner.setCurrentItem(mCurrentPosition);
                    handler.postDelayed(task, mDelayTime);
                }
            }
        }
    };

    public Banner(Context context) {
        this(context, null);
    }

    public Banner(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        mBannerViews = new ArrayList<>();
        mBannerTitles = new ArrayList<>();
        mBannerResources = new ArrayList<>();
        mBannerIndicators = new ArrayList<>();
        mDisplayMetrics = context.getResources().getDisplayMetrics();
        mIndicatorSize = (int) (mIndicatorSize * mDisplayMetrics.density);
//        mFrameLayoutParams = new FrameLayout.LayoutParams(context, attrs);
        init(context);
//        initTypedArray(context, attrs);
        initPagerScroll();
    }

    /**
     * 初始化
     * @param context 上下文
     */
    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.lib_layout_banner, this, true);
        bvp_banner = (BannerViewPager) view.findViewById(R.id.bvp_banner);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        ll_indicator_no_title = (LinearLayout) view.findViewById(R.id.ll_indicator_no_title);
        ll_title_container = (LinearLayout) view.findViewById(R.id.ll_title_container);
        ll_indicator_with_title = (LinearLayout) view.findViewById(R.id.ll_indicator_with_title);
//        fl_title_and_indicator = (FrameLayout) view.findViewById(R.id.fl_title_and_indicator);
    }

    /**
     * 初始化自定义属性
     * @param context 上下文
     * @param attrs 属性集
     */
    private void initTypedArray(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Banner);
        mDelayTime = a.getInt(R.styleable.Banner_delay_time, BannerConfig.DELAY_TIME);
        mScrollTime = a.getInt(R.styleable.Banner_scroll_time, BannerConfig.DURATION);
        isAutoPlay = a.getBoolean(R.styleable.Banner_is_auto_play, BannerConfig.IS_AUTO_PLAY);

        mTitleBackground = a.getResourceId(R.styleable.Banner_title_background, BannerConfig.TITLE_BACKGROUND);
        mTitleTextColor = a.getResourceId(R.styleable.Banner_title_textColor, BannerConfig.TITLE_TEXT_COLOR);
        mTitleTextSize = a.getDimensionPixelSize(R.styleable.Banner_title_textSize, BannerConfig.TITLE_TEXT_SIZE);
//        mTitleHeight = a.getDimensionPixelSize(R.styleable.Banner_title_height, BannerConfig.TITLE_HEIGHT);
        isShowTitle = a.getBoolean(R.styleable.Banner_title_isShow, BannerConfig.IS_SHOW_TITLE);
        mIndicatorWidth = a.getDimensionPixelSize(R.styleable.Banner_indicator_width, BannerConfig.INDICATOR_DEFAULT_SIZE);
        mIndicatorHeight = a.getDimensionPixelSize(R.styleable.Banner_indicator_height, BannerConfig.INDICATOR_DEFAULT_SIZE);
        mIndicatorMargin = a.getDimensionPixelSize(R.styleable.Banner_indicator_margin, BannerConfig.PADDING_SIZE);
        mIndicatorSelectedResId = a.getResourceId(R.styleable.Banner_indicator_drawable_selected, BannerConfig.INDICATOR_SELECTED_RESOURCE_ID);
        mIndicatorUnSelectedResId = a.getResourceId(R.styleable.Banner_indicator_drawable_unselected, BannerConfig.INDICATOR_UNSELECTED_RESOURCE_ID);
        mIndicatorGravity = a.getInt(R.styleable.Banner_indicator_gravity, BannerConfig.INDICATOR_GRAVITY_CENTER);
//        Log.d(tag, "banner indicator gravity:" + mIndicatorGravity);
        mScaleType = a.getInt(R.styleable.Banner_image_scale_type, BannerConfig.SCALE_TYPE);
        a.recycle();
    }

    /**
     * 利用反射修改ViewPager滚动时间
     */
    private void initPagerScroll() {
        try {
            Field mField = ViewPager.class.getField("mScroller");
            BannerScroller mScroller = new BannerScroller(bvp_banner.getContext());
            mScroller.setDuration(mScrollTime);
            mField.set(bvp_banner, mScroller);
        } catch (Exception e) {
            Log.e(tag, e.getMessage());
        }
    }

    public Banner isAutoPlay(boolean isAutoPlay) {
        this.isAutoPlay = isAutoPlay;
        return this;
    }

    public Banner setBannerResources(List<?> bannerResources) {
        this.mBannerResources = bannerResources;
        this.mCount = mBannerResources.size();
        return this;
    }

    public Banner setBannerTitles(List<String> bannerTitles) {
        this.mBannerTitles = bannerTitles;
        isShowTitle = true;
        return this;
    }
    
    public Banner setIndicatorGravity(int gravity) {
        this.mIndicatorGravity = gravity;
        return this;
    }
    
    public Banner setIndicatorSelectedResId(int resId) {
        mIndicatorSelectedResId = resId;
        return this;
    }
    
    public Banner setIndicatorUnselectedResId(int resId) {
        mIndicatorUnSelectedResId = resId;
        return this;
    }
    
    public Banner setIndicatorWidth(int width) {
//        mIndicatorWidth = (int) (mDisplayMetrics.density * width);
        mIndicatorWidth = width;
        return this;
    }
    
    public Banner setIndicatorHeight(int height) {
//        mIndicatorHeight = (int) (mDisplayMetrics.density * height);
        mIndicatorHeight = height;
        return this;
    }
    
    public Banner setBannerScaleType(int type) {
        this.mScaleType = type;
        return this;
    }

    public Banner setImageLoader(ImageLoader loader) {
        this.mImageLoader = loader;
        return this;
    }

    public Banner start() {
        setBannerUI();
        createIndicatorViews();
        setBannerImages();
        setData();
        return this;
    }

    private void setData() {
        mCurrentPosition = 1;
        if (adapter == null) {
            adapter = new BannerPagerAdapter();
        }
        bvp_banner.addOnPageChangeListener(this);
        bvp_banner.setAdapter(adapter);
        bvp_banner.setFocusable(true);
        bvp_banner.setCurrentItem(1);
        if(gravity != -1)
            ll_indicator_no_title.setGravity(gravity);
        if (isShowTitle) {
            ll_indicator_no_title.setVisibility(GONE);
            ll_title_container.setVisibility(VISIBLE);
        } else {
            ll_title_container.setVisibility(GONE);
            ll_indicator_no_title.setVisibility(VISIBLE);
        }
        if (isAutoPlay) {
            startAutoPlay();
        }
    }

    public void startAutoPlay() {
        handler.removeCallbacks(task);
        handler.postDelayed(task, mDelayTime);
    }

    public void stopAutoPlay() {
        handler.removeCallbacks(task);
    }

    private void setBannerUI() {
        if (mIndicatorGravity == BannerConfig.INDICATOR_GRAVITY_CENTER) {
            gravity = Gravity.CENTER;
        } else if (mIndicatorGravity == BannerConfig.INDICATOR_GRAVITY_LEFT) {
            gravity = Gravity.START | Gravity.CENTER_VERTICAL;
        } else if (mIndicatorGravity == BannerConfig.INDICATOR_GRAVITY_RIGHT){
            gravity = Gravity.END | Gravity.CENTER_VERTICAL;
            if (isShowTitle) {
                if(mBannerTitles.size() != mBannerResources.size())
                    throw new RuntimeException("The number of titles can't equal images");
                if(mTitleTextSize != -1)
                    tv_title.setTextSize(mTitleTextSize);
                if(mTitleBackground != -1)
                    ll_indicator_with_title.setBackgroundColor(mTitleTextColor);
                if(mTitleTextColor != -1)
                    tv_title.setTextColor(mTitleTextColor);
                if (mBannerTitles != null && mBannerTitles.size() > 0) {
                    tv_title.setText(mBannerTitles.get(0));
                    ll_title_container.setVisibility(View.VISIBLE);
                    ll_indicator_no_title.setVisibility(View.GONE);
                }
            }
        }
        if (isScroll && mCount > 1) {
            bvp_banner.setScrollable(true);
        } else {
            bvp_banner.setScrollable(false);
        }
    }

    private void createIndicatorViews() {
        mBannerIndicators.clear();
        ll_indicator_no_title.removeAllViews();
        ll_indicator_with_title.removeAllViews();
        for (int i = 0; i < mCount; i++) {
            ImageView indicatorView = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mIndicatorWidth, mIndicatorHeight);
            params.leftMargin = mIndicatorMargin;
            params.rightMargin = mIndicatorMargin;
            if (i == 0) {
                indicatorView.setBackgroundResource(mIndicatorSelectedResId);
            } else {
                indicatorView.setBackgroundResource(mIndicatorUnSelectedResId);
            }
            if (isShowTitle) {
                ll_indicator_with_title.addView(indicatorView, params);
            } else {
                ll_indicator_no_title.addView(indicatorView, params);
            }
            mBannerIndicators.add(indicatorView);
        }
    }

    private void setBannerImages() {
        if (mBannerResources == null || mBannerResources.size() <= 0)
            throw new RuntimeException("Not found banner image data!");
        if (mImageLoader == null)
            throw new RuntimeException("Not found image loader");
        mBannerViews.clear();
        for (int i = 0; i <= mCount + 1; i++) {
            ImageView bannerView = mImageLoader.createImageView(context);
            setScaleType(bannerView);
            Object path = null;
            if (i == 0) {
                path = mBannerResources.get(mCount - 1);
            } else if (i == mCount + 1) {
                path = mBannerResources.get(0);
            } else {
                path = mBannerResources.get(i - 1);
            }
            mBannerViews.add(bannerView);
            mImageLoader.displayImage(context, path, bannerView);
        }
    }

    private void setScaleType(ImageView imageView) {
        switch (mScaleType) {
            case 0:
                imageView.setScaleType(ImageView.ScaleType.CENTER);
                break;
            case 1:
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                break;
            case 2:
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                break;
            case 3:
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                break;
            case 4:
                imageView.setScaleType(ImageView.ScaleType.FIT_END);
                break;
            case 5:
                imageView.setScaleType(ImageView.ScaleType.FIT_START);
                break;
            case 6:
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                break;
            case 7:
                imageView.setScaleType(ImageView.ScaleType.MATRIX);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageSelected(position);
        }
        mBannerIndicators.get((mLastPosition - 1 + mCount) % mCount).setBackgroundResource(mIndicatorUnSelectedResId);
        mBannerIndicators.get((position - 1 + mCount) % mCount).setBackgroundResource(mIndicatorSelectedResId);
        mLastPosition = position;
        if (isShowTitle) {
            tv_title.setText(mBannerTitles.get(toRealPosition(position)));
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrollStateChanged(state);
        }
        mCurrentPosition = bvp_banner.getCurrentItem();
        switch (state) {
            case 0://无操作
                if (mCurrentPosition == 0) {
                    bvp_banner.setCurrentItem(mCount, false);
                } else if (mCurrentPosition == mCount + 1) {
                    bvp_banner.setCurrentItem(1, false);
                }
                break;
            case 1://滑动中
                if (mCurrentPosition == mCount + 1) {
                    bvp_banner.setCurrentItem(1, false);
                } else if (mCurrentPosition == 0) {
                    bvp_banner.setCurrentItem(mCount, false);
                }
                break;
            case 2://滑动完成
                break;
        }
    }

    public void setOnBannerListener(OnBannerListener onBannerListener) {
        mOnBannerListener = onBannerListener;
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        mOnPageChangeListener = onPageChangeListener;
    }

    private int toRealPosition(int position) {
        return  (position - 1 + mCount) % mCount;
    }

    private class BannerPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mBannerViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            container.addView(mBannerViews.get(position));
            View view = mBannerViews.get(position);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnBannerListener != null) {
                        mOnBannerListener.onBannerClick(toRealPosition(position));
                    }
                }
            });
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mBannerViews.get(position));
        }
    }

}


























