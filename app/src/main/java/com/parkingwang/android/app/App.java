package com.parkingwang.android.app;

import android.app.Application;

import com.parkingwang.android.SwUtils;

/**
 * @author DongMS
 * @since 2019/7/5
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //使用前必须初始化
        SwUtils.init(this);
    }
}
