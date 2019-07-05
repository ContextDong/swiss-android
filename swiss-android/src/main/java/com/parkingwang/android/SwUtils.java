package com.parkingwang.android;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.parkingwang.android.listener.OnAppStatusChangedListener;

import java.lang.reflect.InvocationTargetException;

/**
 * @author DongMS
 * @date 2019/5/24
 */
public final class SwUtils {

    @SuppressLint("StaticFieldLeak")
    private static Context sContext;

    public static final Handler MAIN_HANDLER = new Handler(Looper.getMainLooper());
    static final SwAppLifecycleCallbackImpl ACTIVITY_LIFECYCLE = new SwAppLifecycleCallbackImpl();

    private SwUtils() {
    }

    /**
     * 必须进行初始化
     *
     * @param context
     */
    public static void init(@NonNull Context context) {
        sContext = context;
        ((Application) sContext).unregisterActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE);
        SwActivity.ACTIVITY_LIST.clear();
        ((Application) sContext).registerActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE);
    }

    public static Context getContext() {
        if (sContext == null) {
            sContext = getApplicationByReflect();
            ((Application) sContext).registerActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE);
        }
        return sContext;
    }


    private static Application getApplicationByReflect() {
        try {
            @SuppressLint("PrivateApi")
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Object thread = activityThread.getMethod("currentActivityThread").invoke(null);
            Object app = activityThread.getMethod("getApplication").invoke(thread);
            if (app == null) {
                throw new NullPointerException("u should init first");
            }
            return (Application) app;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("u should init first");
    }

    public static void runOnUiThread(final Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        } else {
            MAIN_HANDLER.post(runnable);
        }
    }

    public static void runOnUiThreadDelayed(final Runnable runnable, long delayMillis) {
        MAIN_HANDLER.postDelayed(runnable, delayMillis);
    }

    public static void registerAppStatusChangedListener(@NonNull final Object obj,
                                                        @NonNull final OnAppStatusChangedListener listener) {
        ACTIVITY_LIFECYCLE.addOnAppStatusChangedListener(obj, listener);
    }

    public static void unregisterAppStatusChangedListener(@NonNull final Object obj) {
        ACTIVITY_LIFECYCLE.removeOnAppStatusChangedListener(obj);
    }

}
