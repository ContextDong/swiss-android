package com.parkingwang.android.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.parkingwang.android.SwDoubleClick;
import com.parkingwang.android.view.SwLoading;
import com.parkingwang.android.view.SwToast;

public class MainActivity extends AppCompatActivity {

    private final SwDoubleClick mDoubleClick = new SwDoubleClick(this, "再按一次原地爆炸")
            .setOnDoubleClickHandler(new SwDoubleClick.OnDoubleClickHandler() {
                @Override
                public void onDoubleClick() {
                    // 默认实现为关闭Activity
                    // MainActivity.this.finish();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void success(final View view) {
        loadingAfter(view, new Callback() {
            @Override
            public void function() {
                SwToast.success(getApplicationContext()).show(((Button) view).getText().toString());
            }
        });
    }

    public void warn(final View view) {
        loadingAfter(view, new Callback() {
            @Override
            public void function() {
                SwToast.warning(getApplicationContext()).show(((Button) view).getText().toString());
            }
        });
    }


    public void error(final View view) {
        loadingAfter(view, new Callback() {
            @Override
            public void function() {
                SwToast.failed(getApplicationContext()).show(((Button) view).getText().toString());
            }
        });
    }

    private void loadingAfter(final View view, final Callback callback) {
        final SwLoading loading = loading();
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                loading.hide();
                callback.function();
            }
        }, 1000);
    }

    private SwLoading loading() {
        SwLoading loading = SwLoading.create(this);
        loading.show();
        return loading;
    }

    public void keyboardUtil(final View view) {
        startActivity(new Intent(this, KeyboardActivity.class));
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mDoubleClick.onKeyDown(keyCode, event) ||
                super.onKeyDown(keyCode, event);
    }


    private interface Callback {
        void function();
    }

}
