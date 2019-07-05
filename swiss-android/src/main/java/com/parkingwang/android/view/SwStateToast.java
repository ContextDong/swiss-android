package com.parkingwang.android.view;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parkingwang.android.R;
import com.parkingwang.android.SwResource;
import com.parkingwang.android.SwToast;
import com.parkingwang.android.SwUtils;
import com.parkingwang.android.SwView;

/**
 * @author YOOJIA.CHEN (yoojia.chen@gmail.com)
 * @since 1.0
 */
public class SwStateToast {

    private final View mRootView;
    private final ImageView mIcon;
    private final TextView mMessage;

    private Style mStyle;

    public SwStateToast(Style style) {
        mStyle = style;
        mRootView = SwView.inflate(R.layout.sw_toast);
        mIcon = mRootView.findViewById(R.id.icon);
        mMessage = mRootView.findViewById(R.id.message);
    }

    /**
     * 设置提示样式
     *
     * @param style 样式
     */
    public void setStyle(Style style) {
        mStyle = style;
    }

    /**
     * 指定图标资源ID，显示长时间的提示信息
     *
     * @param iconResId 图标资源ID
     * @param message   提示信息内容
     */
    public void showLong(int iconResId, String message) {
        show(iconResId, message, Toast.LENGTH_LONG);
    }

    /**
     * 显示长时间的提示信息
     *
     * @param message 提示信息内容
     */
    public void showLong(String message) {
        showLong(mStyle.resId, message);
    }

    /**
     * 显示长时间的提示信息
     *
     * @param message 提示信息内容
     */
    public void showLong(int message) {
        showLong(SwResource.getString(message));
    }

    /**
     * 指定图标资源ID，显示短时间的提示信息
     *
     * @param iconResId 图标资源ID
     * @param message   提示信息内容
     */
    public void show(int iconResId, String message) {
        show(iconResId, message, Toast.LENGTH_SHORT);
    }

    /**
     * 使用创建NextToast指定类型的图标资源ID，显示短时间的提示信息
     *
     * @param message 提示信息内容
     */
    public void show(String message) {
        show(mStyle.resId, message);
    }

    /**
     * 使用创建NextToast指定类型的图标资源ID，显示短时间的提示信息
     *
     * @param message 提示信息内容
     */
    public void show(int message) {
        show(SwResource.getString(message));
    }

    /**
     * 创建一个无图标的NextToast
     *
     * @return NextToast
     */
    public static SwStateToast tip() {
        return new SwStateToast(Style.TIP);
    }

    /**
     * 创建一个对号图标的NextToast
     *
     * @return NextToast
     */
    public static SwStateToast success() {
        return new SwStateToast(Style.SUCCESS);
    }

    /**
     * 创建一个叉号图标的NextToast
     *
     * @return NextToast
     */
    public static SwStateToast failed() {
        return new SwStateToast(Style.FAILED);
    }

    /**
     * 创建一个叹号图标的NextToast
     *
     * @return NextToast
     */
    public static SwStateToast warning() {
        return new SwStateToast(Style.WARNING);
    }

    private void show(final int iconResId, final String message, final int duration) {
        SwToast.setGravity(Gravity.CENTER);
        if (duration == Toast.LENGTH_SHORT) {
            SwToast.showCustomShort(mRootView);
        } else {
            SwToast.showCustomLong(mRootView);
        }
        SwUtils.runOnUiThread(() -> {
            if (iconResId != 0) {
                mIcon.setVisibility(View.VISIBLE);
                mIcon.setImageResource(iconResId);
            } else {
                mIcon.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(message)) {
                mMessage.setText(message);
            }
            SwToast.resetToast();
        });
    }

    public enum Style {
        TIP(0),

        SUCCESS(R.drawable.sw_icon_success),

        FAILED(R.drawable.sw_icon_failed),

        WARNING(R.drawable.sw_icon_warning);
        private final int resId;

        Style(int resId) {
            this.resId = resId;
        }
    }

}