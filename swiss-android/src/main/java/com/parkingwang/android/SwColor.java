package com.parkingwang.android;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

/**
 * @author DongMS
 * @since 2019/5/27
 */
public final class SwColor {

    private SwColor() {
    }

    public static int string2Int(@NonNull String colorString) {
        return Color.parseColor(colorString);
    }

    public static String int2RgbString(@ColorInt int colorInt) {
        colorInt = colorInt & 0x00ffffff;
        String color = Integer.toHexString(colorInt);
        while (color.length() < 6) {
            color = "0" + color;
        }
        return "#" + color;
    }

    public static String int2ArgbString(@ColorInt final int colorInt) {
        String color = Integer.toHexString(colorInt);
        while (color.length() < 6) {
            color = "0" + color;
        }
        while (color.length() < 8) {
            color = "f" + color;
        }
        return "#" + color;
    }
}
