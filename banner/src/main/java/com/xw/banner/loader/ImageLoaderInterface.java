package com.xw.banner.loader;

import android.content.Context;
import android.view.View;

/**
 * <br/>
 * 作者：XW <br/>
 * 邮箱：xw_appdev@163.com <br/>
 * 时间：2017-03-03 22:49
 */

public interface ImageLoaderInterface<T extends View> {
    void displayImage(Context context, Object path, T imageView);

    T createImageView(Context context);
}
