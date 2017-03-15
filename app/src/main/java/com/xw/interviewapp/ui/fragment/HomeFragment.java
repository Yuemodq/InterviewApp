package com.xw.interviewapp.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xw.banner.Banner;
import com.xw.banner.BannerConfig;
import com.xw.interviewapp.R;
import com.xw.interviewapp.bean.HomeRecyclerBean;
import com.xw.interviewapp.http.Https;
import com.xw.interviewapp.presenter.loader.GlideImageLoader;
import com.xw.interviewapp.ui.adapter.HomeRecyclerAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 主页，流式布局效果展示、广告栏展示（自动轮播）
 * <br/>
 * 作者：XW <br/>
 * 邮箱：xw_appdev@163.com <br/>
 * 时间：2017-02-21
 */

public class HomeFragment extends Fragment {
    
    private AppBarLayout app_bar;
    private CollapsingToolbarLayout ctl;
    
    private RecyclerView rv;
    
    private HomeRecyclerAdapter mRecyclerAdapter;
    
    private List<HomeRecyclerBean> mRecyclerDatas;
    
    private FloatingActionButton fab;
    
    private Banner banner;
    private List<String> mTitles;
    private List<String> mUrls;
    
    private CollapsingToolbarLayoutState state;
    
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 200:
                    mRecyclerDatas.clear();
                    mRecyclerDatas.addAll((Collection<? extends HomeRecyclerBean>) msg.obj);
                    mRecyclerAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    
    private enum CollapsingToolbarLayoutState {
        EXPANDED,
        COLLAPSED,
        INTERNEDIATE
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home1, container, false);
        initDatas();
        initViews(view);
        return view;
    }

    private void initDatas() {
        mTitles = new ArrayList<>();
        mUrls = new ArrayList<>();

        mTitles.add("最是那一低头的温柔，像一朵水莲花，不胜凉风的娇羞");
        mTitles.add("蓦然回首，那人却在灯火阑珊处。");
        mTitles.add("春眠不觉晓，总是睡不饱。");
        mTitles.add("挥一挥衣袖，不带走一片云彩。");
        mTitles.add("卑鄙是卑鄙者的通行证，高尚是高尚者的墓志铭。");
        mTitles.add("明月装饰了你的窗子，你装饰了别人的梦。");
        mTitles.add("才下眉头，却上心头。");
//        urls.add("http://www.th7.cn/d/file/p/2015/06/29/1c1645081d3d7675bc5db3a06fd752a1.png");
//        urls.add("http://img6.pplive.cn/2013/05/03/17133890332.jpg");
//        urls.add("http://img003.21cnimg.com/photos/game_0/20140826/c100-0-0-550-264_r0/34F32E78011BA8AEE070DEEEBC4687BE.jpeg");
//        urls.add("http://img6.pplive.cn/2012/05/21/09464789529.jpg");
//        urls.add("http://img01.res.yoho.cn/blogimg/2013/11/12/11/013bf914a55bac79f124b19a5b547f3651.jpg");
//        urls.add("http://img01.res.yoho.cn/blogimg/2013/07/31/10/010ef5e0678d2a9d55fa503b1b60d4ece2.jpg");
//        urls.add("http://developer.baidu.com/static/assets/v3/banner_translate.jpg");

        mUrls.add("http://pic.qiantucdn.com/58pic/18/10/41/19x58PICy6d_1024.jpg!/format/webp");
        mUrls.add("http://pic.qiantucdn.com/58pic/12/39/69/52E58PIC97a.jpg!/format/webp");
        mUrls.add("http://pic.qiantucdn.com/58pic/13/86/92/31d58PICTEz_1024.jpg!/format/webp");
        mUrls.add("hhttp://pic.qiantucdn.com/58pic/11/30/39/91r58PIC9qe.jpg!/format/webp");
        mUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&s" +
                "ec=1488571773023&di=0f8ec1e0520adac414c8109eab37b648&imgtype=jpg&s" +
                "rc=http%3A%2F%2Fb.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Fb2de9c" +
                "82d158ccbff5a8885e11d8bc3eb13541b9.jpg");
        mUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&s" +
                "ec=1488571838743&di=8c736c940b687eb2349e16ae930aa68f&imgtype=jpg&s" +
                "rc=http%3A%2F%2Fa.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F77094b" +
                "36acaf2eddcbb17b8d851001e939019315.jpg");
        mUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&s" +
                "ec=1488571923462&di=1dd2ea8645b65d7bfe7ac56d6825bbd7&imgtype=0&src" +
                "=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fw%253D580%2Fsign%3Dd863af" +
                "b4cb95d143da76e42b43f18296%2F5ec678dde71190efec02d168cd1b9d16fffa6084.jpg");
    
        Https.instance().getBeanExecute("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1",
                null, null, null, mHandler, null);
        mRecyclerDatas = new ArrayList<>();

    }

    private void initViews(View view) {
        ctl = (CollapsingToolbarLayout) view.findViewById(R.id.ctl);
//        ctl.setExpandedTitleColor(Color.parseColor("#00ffffff"));
//        ctl.setCollapsedTitleTextColor(Color.parseColor("#ffffff"));
        app_bar = (AppBarLayout) view.findViewById(R.id.app_bar);
        app_bar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    if (state != CollapsingToolbarLayoutState.EXPANDED) {
                        state = CollapsingToolbarLayoutState.EXPANDED;
                        ctl.setTitle("");
                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                        ctl.setTitle("首页");
                        ctl.setCollapsedTitleTextColor(Color.WHITE);
                        state = CollapsingToolbarLayoutState.COLLAPSED;
                    }
                } else {
                    if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {
                        ctl.setTitle("");
                        state = CollapsingToolbarLayoutState.INTERNEDIATE;
                    }
                }
            }
        });
    
        rv = (RecyclerView) view.findViewById(R.id.rv);
//        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
//        rv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerAdapter = new HomeRecyclerAdapter(getActivity(), mRecyclerDatas);
        rv.setAdapter(mRecyclerAdapter);
    
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
    
        banner = (Banner) view.findViewById(R.id.banner);
        banner.setBannerResources(mUrls)
                .setBannerTitles(mTitles)
                .setBannerScaleType(BannerConfig.SCALE_TYPE)
//                .setIndicatorGravity(BannerConfig.INDICATOR_GRAVITY_RIGHT)
                .setIndicatorWidth(20)
                .setIndicatorHeight(20)
                .setIndicatorSelectedResId(R.drawable.banner_indicator_green_radius)
                .setIndicatorUnselectedResId(R.drawable.banner_indicator_yellow_radius)
                .setImageLoader(new GlideImageLoader())
                .start();
    }
    
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (banner != null) {
            if (isVisibleToUser) {
                banner.startAutoPlay();
            } else {
                banner.stopAutoPlay();
            }
        }
    }
    
    @Override
    public void onStart() {
        super.onStart();
        banner.startAutoPlay();
    }
    
    
    @Override
    public void onPause() {
        banner.stopAutoPlay();
        super.onPause();
    }
    
}




















