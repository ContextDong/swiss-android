package com.parkingwang.android.onresult;

import android.content.Intent;

/**
 * @author DongMS
 * @date 2019/6/10
 */
public class ActivityResultInfo {
    public  final  int result;
    public final Intent intent;

    public ActivityResultInfo(int result, Intent intent) {
        this.result = result;
        this.intent = intent;
    }
}
