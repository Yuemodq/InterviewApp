package com.xw.interviewapp.ui.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.View;

import com.xw.interviewapp.R;

/**
 * <br/>
 * 作者：XW <br/>
 * 邮箱：xw_appdev@163.com <br/>
 * 时间：2017-02-23 20:52
 */

public class FooterSlideGradualnessView extends View {

    private Context mContext;

    /**
     * 控件图片
     */
    private Bitmap mIconBitmap;

    /**
     * 控件文字
     */
    private String mText;

    /**
     * 控件文字大小
     */
    private float mTextSize = 10;

    /**
     * 控件主题色
     */
    private int mColor = 0xff45c01a;

    /**
     * 文字默认颜色
     */
    private int mTextColor = 0xff555555;

    /**
     * 文字画笔
     */
    private Paint mTextPaint;

    /**
     * 文字矩形区域
     */
    private Rect mTextBounds = new Rect();

    /**
     * 图片所在矩形区域
     */
    private Rect mIconRect;

    /**
     * 图片
     */
    private Bitmap mBitmap;

    /**
     * 缓存画布
     */
//    private Canvas mCacheCanvas;

    /**
     * 图片画笔
     */
//    private Paint mIconPaint;

    /**
     * 渐变的透明度
     */
    private float mGradualAlpha/* = 1.0f*/;

    public FooterSlideGradualnessView(Context context) {
        super(context);
        init(context, null);
    }

    public FooterSlideGradualnessView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

//    private void init()

    /**
     * 初始化
     * @param context 上下文
     * @param attrs 属性集
     */
    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        if (attrs != null) {
            //加载自定义属性
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FooterSlideGradualnessView);
            for (int i=0;i<a.getIndexCount();i++) {
                int attr = a.getIndex(i);
                switch (attr) {
                    case R.styleable.FooterSlideGradualnessView_icon:
                        BitmapDrawable bd = (BitmapDrawable) a.getDrawable(attr);
                        if (bd != null) {
                            mIconBitmap = bd.getBitmap();
                        }
                        break;
                    case R.styleable.FooterSlideGradualnessView_text:
                        mText = a.getString(attr);
                        break;
                    case R.styleable.FooterSlideGradualnessView_text_size:
                        mTextSize = a.getDimension(attr, 10);
                        break;
                    case R.styleable.FooterSlideGradualnessView_color:
                        mColor = a.getColor(attr, 0xff45c01a);
                        break;
                    case R.styleable.FooterSlideGradualnessView_text_default_color:
                        mTextColor = a.getColor(attr, 0xff555555);
                        break;
                }
            }
            a.recycle();
        }

        //初始化画笔
        mTextPaint = new Paint();
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //获取文字矩形区域
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBounds);//文字矩形赋予mTextBounds

        //获取图片宽高:取宽高中最小值
        int iconWidth = Math.min(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
                getMeasuredHeight() - getPaddingTop() - getPaddingBottom() - mTextBounds.height());
        //设置图片所在矩形区域
        int left = getMeasuredWidth() / 2 - mIconBitmap.getWidth() / 2;
        int top = (getMeasuredHeight() - mTextBounds.height()) / 2 - iconWidth / 2;
        mIconRect = new Rect(left, top, left + iconWidth, top + iconWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //清空画布
        canvas.drawBitmap(mIconBitmap, null, mIconRect, null);
        int alpha = (int) Math.ceil(255 * mGradualAlpha);
        //缓存画布上绘制图片
        drawIconBitmap(alpha);
        drawSelectedText(canvas, alpha);
        drawNormalText(canvas, alpha);
        //缓存画布内容添加至显示画布
        canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    /**
     * 绘制选中时的文字颜色
     * @param canvas 画布
     * @param alpha 透明度
     */
    private void drawSelectedText(Canvas canvas, int alpha) {
        mTextPaint.setColor(mColor);
        mTextPaint.setAlpha(alpha);
        canvas.drawText(mText, mIconRect.left + mIconRect.width() / 2 - mTextBounds.width() / 2,
                mIconRect.bottom + mTextBounds.height(),
                mTextPaint);
    }

    /**
     * 绘制未选中时的文字颜色
     * @param canvas 画布
     * @param alpha 透明度
     */
    private void drawNormalText(Canvas canvas, int alpha) {
        mTextPaint.setColor(mTextColor);
//        mTextPaint.setColor(0xff333333);
        mTextPaint.setAlpha(255 - alpha);
        canvas.drawText(mText, mIconRect.left + mIconRect.width() / 2 - mTextBounds.width() / 2,
                mIconRect.bottom + mTextBounds.height(),
                mTextPaint);
    }

    /**
     * 缓存画布绘制图片
     * @param alpha 透明度
     */
    private void drawIconBitmap(int alpha) {
        //缓存
        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        //缓存画布
        Canvas cacheCanvas = new Canvas(mBitmap);
        //画笔
        Paint iconPaint = new Paint();
        iconPaint.setColor(mColor);
        //抗锯齿
        iconPaint.setAntiAlias(true);
        //防抖动
        iconPaint.setDither(true);
        iconPaint.setAlpha(alpha);
        //先绘制颜色块
        cacheCanvas.drawRect(mIconRect, iconPaint);
        //绘制图片
        iconPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        iconPaint.setAlpha(255);
        cacheCanvas.drawBitmap(mIconBitmap, null, mIconRect, iconPaint);
    }

    /**
     * 设置透明度
     * @param offset 透明度
     */
    public void setGradualAlpha(float offset) {
        mGradualAlpha = offset;
        invalidate();
    }

    /**
     * 设置控件图片
     * @param drawableRes 控件图片资源
     */
    public void setViewDrawable(int drawableRes) {
        Resources r = mContext.getResources();
        BitmapDrawable d = (BitmapDrawable) ResourcesCompat.getDrawable(r, drawableRes, null);
        if (d != null) {
            mIconBitmap = d.getBitmap();
        }
    }

    /**
     * 设置控件文字
     * @param text 控件文字
     */
    public void setViewText(String text) {
        mText = text;
    }
}



























