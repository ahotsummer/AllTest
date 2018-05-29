package com.example.cyl.alltest;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by cyl on 2018/5/28.
 */

public class CommonDialog extends Dialog {
    private LayoutInflater mInflater;
    private Context mContext;
    private TextView mTvContent;
    private View mRootView;
    private TextView mLeftButton;
    private TextView mRightButton;
    private View mDivider;
    private View mButtonDivider;
    private DialogBtnClickListener mListener;
    interface DialogBtnClickListener{
        void onLeftClicked();
        void onRightClicked();
        //hihi
    }

    public CommonDialog(@NonNull Context context) {
        this(context, R.style.Dialog);
    }

    public CommonDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        mContext = context;
        initUI();
        initEvent();
    }
    public void initUI(){
        LayoutInflater mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = mInflater.inflate(R.layout.activity_main2,null);

    }
    public void initEvent(){

    }

}
