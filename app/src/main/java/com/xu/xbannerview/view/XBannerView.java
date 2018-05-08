package com.xu.xbannerview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.xu.xbannerview.R;


public class XBannerView extends View {

    private int mPosition = 0;
    private int mCount = 0;
    private int mColor;
    private int mHeight, mWidth, mMargin;
    private Paint mPaint;

    private float xExtend, rX, rY;

    private int mViewPagerIndex;
    private ViewPager mViewPager;

    public XBannerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        //初始化宽高，间距，圆弧的xy半径，填充颜色，x延伸距离默认等于宽度
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.XBannerView);
        mHeight = typedArray.getInt(R.styleable.XBannerView_app_banner_height,15);
        mWidth = typedArray.getInt(R.styleable.XBannerView_app_banner_width,30);
        mMargin = typedArray.getInt(R.styleable.XBannerView_app_banner_margin,20);
        rX = typedArray.getFloat(R.styleable.XBannerView_app_x_radius,6);
        rY = typedArray.getFloat(R.styleable.XBannerView_app_y_radius,4);
        mColor = typedArray.getColor(R.styleable.XBannerView_app_banner_color, Color.LTGRAY);
        xExtend = mWidth;

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);//充满
        mPaint.setAntiAlias(true);// 设置画笔的锯齿效果
        mPaint.setColor(mColor);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBanner(canvas);
    }

    private void drawBanner(Canvas canvas) {
        int xMargin = mMargin + mWidth;

        for (int i = 0; i < mCount; i++) {
            RectF rectF = new RectF();
            rectF.left = xMargin * i;
            rectF.top = 0;
            rectF.bottom = mHeight;
            if (i == 0) {
                rectF.right = xMargin * (mPosition) + xExtend;
            } else {
                rectF.right = mWidth + xMargin * (i);
            }
            canvas.drawRoundRect(rectF, rX, rY, mPaint);//第二个参数是x半径，第三个参数是y半径
        }
    }

    public void bindWithViewPager(ViewPager viewPager) {
        if (viewPager != null) {
            mViewPager = viewPager;
            mPosition = viewPager.getCurrentItem();
            mCount = viewPager.getAdapter().getCount();
            viewPager.addOnPageChangeListener(onPageChangeListener);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 重新测量当前界面的宽度
        int width = getPaddingLeft() + getPaddingRight() + mWidth * mCount + mMargin * (mCount - 1);
        int height = getPaddingTop() + getPaddingBottom() + mHeight;
        width = resolveSize(width, widthMeasureSpec);
        height = resolveSize(height, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            Log.d("onPage_Scroll", position + "|" + positionOffset + "|" + positionOffsetPixels);
            mPosition = position;
            xExtend = mWidth + positionOffset * mMargin;
            invalidate();//刷新绘制
            if (mViewPagerIndex == position) {
                Log.d("onPage444", "正在向左滑动");
            } else {
                Log.d("onPage444", "正在向右滑动");
            }
//            if (mViewPagerIndex == position && position == (list.size()-1)) {
//                //判断最后一页向左滑后续操作
//            }
        }

        @Override
        public void onPageSelected(int position) {
            Log.d("onPage_Select", position + "");
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            Log.d("onPage_State", state + "");
            if (state == 1) {//state有三种状态下文会将，当手指刚触碰屏幕时state的值为1，我们就在这个时候给mViewPagerIndex 赋值。
                mViewPagerIndex = mViewPager.getCurrentItem();
            }
        }
    };
}
