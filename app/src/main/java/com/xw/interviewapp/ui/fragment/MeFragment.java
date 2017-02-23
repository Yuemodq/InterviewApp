package com.xw.interviewapp.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xw.interviewapp.R;

/**
 * Created by Administrator on 2017-02-21.
 * 个人中心：缓存清理、图片上传（头像）、图片处理（圆形、方形、圆角等）
 */

public class MeFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, null);
        return view;
    }
}
