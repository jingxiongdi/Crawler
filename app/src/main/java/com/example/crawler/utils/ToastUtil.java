package com.example.crawler.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by jingxiongdi on 2018/4/16.
 */


public class ToastUtil {
    private static  ToastUtil mToastUtils;
    private static Toast mToast;

    private ToastUtil(Context context){
        if (null == mToast){
            mToast = Toast.makeText(context.getApplicationContext(),"",Toast.LENGTH_LONG);
        }
    }

    public static ToastUtil getInstance(Context context) {
        if (mToastUtils == null){
            mToastUtils = new ToastUtil(context.getApplicationContext());
        }
        return mToastUtils;
    }

    public void showShortToast(String mString){
        if (mToast == null){
            return;
        }
        mToast.setText(mString);
        mToast.setDuration(Toast.LENGTH_SHORT);
        // mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }

    public void showLongToast(String mString){
        if (mToast == null){
            return;
        }
        mToast.setText(mString);
        mToast.setDuration(Toast.LENGTH_LONG);
        // mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }

}
