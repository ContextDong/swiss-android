package com.parkingwang.android;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * @author 董棉生(dongmiansheng @ parkingwang.com)
 * @since 18-10-25
 */

public class SwDim {

    private SwDim() {
    }

    /**
     * 得到设备屏幕的宽度
     */
    public static int getScreenWidths(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 得到设备屏幕的高度
     */
    public static int getScreenHeights(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取DisplayMetrics对象
     *
     * @param context
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    /**
     * 得到设备屏幕的密度
     */
    public static float getScreenDensity(Context context) {
        return getDisplayMetrics(context).density;
    }

    public static float dp2px(Context context, float dpValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getDisplayMetrics(context));
    }

    public static float sp2px(Context context, float spValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, getDisplayMetrics(context));
    }

}
