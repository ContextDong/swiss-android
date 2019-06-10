package com.parkingwang.android;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.res.ResourcesCompat;

/**
 * @author DongMS
 * @date 2019/5/24
 */
public final class SwResource {

    private SwResource() {
    }

    /**
     * Resources.getSystem() 可以在任何地方进行使用，但是有一个局限，只能获取系统本身的资源
     * @return
     */
    public static Resources getResource() {
        return SwUtils.getContext().getResources();
    }

    @ColorInt
    public static int getColor(@ColorRes int colorResId) {
        return getColor(colorResId, null);
    }

    @ColorInt
    public static int getColor(@ColorRes int colorResId, @Nullable Resources.Theme theme) {
        return ResourcesCompat.getColor(getResource(), colorResId, theme);
    }

    public static String getString(@StringRes int strResId) {
        return SwUtils.getContext().getString(strResId);
    }

    public static String getString(@StringRes int strResId, Object... format) {
        return SwUtils.getContext().getString(strResId, format);
    }

    public static String[] getStringArray(@ArrayRes int strArrResId) {
        return SwUtils.getContext().getResources().getStringArray(strArrResId);
    }

    public static Drawable getDrawable(@DrawableRes int drawableResId) {
        return getDrawable(drawableResId, null);
    }

    public static Drawable getDrawable(@DrawableRes int drawableResId, Resources.Theme theme) {
        return ResourcesCompat.getDrawable(getResource(), drawableResId, theme);
    }

}
