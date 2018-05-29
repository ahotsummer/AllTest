package com.example.cyl.alltest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class ScreenLockView extends View {
    //别人动力文件
    //gaigia
    public static final String TAG = "ScreenLockView";
    //gaigai
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static int SMALL_RADIUS = 30;
    private static int BIGGER_RADIUS = 60;
    private static final String GRAYCOLOR = "#ffd5dbe8";
    private static final String BLUECOLOR = "#ff508cee";
    private static final float LINE_WIDTH = 10.0f;
    private static final float LINE_NORMAL = 1.0f;
    private int width;
    private int height;
    public Circle[] circles = new Circle[9];
    private Paint mPaint;
    private float mNextX, mNextY;
    private Path mPath = new Path();
    private String mRightPsw;
    private StringBuilder mInputPsw;
    private Context mContext;
    private ResponseInput object;
    private int normalColor;
    private int clickedColor;

    public ScreenLockView(Context context) {
        this(context, null);
    }

    public ScreenLockView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ScreenLockView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }



    private void init(Context context,AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ScreenLockView, defStyle, 0);

        normalColor = a.getColor(
                R.styleable.ScreenLockView_normalColor,
                Color.parseColor(GRAYCOLOR));
        clickedColor = a.getColor(
                R.styleable.ScreenLockView_clickedColor,
                Color.parseColor(BLUECOLOR));
        a.recycle();

        mContext = context;
        object = (ResponseInput) mContext;
        mInputPsw = new StringBuilder();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(normalColor);
    }
    public String getmRightPsw() {
        return mRightPsw;
    }

    public void setmRightPsw(String mRightPsw) {
        this.mRightPsw = mRightPsw;
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
       /* int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int width = WIDTH;
        int height = HEIGHT;
        if (widthSpecMode == MeasureSpec.AT_MOST && heightpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(width, height);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(width, heightSpecSize);
            height = heightSpecSize;
        } else if (heightMeasureSpec == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, height);
            width = widthSpecSize;
        }
        initCircleLocation();*/
        width = ViewUitl.getSize(widthMeasureSpec,WIDTH);
        height = ViewUitl.getSize(widthMeasureSpec,HEIGHT);
        initCircleLocation();
        setMeasuredDimension(width,height);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < circles.length; i++) {
            Circle circle = circles[i];
            if (circle.isClicked) {
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setColor(clickedColor);
                mPaint.setStrokeWidth(LINE_NORMAL);
                //画大圆的轮廓
                canvas.drawCircle(circle.x, circle.y, circle.outterRadius, mPaint);
            } else {
                mPaint.setColor(normalColor);
            }
            //画小圆
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setStrokeWidth(LINE_NORMAL);
            canvas.drawCircle(circle.x, circle.y, circle.innderRadius, mPaint);

        }
        //画连线
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(LINE_WIDTH);
        mPaint.setColor(clickedColor);
        canvas.drawPath(mPath, mPaint);
        //画连线
        canvas.save();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {

            case MotionEvent.ACTION_DOWN: {
                Log.d(TAG, "ACTION_DOWN");
                /*Log.d(TAG + "onTouchEvent", "getX:" + event.getX() + ",getRawX="
                        + event.getRawX() + ",getY=" + event.getY()
                        + ",getRawY=" + event.getRawY());*/
                int index = getClickedCircleIndex(event.getX(), event.getY());
                if (index >= 0 && index <= circles.length) {
                    gatherInput(index);
                    mPath.moveTo(circles[index].x, circles[index].y);
                    return true;
                } else {
                    //TODO 第一次没触到任何块则提示

                    return false;
                }

            }
            case MotionEvent.ACTION_MOVE: {
                Log.d(TAG, "ACTION_MOVE");
                float x = event.getX();
                float y = event.getY();
                int index = getClickedCircleIndex(x, y);
                if (index >= 0 && index < circles.length) {
                    circles[index].isClicked = true;
                    gatherInput(index);
                    if (getClickedCircleIndex(mNextX, mNextY) >= 0) {
                        mPath.lineTo(circles[index].x, circles[index].y);
                    } else {
                        mPath.setLastPoint(circles[index].x, circles[index].y);
                    }
                    mNextX = circles[index].x;
                    mNextY = circles[index].y;
                } else {
                    mNextX = x;
                    mNextY = y;
                    mPath.setLastPoint(mNextX, mNextY);

                }

                invalidate();
                Log.d(TAG + "index", "index:" + index);
            }
            break;
            case MotionEvent.ACTION_UP: {
                Log.d(TAG, "ACTION_UP");
                //TODO 判断密码是否正确
                if (isInputOK()) {

                    object.inputOK();
                    //Toast.makeText(mContext, "密码正确: "+mInputPsw, Toast.LENGTH_SHORT).show();
                } else {
                    object.inputErr();
                    // Toast.makeText(mContext, "密码错误: "+mInputPsw, Toast.LENGTH_SHORT).show();
                }
                uninit();

            }
            break;
        }
        return super.onTouchEvent(event);
    }

    public void initCircleLocation() {
        int contentWidth = width - 2 * BIGGER_RADIUS;
        int contentHeight = height - 2 * BIGGER_RADIUS;
        for (int i = 0; i < circles.length; i++) {
            circles[i] = new Circle();
            circles[i].x = BIGGER_RADIUS + (i % 3) * contentWidth / 2;
            circles[i].y = BIGGER_RADIUS + (i / 3) * contentHeight / 2;
            circles[i].isClicked = false;
            circles[i].innderRadius = SMALL_RADIUS;
            circles[i].outterRadius = BIGGER_RADIUS;
        }
    }


    public int getClickedCircleIndex(float x, float y) {

        for (int i = 0; i < circles.length; i++) {
            Circle cirlce = circles[i];
            if (x >= cirlce.x - cirlce.outterRadius
                    && x <= cirlce.x + cirlce.outterRadius
                    && y <= cirlce.y + cirlce.outterRadius
                    && y >= cirlce.y - cirlce.outterRadius) {
                return i;
            }
        }
        return -1;
    }

    public void uninit() {

        if (null != circles) {
            for (int i = 0; i < circles.length; i++) {
                circles[i].isClicked = false;
            }
            invalidate();
        }
        if (null != mPath) {
            mPath.reset();
        }
        mInputPsw.delete(0, mInputPsw.length());

    }


    /**
     * //得到用户输入的内容
     *
     * @param index 划过点的下标
     */
    public void gatherInput(int index) {

        int content = index + 1;
        if (mInputPsw.length() == 0) {
            mInputPsw.append(content);
        } else {
            char lastCharacter = mInputPsw.charAt(mInputPsw.length() - 1);
            int iLastValue = Character.getNumericValue(lastCharacter);
            if (iLastValue != content) {
                mInputPsw.append(content);
            }
        }
    }

    /**
     * 验证输入的密码是否正确
     *
     * @return 正确返回true
     */
    public boolean isInputOK() {

        return mInputPsw == null || mRightPsw == null
                ? false : mRightPsw.equals(mInputPsw.toString());
    }

    static class Circle {
        //bug cherry-pick
        private int x;
        private int y;
        private int innderRadius;
        private int outterRadius;
        private boolean isClicked;
    }

    public interface ResponseInput {

        public void inputOK();

        public void inputErr();
        //bugbug
    }
}
