package com.parkingwang.android.onresult;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;

/**
 * @author DongMS
 * @since 2019/6/10
 */
public class OnResultFragment extends Fragment {

    private final SparseArray<SwOnResult.Callback> mCallbacks = new SparseArray<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    public void startForResult(Intent intent, SwOnResult.Callback callback) {
        int requestCode = shortHashCode(callback.hashCode());
        mCallbacks.put(requestCode, callback);
        startActivityForResult(intent, requestCode);
    }

    private int shortHashCode(Object callback) {
        return callback.hashCode() & 0x0000ffff;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SwOnResult.Callback callback = mCallbacks.get(requestCode);
        if (callback != null) {
            callback.onActivityResult(resultCode, data);
        }
        mCallbacks.remove(requestCode);
    }
}
