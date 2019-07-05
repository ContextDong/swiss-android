package com.parkingwang.android.onresult;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * @author DongMS
 * @since 2019/6/10
 */
public class SwOnResult {

    private static final String TAG = "SwOnResult";

    private final OnResultFragment mAvoidOnResultFragment;

    public SwOnResult(AppCompatActivity activity) {
        mAvoidOnResultFragment = getAvoidOnResultFragment(activity);
    }

    public SwOnResult(Fragment fragment) {
        mAvoidOnResultFragment = getAvoidOnResultFragment((AppCompatActivity) fragment.getActivity());
    }

    private OnResultFragment getAvoidOnResultFragment(AppCompatActivity activity) {
        OnResultFragment fragment = findAvoidOnResultFragment(activity);
        if (fragment == null) {
            fragment = new OnResultFragment();
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            fragmentManager.beginTransaction().add(fragment, TAG).commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return fragment;
    }

    private OnResultFragment findAvoidOnResultFragment(AppCompatActivity activity) {
        return (OnResultFragment) activity.getSupportFragmentManager().findFragmentByTag(TAG);
    }

    public void startForResult(Intent intent, Callback callback) {
        mAvoidOnResultFragment.startForResult(intent, callback);
    }

    public void startForResult(Class<? extends AppCompatActivity> clz, Callback callback) {
        mAvoidOnResultFragment.startForResult(new Intent(mAvoidOnResultFragment.getActivity(), clz), callback);
    }

    public interface Callback {
        void onActivityResult(int resultCode, Intent data);
    }
}
