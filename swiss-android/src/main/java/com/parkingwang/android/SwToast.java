package com.parkingwang.android;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

/**
 * @author DongMS
 * @date 2019/7/4
 */
public class SwToast {

    private static final int COLOR_DEFAULT = 0xFEFFFFFF;
    private static final String NULL = "SwToastNull";
    private static final String TAG = SwToast.class.getSimpleName();

    private static IToast iToast;
    private static int sGravity = -1;
    private static int sXOffset = -1;
    private static int sYOffset = -1;
    private static int sBgColor = COLOR_DEFAULT;
    private static int sBgResource = -1;
    private static int sMsgColor = COLOR_DEFAULT;
    private static int sMsgTextSize = -1;

    private SwToast() {
    }


    public static void setGravity(final int gravity, final int xOffset, final int yOffset) {
        sGravity = gravity;
        sXOffset = xOffset;
        sYOffset = yOffset;
    }

    public static void setGravity(final int gravity) {
        setGravity(gravity, 0, 0);
    }

    public static void setBgColor(@ColorInt final int backgroundColor) {
        sBgColor = backgroundColor;
    }

    public static void setBgResource(@DrawableRes final int bgResource) {
        sBgResource = bgResource;
    }

    public static void setMsgColor(@ColorInt final int msgColor) {
        sMsgColor = msgColor;
    }

    public static void setMsgTextSize(final int textSize) {
        sMsgTextSize = textSize;
    }

    public static void showShort(final CharSequence text) {
        show(text == null ? NULL : text, Toast.LENGTH_SHORT);
    }

    public static void showShort(@StringRes final int resId) {
        show(resId, Toast.LENGTH_SHORT);
    }

    public static void showShort(@StringRes final int resId, final Object... args) {
        show(resId, Toast.LENGTH_SHORT, args);
    }

    public static void showShort(final String format, final Object... args) {
        show(format, Toast.LENGTH_SHORT, args);
    }

    public static void showLong(final CharSequence text) {
        show(text == null ? NULL : text, Toast.LENGTH_LONG);
    }

    public static void showLong(@StringRes final int resId) {
        show(resId, Toast.LENGTH_LONG);
    }

    public static void showLong(@StringRes final int resId, final Object... args) {
        show(resId, Toast.LENGTH_LONG, args);
    }

    public static void showLong(final String format, final Object... args) {
        show(format, Toast.LENGTH_LONG, args);
    }

    public static View showCustomShort(@LayoutRes final int layoutId) {
        return showCustomShort(SwView.inflate(layoutId));
    }

    public static View showCustomShort(final View view) {
        show(view, Toast.LENGTH_SHORT);
        return view;
    }

    public static View showCustomLong(@LayoutRes final int layoutId) {
        return showCustomLong(SwView.inflate(layoutId));
    }

    public static View showCustomLong(final View view) {
        show(view, Toast.LENGTH_LONG);
        return view;
    }

    public static void cancel() {
        if (iToast != null) {
            iToast.cancel();
        }
    }

    private static void show(final int resId, final int duration) {
        try {
            CharSequence text = SwResource.getString(resId);
            show(text, duration);
        } catch (Exception ignore) {
            show(String.valueOf(resId), duration);
        }
    }

    private static void show(final int resId, final int duration, final Object... args) {
        try {
            CharSequence text = SwResource.getString(resId);
            String format = String.format(text.toString(), args);
            show(format, duration);
        } catch (Exception ignore) {
            show(String.valueOf(resId), duration);
        }
    }

    private static void show(final String format, final int duration, final Object... args) {
        String text;
        if (format == null) {
            text = NULL;
        } else {
            text = String.format(format, args);
            if (text == null) {
                text = NULL;
            }
        }
        show(text, duration);
    }

    private static void show(final CharSequence text, final int duration) {
        SwUtils.runOnUiThread(() -> {
            cancel();
            iToast = ToastFactory.makeToast(SwUtils.getContext(), text, duration);
            final View toastView = iToast.getView();
            if (toastView == null) return;
            final TextView tvMessage = toastView.findViewById(android.R.id.message);
            if (sMsgColor != COLOR_DEFAULT) {
                tvMessage.setTextColor(sMsgColor);
            }
            if (sMsgTextSize != -1) {
                tvMessage.setTextSize(sMsgTextSize);
            }
            if (sGravity != -1 || sXOffset != -1 || sYOffset != -1) {
                iToast.setGravity(sGravity, sXOffset, sYOffset);
            }
            setBg(tvMessage);
            iToast.show();
        });
    }

