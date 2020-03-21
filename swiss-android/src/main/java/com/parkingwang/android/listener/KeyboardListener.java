package com.parkingwang.android.listener;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * @author DongMS
 * @since 2019/5/24
 */
public interface KeyboardListener {

    /**
     *
     * @return 界面所有的EdiTextView
     */
    @NonNull
    View[] allEditTextView();

    /**
     * 传入EditText的Id
     * 没有传入的EditText不做处理
     *
     * @return id 数组
     */
    @Nullable
    int[] hideSoftByEditViewIds();

    /**
     * 传入要过滤的View
     * 过滤之后点击将不会有隐藏软键盘的操作
     *
     * @return View 数组
     */
    @Nullable
    View[] filterViewByIds();

}
