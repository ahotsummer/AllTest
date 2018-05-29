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
        SharedPreferences sp = getSharedPreferences("SHG",MODE_PRIVATE);

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

}