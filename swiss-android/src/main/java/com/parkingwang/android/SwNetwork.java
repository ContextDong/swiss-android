package com.parkingwang.android;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * @author 董棉生(dongmiansheng @ parkingwang.com)
 * @since 18-10-25
 */

public class SwNetwork {

    /**
     * @param context 上下文
     * @return 判断网络连接是否可用
     * 需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            //如果仅仅是用来判断网络连接
            //则可以使用 cm.getActiveNetworkInfo().isAvailable();
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo anInfo : info) {
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断wifi是否连接状态
     * 需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}
     *
     * @param context 上下文
     * @return {@code true}: 连接<br>{@code false}: 未连接
     */
    public static boolean isWifiConnected(Context context) {
        NetworkInfo networkInfo = getActiveNetworkInfo(context);
        return networkInfo != null && networkInfo.isAvailable()
                && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * @param context 上下文
     * @return 判断是否为3G网络
     * 需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}
     */
    public static boolean is3G(Context context) {
        NetworkInfo networkInfo = getActiveNetworkInfo(context);
        return networkInfo != null && networkInfo.isAvailable()
                && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    /**
     * @param context 上下文
     * @return 判断网络是否是4G
     * 需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}
     */
    public static boolean is4G(Context context) {
        NetworkInfo networkInfo = getActiveNetworkInfo(context);
        return networkInfo != null && networkInfo.isAvailable()
                && networkInfo.getSubtype() == TelephonyManager.NETWORK_TYPE_LTE;
    }


    private static NetworkInfo getActiveNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }


    /**
     * 获取移动网络运营商名称
     * <p>如中国联通、中国移动、中国电信</p>
     *
     * @param context 上下文
     * @return 移动网络运营商名称
     */
    public static String getNetworkOperatorName(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getNetworkOperatorName() : null;
    }

}
