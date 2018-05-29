package com.example.cyl.alltest;

/**
 * Created by cyl on 2018/5/22.
 */
import android.content.Context;
import android.view.View.MeasureSpec;

public class ViewUitl {

    //hhhj
    //b
    //c
    public static int getSize(int SizeInfoMeasureSpec,int limitSize){

        int mode = MeasureSpec.getMode(SizeInfoMeasureSpec);
        int size = limitSize;
        switch(mode){
            case MeasureSpec.EXACTLY:{
                size = MeasureSpec.getSize(SizeInfoMeasureSpec);
            }break;
            case MeasureSpec.AT_MOST:{
                size = Math.min(size,MeasureSpec.getSize(SizeInfoMeasureSpec));
            }break;
            default:{
                size = limitSize;
            }break;
        }
        return size;
    }

    public static int dip2px(Context context, float dpValue) {

        float scale = context.getResources().getDisplayMetrics().density;

        return (int)(dpValue*scale+0.5f);

    }
    public static int px2dip(Context context,float pxValue) {

        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue/scale+0.5f);

    }


}