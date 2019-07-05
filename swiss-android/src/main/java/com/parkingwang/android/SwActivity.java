package com.parkingwang.android;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author DongMS
 * @date 2019/7/5
 */
public final class SwActivity {

    static final LinkedList<Activity> ACTIVITY_LIST = new LinkedList<>();

    private SwActivity() {
    }

    public static Context getTopActivityOrApp() {
        if (isAppForeground()) {
            Activity topActivity = getTopActivity();
            return topActivity == null ? SwUtils.getContext() : topActivity;
        } else {
            return SwUtils.getContext();
        }
    }

    public static boolean isAppForeground() {
        ActivityManager am = (ActivityManager) SwUtils.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) return false;
        List<ActivityManager.RunningAppProcessInfo> info = am.getRunningAppProcesses();
        if (info == null || info.size() == 0) return false;
        for (ActivityManager.RunningAppProcessInfo aInfo : info) {
            if (aInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                if (aInfo.processName.equals(SwUtils.getContext().getPackageName())) {
                    return true;
                }
            }
        }
        return false;
    }

    static Activity getTopActivity() {
        if (!ACTIVITY_LIST.isEmpty()) {
            for (int i = ACTIVITY_LIST.size() - 1; i >= 0; i--) {
                Activity activity = ACTIVITY_LIST.get(i);
                if (activity == null
                        || activity.isFinishing()
                        || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed())) {
                    continue;
                }
                return activity;
            }
        }
        Activity topActivityByReflect = getTopActivityByReflect();
        if (topActivityByReflect != null) {
            setTopActivity(topActivityByReflect);
        }
        return topActivityByReflect;
    }

    private static Activity getTopActivityByReflect() {
        try {
            @SuppressLint("PrivateApi")
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Object currentActivityThreadMethod = activityThreadClass.getMethod("currentActivityThread").invoke(null);
            Field mActivityListField = activityThreadClass.getDeclaredField("ACTIVITY_LIST");
            mActivityListField.setAccessible(true);
            Map activities = (Map) mActivityListField.get(currentActivityThreadMethod);
            if (activities == null) return null;
            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    return (Activity) activityField.get(activityRecord);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    static void setTopActivity(final Activity activity) {
        if (ACTIVITY_LIST.contains(activity)) {
            if (!ACTIVITY_LIST.getLast().equals(activity)) {
                ACTIVITY_LIST.remove(activity);
                ACTIVITY_LIST.addLast(activity);
            }
        } else {
            ACTIVITY_LIST.addLast(activity);
        }
    }

}
