package com.example.cyl.alltest;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.StringTokenizer;

public class Main2Activity extends Activity implements ScreenLockView.ResponseInput,ViewPager.OnPageChangeListener{

    private ScreenLockView mUnLockView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mUnLockView = (ScreenLockView) findViewById(R.id.unlockview);
        mUnLockView.setmRightPsw("14789");
        SharedPreferences sp = getSharedPreferences("SHG",MODE_PRIVATE)


    }

    private void setPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(mProgressColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mProgressWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    public void inputOK() {
        Toast.makeText(this, "密码正确", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void inputErr() {
        Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private static class TimelineHandler extends Handler {
        private WeakReference<Activity> ref;

        public TimelineHandler(Activity context) {
            ref = new WeakReference<Activity>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            final Activity context = ref.get();
         //   if (context == null || context.isDetached()) return;//fragment
            if (context == null || context.isFinishing()) {
                return;
            }
            switch (msg.what) {
                    case :

                break;
                    case :

                break;

            }
            super.handleMessage(msg);
        }
    }

    private TimelineHandler handler = new TimelineHandler(this);

    private static class TimelineHandler extends Handler {
        private WeakReference<Activity> ref;

        public TimelineHandler(Activity context) {
            ref = new WeakReference<Activity>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            final Activity context = ref.get();
            //   if (context == null || context.isDetached()) return;//fragment
            if (context == null || context.isFinishing()) {
                return;
            }
            switch (msg.what) {
                    case :

                break;
                    case :

                break;

            }
            super.handleMessage(msg);
        }
    }

    private TimelineHandler handler = new TimelineHandler(this);

}