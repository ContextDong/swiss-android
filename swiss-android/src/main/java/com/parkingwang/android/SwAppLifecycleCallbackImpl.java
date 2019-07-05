package com.parkingwang.android;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.parkingwang.android.listener.OnAppStatusChangedListener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author DongMS
 * @date 2019/7/4
 */
class SwAppLifecycleCallbackImpl implements Application.ActivityLifecycleCallbacks {

    final Map<Object, OnAppStatusChangedListener> mStatusListenerMap = new HashMap<>();
    final Map<Activity, Set<OnActivityDestroyedListener>> mDestroyedListenerMap = new HashMap<>();

    private int mForegroundCount = 0;
    private int mConfigCount = 0;
    private boolean mIsBackground = false;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        SwActivity.setTopActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (!mIsBackground) {
            SwActivity.setTopActivity(activity);
        }
        if (mConfigCount < 0) {
            ++mConfigCount;
        } else {
            ++mForegroundCount;
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        SwActivity.setTopActivity(activity);
        if (mIsBackground) {
            mIsBackground = false;
            postStatus(true);
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (activity.isChangingConfigurations()) {
            --mConfigCount;
        } else {
            --mForegroundCount;
            if (mForegroundCount <= 0) {
                mIsBackground = true;
                postStatus(false);
            }
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        SwActivity.ACTIVITY_LIST.remove(activity);
        consumeOnActivityDestroyedListener(activity);
    }


    private void postStatus(final boolean isForeground) {
        if (mStatusListenerMap.isEmpty()) return;
        for (OnAppStatusChangedListener onAppStatusChangedListener : mStatusListenerMap.values()) {
            if (onAppStatusChangedListener == null) return;
            if (isForeground) {
                onAppStatusChangedListener.onForeground();
            } else {
                onAppStatusChangedListener.onBackground();
            }
        }
    }

    private void consumeOnActivityDestroyedListener(Activity activity) {
        Iterator<Map.Entry<Activity, Set<OnActivityDestroyedListener>>> iterator
                = mDestroyedListenerMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Activity, Set<OnActivityDestroyedListener>> entry = iterator.next();
            if (entry.getKey() == activity) {
                Set<OnActivityDestroyedListener> value = entry.getValue();
                for (OnActivityDestroyedListener listener : value) {
                    listener.onActivityDestroyed(activity);
                }
                iterator.remove();
            }
        }
    }

    void addOnAppStatusChangedListener(final Object object,
                                       final OnAppStatusChangedListener listener) {
        mStatusListenerMap.put(object, listener);
    }

    void removeOnAppStatusChangedListener(final Object object) {
        mStatusListenerMap.remove(object);
    }

    void removeOnActivityDestroyedListener(final Activity activity) {
        if (activity == null) return;
        mDestroyedListenerMap.remove(activity);
    }

    void addOnActivityDestroyedListener(final Activity activity,
                                        final OnActivityDestroyedListener listener) {
        if (activity == null || listener == null) return;
        Set<OnActivityDestroyedListener> listeners;
        if (!mDestroyedListenerMap.containsKey(activity)) {
            listeners = new HashSet<>();
            mDestroyedListenerMap.put(activity, listeners);
        } else {
            listeners = mDestroyedListenerMap.get(activity);
            if (listeners.contains(listener)) return;
        }
        listeners.add(listener);
    }

    interface OnActivityDestroyedListener {
        void onActivityDestroyed(Activity activity);
    }

}
