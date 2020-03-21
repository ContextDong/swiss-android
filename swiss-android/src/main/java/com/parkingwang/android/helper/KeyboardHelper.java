package com.parkingwang.android.helper;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.parkingwang.android.SwKeyboard;
import com.parkingwang.android.listener.KeyboardListener;

import java.lang.ref.WeakReference;

/**
 * @author DongMS
 * @since 2019/5/24
 */
public class KeyboardHelper {

    private WeakReference<KeyboardListener> mListener;

    private KeyboardHelper(KeyboardListener listener) {
        mListener = new WeakReference<>(listener);
    }

    public static KeyboardHelper newInstance(@NonNull KeyboardListener listener) {
        return new KeyboardHelper(listener);
    }

    /**
     * 点击editText外部区域隐藏软键盘
     *
     * @param activity 宿主
     * @param ev       触摸事件
     * @return false, 交给activity处理[super.dispatchTouchEvent(ev)]
     */
    public boolean dispatchTouchEvent(Activity activity, MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            KeyboardListener listener = mListener.get();
            if (listener == null) {
                return false;
            }
            if (isTouchView(listener.filterViewByIds(), ev)) {
                return false;
            }
            if (listener.hideSoftByEditViewIds() == null) {
                return false;
            }
            if (listener.hideSoftByEditViewIds().length == 0) {
                return false;
            }
            if (isTouchView(listener.allEditTextView(), ev)) {
                return false;
            }
            View view = activity.getCurrentFocus();
            if (isFocusEditText(view, listener.hideSoftByEditViewIds())) {
                //隐藏键盘
                SwKeyboard.hideSoftInput(activity);
                clearViewFocus(view, listener.hideSoftByEditViewIds());
                return true;
            }
        }
        return false;
    }

    /**
     * 是否触摸在指定view上面,对某个控件过滤
     *
     * @param views
     * @param ev
     * @return
     */
    private boolean isTouchView(View[] views, MotionEvent ev) {
        if (views == null || views.length == 0) {
            return false;
        }
        int[] location = new int[2];
        for (View view : views) {
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            if (ev.getX() > x && ev.getX() < (x + view.getWidth())
                    && ev.getY() > y && ev.getY() < (y + view.getHeight())) {
                return true;
            }
        }
        return false;
    }

    /**
     * edittext是否有焦点
     *
     * @param v   焦点所在View
     * @param ids 输入框
     * @return true代表焦点在edit上
     */
    private boolean isFocusEditText(View v, @IdRes int... ids) {
        if (v instanceof EditText) {
            EditText tmpEt = (EditText) v;
            for (int id : ids) {
                if (tmpEt.getId() == id) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 清除editText的焦点
     *
     * @param v   焦点所在View
     * @param ids 输入框
     */
    private void clearViewFocus(View v, int... ids) {
        if (null != v && null != ids && ids.length > 0) {
            for (int id : ids) {
                if (v.getId() == id) {
                    v.clearFocus();
                    break;
                }
            }
        }
    }

    public void onDestroy(Activity context) {
        SwKeyboard.fixSoftInputLeaks(context);
    }

}