    private static void show(final View view, final int duration) {
        SwUtils.runOnUiThread(() -> {
            cancel();
            iToast = ToastFactory.newToast(SwUtils.getContext());
            iToast.setView(view);
            iToast.setDuration(duration);
            if (sGravity != -1 || sXOffset != -1 || sYOffset != -1) {
                iToast.setGravity(sGravity, sXOffset, sYOffset);
            }
            setBg();
            iToast.show();
        });
    }

    private static void setBg() {
        if (sBgResource != -1) {
            final View toastView = iToast.getView();
            toastView.setBackgroundResource(sBgResource);
        } else if (sBgColor != COLOR_DEFAULT) {
            final View toastView = iToast.getView();
            Drawable background = toastView.getBackground();
            if (background != null) {
                background.setColorFilter(
                        new PorterDuffColorFilter(sBgColor, PorterDuff.Mode.SRC_IN)
                );
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    toastView.setBackground(new ColorDrawable(sBgColor));
                } else {
                    toastView.setBackgroundDrawable(new ColorDrawable(sBgColor));
                }
            }
        }
    }

    private static void setBg(final TextView tvMsg) {
        if (sBgResource != -1) {
            final View toastView = iToast.getView();
            toastView.setBackgroundResource(sBgResource);
            tvMsg.setBackgroundColor(Color.TRANSPARENT);
        } else if (sBgColor != COLOR_DEFAULT) {
            final View toastView = iToast.getView();
            Drawable tvBg = toastView.getBackground();
            Drawable msgBg = tvMsg.getBackground();
            if (tvBg != null && msgBg != null) {
                tvBg.setColorFilter(new PorterDuffColorFilter(sBgColor, PorterDuff.Mode.SRC_IN));
                tvMsg.setBackgroundColor(Color.TRANSPARENT);
            } else if (tvBg != null) {
                tvBg.setColorFilter(new PorterDuffColorFilter(sBgColor, PorterDuff.Mode.SRC_IN));
            } else if (msgBg != null) {
                msgBg.setColorFilter(new PorterDuffColorFilter(sBgColor, PorterDuff.Mode.SRC_IN));
            } else {
                toastView.setBackgroundColor(sBgColor);
            }
        }
    }

    static class ToastFactory {

        static IToast makeToast(Context context, CharSequence text, int duration) {
            if (NotificationManagerCompat.from(context).areNotificationsEnabled()) {
                return new SystemToast(makeNormalToast(context, text, duration));
            }
            return new ToastWithoutNotification(makeNormalToast(context, text, duration));
        }

        static IToast newToast(Context context) {
            if (NotificationManagerCompat.from(context).areNotificationsEnabled()) {
                return new SystemToast(new Toast(context));
            }
            return new ToastWithoutNotification(new Toast(context));
        }

        private static Toast makeNormalToast(Context context, CharSequence text, int duration) {
            @SuppressLint("ShowToast")
            Toast toast = Toast.makeText(context, "", duration);
            toast.setText(text);
            return toast;
        }
    }

    static class SystemToast extends AbsToast {

        SystemToast(Toast toast) {
            super(toast);
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {
                try {
                    //noinspection JavaReflectionMemberAccess
                    Field mTNField = Toast.class.getDeclaredField("mTN");
                    mTNField.setAccessible(true);
                    Object mTN = mTNField.get(toast);
                    Field mTNmHandlerField = mTNField.getType().getDeclaredField("mHandler");
                    mTNmHandlerField.setAccessible(true);
                    Handler tnHandler = (Handler) mTNmHandlerField.get(mTN);
                    mTNmHandlerField.set(mTN, new SafeHandler(tnHandler));
                } catch (Exception ignored) {/**/}
            }
        }

        @Override
        public void show() {
            mToast.show();
        }

        @Override
        public void cancel() {
            mToast.cancel();
        }

        static class SafeHandler extends Handler {
            private Handler impl;

            SafeHandler(Handler impl) {
                this.impl = impl;
            }

            @Override
            public void handleMessage(Message msg) {
                impl.handleMessage(msg);
            }

            @Override
            public void dispatchMessage(Message msg) {
                try {
                    impl.dispatchMessage(msg);
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            }
        }
    }

    static class ToastWithoutNotification extends AbsToast {

        private View mView;
        private WindowManager mWM;

        private WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();

        private final SwAppLifecycleCallbackImpl.OnActivityDestroyedListener mDestroyedListener = new SwAppLifecycleCallbackImpl.OnActivityDestroyedListener() {
            @Override
            public void onActivityDestroyed(Activity activity) {
                if (iToast == null) return;
                activity.getWindow().getDecorView().setVisibility(View.GONE);
                iToast.cancel();
            }
        };

        ToastWithoutNotification(Toast toast) {
            super(toast);
        }

        @Override
        public void show() {
            mView = mToast.getView();
            if (mView == null) return;
            final Context context = mToast.getView().getContext();
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
                mWM = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                mParams.type = WindowManager.LayoutParams.TYPE_TOAST;
            } else {
                Context topActivityOrApp = SwActivity.getTopActivityOrApp();
                if (!(topActivityOrApp instanceof Activity)) {
                    Log.e(TAG, "Couldn't get top Activity.");
                    return;
                }
                Activity topActivity = (Activity) topActivityOrApp;
                if (topActivity.isFinishing() || topActivity.isDestroyed()) {
                    Log.e(TAG, topActivity + " is useless");
                    return;
                }
                mWM = topActivity.getWindowManager();
                mParams.type = WindowManager.LayoutParams.LAST_APPLICATION_WINDOW;
                SwUtils.ACTIVITY_LIFECYCLE.addOnActivityDestroyedListener(topActivity, mDestroyedListener);
            }

            mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
            mParams.format = PixelFormat.TRANSLUCENT;
            mParams.windowAnimations = android.R.style.Animation_Toast;
            mParams.setTitle("ToastWithoutNotification");
            mParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
            mParams.packageName = SwUtils.getContext().getPackageName();

            mParams.gravity = mToast.getGravity();
            if ((mParams.gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.FILL_HORIZONTAL) {
                mParams.horizontalWeight = 1.0f;
            }
            if ((mParams.gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.FILL_VERTICAL) {
                mParams.verticalWeight = 1.0f;
            }

            mParams.x = mToast.getXOffset();
            mParams.y = mToast.getYOffset();
            mParams.horizontalMargin = mToast.getHorizontalMargin();
            mParams.verticalMargin = mToast.getVerticalMargin();

            try {
                if (mWM != null) {
                    mWM.addView(mView, mParams);
                }
            } catch (Exception ignored) {/**/}

            SwUtils.runOnUiThreadDelayed(this::cancel, mToast.getDuration() == Toast.LENGTH_SHORT ? 2000 : 3500);
        }

        @Override
        public void cancel() {
            try {
                if (mWM != null) {
                    mWM.removeViewImmediate(mView);
                }
            } catch (Exception ignored) {
            }
            mView = null;
            mWM = null;
            mToast = null;
        }
    }

    static abstract class AbsToast implements IToast {

        Toast mToast;

        AbsToast(Toast toast) {
            mToast = toast;
        }

        @Override
        public void setView(View view) {
            mToast.setView(view);
        }

        @Override
        public View getView() {
            return mToast.getView();
        }

        @Override
        public void setDuration(int duration) {
            mToast.setDuration(duration);
        }

        @Override
        public void setGravity(int gravity, int xOffset, int yOffset) {
            mToast.setGravity(gravity, xOffset, yOffset);
        }

        @Override
        public void setText(int resId) {
            mToast.setText(resId);
        }

        @Override
        public void setText(CharSequence s) {
            mToast.setText(s);
        }
    }

    interface IToast {

        void show();

        void cancel();

        void setView(View view);

        View getView();

        void setDuration(int duration);

        void setGravity(int gravity, int xOffset, int yOffset);

        void setText(@StringRes int resId);

        void setText(CharSequence s);
    }
}
