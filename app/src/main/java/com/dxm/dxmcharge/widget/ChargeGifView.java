package com.dxm.dxmcharge.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.dxm.dxmcharge.R;


public class ChargeGifView extends View {

    private final int DEFAULT_WIDTH = 550;//默认控件宽度
    private final int DEFAULT_HEIGHT = 550;//默认控件高度
    private final int DEFAULT_STROKE_WIDTH = 20;//默认圆环厚度
    private final int DEFAULT_SCALE_WIDTH = 5;//刻度
    private final int DEFAULT_SCALE_ANGLE = 15;//刻度


    private Paint mPaint;
    private Paint mScaleBgPaint;
    private Paint mScaleFgPaint;

    //内圆半径
    private int radius;
    //控件宽度
    private int width = DEFAULT_WIDTH;
    //控件高度
    private int height = DEFAULT_HEIGHT;
    //圆环厚度
    private int strokeWidth = DEFAULT_STROKE_WIDTH;
    //刻度
    private int scaleWidth = DEFAULT_SCALE_WIDTH;
    //刻度之间的间隔角度
    private int scaleAngle = DEFAULT_SCALE_ANGLE;


    private int current = 0;
    private int scaleNum;
    private int circle_color;
    private int circle_width;

    private int scales_color;
    private int scales_width;



    public ChargeGifView(Context context) {
        this(context, null);
    }

    public ChargeGifView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChargeGifView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
        init();
    }

    private void initAttr(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ChargeGifView);
        scaleAngle = typedArray.getInt(R.styleable.ChargeGifView_charge_angle, DEFAULT_SCALE_ANGLE);
        circle_color=typedArray.getColor(R.styleable.ChargeGifView_charge_circle_color, ContextCompat.getColor(context,R.color.color_2BB6F7));
        circle_width=typedArray.getInt(R.styleable.ChargeGifView_charge_circle_width,DEFAULT_STROKE_WIDTH);
        scales_color=typedArray.getColor(R.styleable.ChargeGifView_charge_scales_color,ContextCompat.getColor(context,R.color.color_2BB6F7));
        scales_width=typedArray.getInt(R.styleable.ChargeGifView_charge_scales_color,DEFAULT_SCALE_WIDTH);
        typedArray.recycle();

    }


    private void init() {

        scaleNum = 360 / scaleAngle;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setColor(circle_color);


        mScaleBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mScaleBgPaint.setStyle(Paint.Style.FILL);
        mScaleBgPaint.setStrokeWidth(scaleWidth);
        mScaleBgPaint.setColor(Color.GRAY);


        mScaleFgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mScaleFgPaint.setStyle(Paint.Style.FILL);
        mScaleFgPaint.setStrokeWidth(scaleWidth);
        mScaleFgPaint.setColor(scales_color);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getViewWidth(widthMeasureSpec);
        height = getViewHeight(heightMeasureSpec);

        radius = width / 2;

        setMeasuredDimension(width, height);
    }


    private int getViewWidth(int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size;
        if (mode == MeasureSpec.EXACTLY) {
            size = MeasureSpec.getSize(widthMeasureSpec);
        } else {
            size = DEFAULT_WIDTH;//默认大小
        }
        return size;

    }


    private int getViewHeight(int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size;
        if (mode == MeasureSpec.EXACTLY) {
            size = MeasureSpec.getSize(heightMeasureSpec);
        } else {
            size = DEFAULT_WIDTH;//默认大小
        }
        return size;

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw background circle
        double v = radius * 0.8;
        canvas.drawCircle(radius, radius, (float) v, mPaint);

        double v1 = radius - v;

        canvas.save();
        //每15度画一个刻度
        for (int i = 0; i < scaleNum; i++) {
            canvas.rotate(scaleAngle, radius, radius); // 注意这里的后两个参数：代表以哪个位置为中心开始旋转，默认是以(0,0)位置为中心开始旋转，这里是以该圆的圆心为中心点开始旋转。
            canvas.drawLine(radius, 0, radius, (float) v1 - 20, mScaleBgPaint);
        }


        canvas.restore();


        canvas.save();
        //每15度画一个刻度
        for (int i = 0; i < current; i++) {
            canvas.rotate(scaleAngle, radius, radius); // 注意这里的后两个参数：代表以哪个位置为中心开始旋转，默认是以(0,0)位置为中心开始旋转，这里是以该圆的圆心为中心点开始旋转。
            canvas.drawLine(radius, 0, radius, (float) v1 - 20, mScaleFgPaint);
        }

        canvas.restore();

//        setValueAndStartAnim();

    }


    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    /**
     * 设置温度值并启动动画（插值器：LinearInterpolator）
     */
    public void setValueAndStartAnim() {
        ObjectAnimator waveShiftAnim = ObjectAnimator.ofInt(this, "current", 0, scaleNum);
        waveShiftAnim.addUpdateListener(animation -> {
            postInvalidate();
        });
        waveShiftAnim.setRepeatCount(-1);
        waveShiftAnim.setDuration(5 * 1000);
        waveShiftAnim.setInterpolator(new LinearInterpolator());
        waveShiftAnim.start();
    }


}
