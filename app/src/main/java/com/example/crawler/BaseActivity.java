package com.example.crawler;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by jingxiongdi on 2018/4/16.
 */

public abstract class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    public abstract void initData();
}
