package com.parkingwang.android.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parkingwang.android.SwActivity;
import com.parkingwang.android.SwKeyboard;
import com.parkingwang.android.SwUtils;
import com.parkingwang.android.helper.KeyboardHelper;
import com.parkingwang.android.listener.KeyboardListener;
import com.parkingwang.android.listener.OnAppStatusChangedListener;

/**
 * @author DongMS
 * @date 2019/5/24
 */
public class KeyboardActivity extends AppCompatActivity implements KeyboardListener {

    private EditText mEtNoHide;
    private EditText mEtHide;
    private Button mBtnTest;
    private KeyboardHelper mKeyboardHelper;

    private final OnAppStatusChangedListener mAppStatusChangedListener = new OnAppStatusChangedListener() {
        @Override
        public void onForeground() {
            Log.e(KeyboardActivity.class.getSimpleName(), "APP在前台");
        }

        @Override
        public void onBackground() {
            Log.e(KeyboardActivity.class.getSimpleName(), "APP在后台");
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwUtils.registerAppStatusChangedListener(this, mAppStatusChangedListener);
        setContentView(R.layout.activity_keyboard);
        mEtNoHide = findViewById(R.id.et_no_hide);
        mEtHide = findViewById(R.id.et_hide);
        mBtnTest = findViewById(R.id.btn_test);
        mKeyboardHelper = KeyboardHelper.newInstance(this);
    }

    @Nullable
    @Override
    public int[] hideSoftByEditViewIds() {
        return new int[]{R.id.et_hide};
    }

    @Nullable
    @Override
    public View[] filterViewByIds() {
        return new View[]{mBtnTest};
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return mKeyboardHelper.dispatchTouchEvent(this, event) || super.dispatchTouchEvent(event);
    }

    public void showKeyboard(View view) {
        SwKeyboard.showSoftInput(this);
    }

    public void hideKeyboard(View view) {
        SwKeyboard.hideSoftInput(this);
    }

    public void showInputDialog(View view) {
        //todo
    }

    @Override
    protected void onDestroy() {
        mKeyboardHelper.onDestroy(this);
        super.onDestroy();
        SwUtils.unregisterAppStatusChangedListener(this);
    }
}
