package com.parkingwang.android.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.parkingwang.android.SwDoubleClick;
import com.parkingwang.android.SwToast;
import com.parkingwang.android.SwUtils;
import com.parkingwang.android.app.bar.BarActivity;
import com.parkingwang.android.listener.OnAppStatusChangedListener;
import com.parkingwang.android.onresult.SwOnResult;

public class MainActivity extends AppCompatActivity {

    private final SwDoubleClick mDoubleClick = new SwDoubleClick(this, "再按一次原地爆炸")
            .setOnDoubleClickHandler(() -> {
                // 默认实现为关闭Activity
                // MainActivity.this.finish();
            });

    private final OnAppStatusChangedListener mAppStatusChangedListener = new OnAppStatusChangedListener() {
        @Override
        public void onForeground() {
            Log.e(MainActivity.class.getSimpleName(), "APP在前台");
        }

        @Override
        public void onBackground() {
            Log.e(MainActivity.class.getSimpleName(), "APP在后台");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwUtils.registerAppStatusChangedListener(this, mAppStatusChangedListener);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SwUtils.unregisterAppStatusChangedListener(this);
    }


    public void keyboardUtil(final View view) {
        startActivity(new Intent(this, KeyboardActivity.class));
    }

    public void barUtil(final View view) {
        startActivity(new Intent(this, BarActivity.class));
    }

    public void toastUtil(View view) {
        new SwOnResult(this).startForResult(ToastActivity.class, (resultCode, data) -> {
            if (resultCode == Activity.RESULT_OK) {
                SwToast.showShort(data.getStringExtra("msg"));
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mDoubleClick.onKeyDown(keyCode, event) ||
                super.onKeyDown(keyCode, event);
    }
}
