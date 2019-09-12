package com.parkingwang.android;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * @author DongMS
 * @since 2019/7/4
 */
public final class SwView {

    private SwView() {
    }

    public static LayoutInflater getLayoutInflater() {
        return getLayoutInflater(null);
    }

    public static LayoutInflater getLayoutInflater(final Context context) {
        if (context == null) {
            return LayoutInflater.from(SwUtils.getContext());
        } else {
            return LayoutInflater.from(context);
        }
    }

    public static View inflate(@LayoutRes final int layoutRes) {
        return getLayoutInflater().inflate(layoutRes, null);
    }

    public static View inflate(final Context context, @LayoutRes final int layoutRes) {
        return getLayoutInflater(context).inflate(layoutRes, null);
    }

    public static View inflate(@LayoutRes final int layoutRes, final ViewGroup parentView) {
        return getLayoutInflater().inflate(layoutRes, parentView, false);
    }

    public static View inflate(final Context context, @LayoutRes final int layoutRes, final ViewGroup parentView) {
        return getLayoutInflater(context).inflate(layoutRes, parentView, false);
    }

    public static View inflate(@LayoutRes final int layoutRes, final ViewGroup parentView, final boolean isAttachParentView) {
        return getLayoutInflater().inflate(layoutRes, parentView, isAttachParentView);
    }

    public static View inflate(final Context context, @LayoutRes final int layoutRes, final ViewGroup parentView, final boolean isAttachParentView) {
        return getLayoutInflater(context).inflate(layoutRes, parentView, isAttachParentView);
    }

    /**
     * 扩大触摸面积
     *
     * @param additional 要增加的每个方向的面积
     * @param view       要扩大触摸面积的View
     */
    public static void extendTouchRange(final int additional, final View view) {
        view.post(() -> {
            final Rect touchRect = new Rect();
            view.getHitRect(touchRect);
            touchRect.left -= additional;
            touchRect.right += additional;
            touchRect.top -= additional;
            touchRect.bottom += additional;
            TouchDelegate touchDelegate = new TouchDelegate(touchRect, view);
            ViewParent parent = view.getParent();
            if (parent instanceof View) {
                ((View) parent).setTouchDelegate(touchDelegate);
            }
        });
    }

}
