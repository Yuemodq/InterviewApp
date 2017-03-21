package com.xw.interviewapp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xw.interviewapp.R;

/**
 * <br/>
 * 作者：XW <br/>
 * 邮箱：xw_appdev@163.com <br/>
 * 时间：2017-03-18 23:18
 */

public class ShowImageActivity extends AppCompatActivity {
    
    private ImageView iv_show;
    private int left, top, right, bottom;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
//            );
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN /*|
//                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE*/);
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_show_image);
        initViews();
    }
    
    private void initViews() {
        iv_show = (ImageView) findViewById(R.id.iv_show);
        Intent intent = getIntent();
        String imageResId = intent.getStringExtra("image_uri");
        Glide.with(this).load(imageResId).into(iv_show);
        iv_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeShowImage();
            }
        });
    }
    
    private Drawable getDrawable() {
        Intent intent = getIntent();
        int imageResId = intent.getIntExtra("image_uri", R.mipmap.ic_launcher);
        return ActivityCompat.getDrawable(this, imageResId);
    }
    
    private void closeShowImage() {
        ActivityCompat.finishAfterTransition(this);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
//                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        overridePendingTransition(0, R.anim.slide_bottom_out);
//        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(this,
//                v, "hero");
//        startActivity(intent, activityOptions.toBundle());
    }
}

























