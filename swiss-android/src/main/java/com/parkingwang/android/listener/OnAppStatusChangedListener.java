package com.parkingwang.android.listener;

/**
 * @author DongMS
 * @since 2019/7/5
 */
public interface OnAppStatusChangedListener {
    void onForeground();

    void onBackground();
}
