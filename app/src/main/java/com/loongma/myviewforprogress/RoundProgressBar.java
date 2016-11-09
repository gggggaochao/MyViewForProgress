package com.loongma.myviewforprogress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 高超 on 2016/10/28.
 */

public class RoundProgressBar extends View {

    //画笔对象的引用
    private Paint paint;
    //圆环的颜色
    private int roundColor;
    //圆环进度的颜色
    private int roundProgressColor;
    //中间进度百分比的字符串的颜色
    private int textColor;
    //中间进度百分比的字符串的字体大小
    private float textSize;
    //圆环的宽度
    private float roundWidth;
    //最大进度
    private int max;
    //当前进度
    private int progress;
    //是否显示中间的进度
    private boolean textIsDisplay;
    //进去的风格，实心或者空心
    private int style;

    public static  final int STROKE =0;
    public static  final int FILL = 1;

    public RoundProgressBar(Context context) {
        super(context);
    }

    public RoundProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    TypedArray mTypedArray = context.obtainStyledAttributes(attrs,R.styleable.RoundProgressBar);

        //获取自定义属性和默认值
        roundColor =mTypedArray.getColor(R.styleable.RoundProgressBar_roundColor, Color.WHITE);
        roundProgressColor=mTypedArray.getColor(R.styleable.RoundProgressBar_roundProgressColor,Color.GREEN);
        textColor=mTypedArray.getColor(R.styleable.RoundProgressBar_textColor,Color.WHITE);
        textSize=mTypedArray.getDimension(R.styleable.RoundProgressBar_textSize,15);
        roundWidth=mTypedArray.getDimension(R.styleable.RoundProgressBar_roundWidth,5);
        max=mTypedArray.getInteger(R.styleable.RoundProgressBar_max,100);
        textIsDisplay =mTypedArray.getBoolean(R.styleable.RoundProgressBar_textIsDisplayable,true);
        style=mTypedArray.getInt(R.styleable.RoundProgressBar_style,0);

        mTypedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * 画最外层的大圆环
         */
        int centre = getWidth() / 2;  //获取圆心的X坐标
        int radius = (int) (centre - roundWidth / 2);//圆环的半径
        paint.setColor(roundColor);  //设置圆环的颜色
        paint.setStyle(Paint.Style.STROKE);//设置空心
        paint.setStrokeWidth(roundWidth); //设置圆环的宽度
        paint.setAntiAlias(true);  //消除锯齿
        canvas.drawCircle(centre, centre, radius, paint);  //画出圆环

        /**
         *  画进度百分比
         */
        paint.setStrokeWidth(0);
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        int percent = (int) (((float) progress / (float) max) * 100);//中间的进度百分比，先转换成float在进行除法运算，不然都为0
        float textWidth = paint.measureText(percent + "%");  //测量字体宽度，我们需要根据字体的宽度这只在圆环中

        if (textIsDisplay && percent != 0 && style == STROKE) {
            canvas.drawText(percent + "%", centre - textWidth / 2, centre + textSize / 2, paint);
        }


        //画圆弧
        paint.setStrokeWidth(roundWidth);
        paint.setColor(roundProgressColor);
        RectF oval = new RectF(centre - radius, centre - radius, centre + radius, centre + radius);

        switch (style) {
            case STROKE:
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawArc(oval, 0, 360 * progress / max, false, paint);
                break;
            case FILL:
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                if (progress != 0)
                    canvas.drawArc(oval, 0, 360 * progress / max, true, paint);
                break;
        }
    }
        public synchronized  int getMax(){
            return  max;
        }

    public synchronized void setMax(int max){
        if (max <0){
            throw  new IllegalArgumentException("max not less than 0");
        }
        this.max=max;
    }
    public synchronized int getProgress() {
        return progress;
    }
    public synchronized void setProgress(int progress) {
        if(progress < 0){
            throw new IllegalArgumentException("progress not less than 0");
        }
        if(progress > max){
            progress = max;
        }
        if(progress <= max){
            this.progress = progress;
            postInvalidate();
        }

    }


    public int getCricleColor() {
        return roundColor;
    }

    public void setCricleColor(int cricleColor) {
        this.roundColor = cricleColor;
    }

    public int getCricleProgressColor() {
        return roundProgressColor;
    }

    public void setCricleProgressColor(int cricleProgressColor) {
        this.roundProgressColor = cricleProgressColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public float getRoundWidth() {
        return roundWidth;
    }

    public void setRoundWidth(float roundWidth) {
        this.roundWidth = roundWidth;
    }


}
