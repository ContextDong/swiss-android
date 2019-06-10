package com.parkingwang.android;

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
    public static int getScreenWidths() {
        return SwResource.getResource().getDisplayMetrics().widthPixels;
    }

    /**
     * 得到设备屏幕的高度
     */
    public static int getScreenHeights() {
        return SwResource.getResource().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取DisplayMetrics对象
     *
     * @return
     */
    public static DisplayMetrics getDisplayMetrics() {
        return SwResource.getResource().getDisplayMetrics();
    }

    /**
     * 得到设备屏幕的密度
     */
    public static float getScreenDensity() {
        return getDisplayMetrics().density;
    }

    public static float dp2pxF(float dpValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getDisplayMetrics());
    }

    public static float sp2pxF(float spValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, getDisplayMetrics());
    }

    /**
     * Value of dp to value of px.
     *
     * @param dpValue The value of dp.
     * @return value of px
     */
    public static int dp2px(final float dpValue) {
        final float scale = SwResource.getResource().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * Value of px to value of dp.
     *
     * @param pxValue The value of px.
     * @return value of dp
     */
    public static int px2dp(final float pxValue) {
        final float scale = SwResource.getResource().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * Value of sp to value of px.
     *
     * @param spValue The value of sp.
     * @return value of px
     */
    public static int sp2px(final float spValue) {
        final float fontScale = SwResource.getResource().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * Value of px to value of sp.
     *
     * @param pxValue The value of px.
     * @return value of sp
     */
    public static int px2sp(final float pxValue) {
        final float fontScale = SwResource.getResource().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

}
