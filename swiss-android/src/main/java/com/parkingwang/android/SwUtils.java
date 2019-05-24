package com.parkingwang.android;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import java.lang.reflect.InvocationTargetException;

/**
 * @author DongMS
 * @date 2019/5/24
 */
public final class SwUtils {

    @SuppressLint("StaticFieldLeak")
    private static Context sContext;

    private SwUtils() {
    }

    /**
     * 需要进行初始化
     *
     * @param context
     */
    public static void init(@NonNull Context context) {
        sContext = context;
    }

    public static Context getContext() {
        if (sContext == null) {
            sContext = getApplicationByReflect();
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

}
