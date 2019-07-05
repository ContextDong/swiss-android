package com.parkingwang.android;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author DongMS
 * @date 2019/7/4
 */
public class SwView {

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

}
