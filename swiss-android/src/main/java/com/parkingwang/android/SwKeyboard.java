package com.parkingwang.android;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;

/**
 * @author DongMS
 * @since 2019/5/24
 */
public final class SwKeyboard {

    private SwKeyboard() {
    }

    public static void showSoftInput(final Activity activity) {
        showSoftInput(activity, InputMethodManager.SHOW_FORCED);
    }

    public static void showSoftInput(final Activity activity, final int flags) {
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        showSoftInput(view, flags);
    }

    public static void showSoftInput(final View view) {
        showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    public static void showSoftInput(final View view, final int flags) {
        final InputMethodManager imm = (InputMethodManager) SwUtils.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        imm.showSoftInput(view, flags, new ResultReceiver(new Handler()) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                if (resultCode == InputMethodManager.RESULT_UNCHANGED_HIDDEN
                        || resultCode == InputMethodManager.RESULT_HIDDEN) {
                    toggleSoftInput();
                }
            }
        });
    }

    public static void hideSoftInput(final Activity activity) {
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        hideSoftInput(view);
    }

    public static void hideSoftInput(final View view) {
        final InputMethodManager imm = (InputMethodManager) SwUtils.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0, new ResultReceiver(new Handler()) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                if (resultCode == InputMethodManager.RESULT_UNCHANGED_SHOWN
                        || resultCode == InputMethodManager.RESULT_SHOWN) {
                    toggleSoftInput();
                }
            }
        });
    }

    public static void toggleSoftInput() {
        InputMethodManager imm = (InputMethodManager) SwUtils.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /**
     * @param activity 修复软键盘的内存泄漏，activity的onDestroy()回调
     */
    public static void fixSoftInputLeaks(final Activity activity) {
        if (activity == null) return;
        InputMethodManager imm = (InputMethodManager) SwUtils.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        String[] leakViews = new String[]{"mLastSrvView", "mCurRootView", "mServedView", "mNextServedView"};
        for (String leakView : leakViews) {
            try {
                Field leakViewField = InputMethodManager.class.getDeclaredField(leakView);
                if (leakViewField == null) continue;
                if (!leakViewField.isAccessible()) {
                    leakViewField.setAccessible(true);
                }
                Object obj = leakViewField.get(imm);
                if (!(obj instanceof View)) continue;
                View view = (View) obj;
                if (view.getRootView() == activity.getWindow().getDecorView().getRootView()) {
                    leakViewField.set(imm, null);
                }
            } catch (Throwable ignore) { /**/ }
        }
    }

}
