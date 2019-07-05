package com.parkingwang.android.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.TextView;

import com.parkingwang.android.R;
import com.parkingwang.android.SwUtils;

/**
 * @author YOOJIA.CHEN (yoojia.chen@gmail.com)
 * @since 1.0
 */
public class SwLoading extends Dialog {

    private final TextView mMessage;

    private CharSequence mMessageText;

    private int mMessageId;

    private final Runnable mShowDelayTask = this::showOnMainThread;

    private final Runnable mHideDelayTask = SwLoading.this::hide;

    private final Runnable mDismissDelayTask = SwLoading.this::dismiss;

    public SwLoading(Context context) {
        super(context, R.style.SwissProgress);
        setContentView(R.layout.sw_progress);
        setCancelable(false);
        mMessage = findViewById(R.id.message);
    }

    public SwLoading setMessage(@StringRes final int msg) {
        mMessageId = msg;
        return this;
    }

    public SwLoading setMessage(final CharSequence msg) {
        mMessageText = msg;
        return this;
    }

    @Override
    public void setTitle(final CharSequence title) {
        setMessage(title);
    }

    @Override
    public void setTitle(@StringRes final int titleId) {
        setMessage(titleId);
    }

    public void showDelay(final long delayMillis) {
        SwUtils.runOnUiThreadDelayed(mShowDelayTask, delayMillis);
    }

    public void hideDelay(final long delayMillis) {
        SwUtils.runOnUiThreadDelayed(mHideDelayTask, delayMillis);
    }

    public void dismissDelay(final long delayMillis) {
        SwUtils.runOnUiThreadDelayed(mDismissDelayTask, delayMillis);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SwUtils.MAIN_HANDLER.removeCallbacks(mShowDelayTask);
        SwUtils.MAIN_HANDLER.removeCallbacks(mHideDelayTask);
        SwUtils.MAIN_HANDLER.removeCallbacks(mDismissDelayTask);
    }

    /**
     * Show方法可以在任意线程中调用
     */
    @Override
    public void show() {
        SwUtils.runOnUiThread(this::showOnMainThread);
    }

    private void showOnMainThread() {
        if (mMessageId != 0) {
            mMessage.setText(mMessageId);
        } else if (mMessageText != null) {
            mMessage.setText(mMessageText);
        } else {
            mMessage.setVisibility(View.GONE);
        }
        super.show();
    }

    public static SwLoading create(final Activity context) {
        return new SwLoading(context);
    }

}