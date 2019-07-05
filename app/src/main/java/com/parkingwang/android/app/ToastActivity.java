package com.parkingwang.android.app;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parkingwang.android.SwToast;
import com.parkingwang.android.view.SwLoading;
import com.parkingwang.android.view.SwStateToast;

/**
 * SwToast是一个全局配置的Toast，如果app内部需要不同样式的吐司，每次调用前需要 SwToast.resetToast(); 再配置样式后show()
 *
 * @author DongMS
 * @date 2019/7/5
 */
public class ToastActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);
    }

    public void success(final View view) {
        loadingAfter(view, () -> SwStateToast.success().show(((Button) view).getText().toString()));
    }

    public void warn(final View view) {
        loadingAfter(view, () -> SwStateToast.warning().show(((Button) view).getText().toString()));
    }


    public void error(final View view) {
        loadingAfter(view, () -> SwStateToast.failed().show(((Button) view).getText().toString()));
    }

    private void loadingAfter(final View view, final Callback callback) {
        final SwLoading loading = loading();
        view.postDelayed(() -> {
            loading.hide();
            callback.function();
        }, 1000);
    }

    private SwLoading loading() {
        SwLoading loading = SwLoading.create(this);
        loading.show();
        return loading;
    }

    public void normal(View view) {
        SwToast.resetToast();
        SwToast.showShort("normal");
    }

    public void style(View view) {
        SwToast.resetToast();
        SwToast.setMsgColor(Color.RED);
        SwToast.setBgColor(Color.YELLOW);
        //这里传的是sp值，不是px值
        SwToast.setMsgTextSize(20);
        SwToast.showLong("style");
    }

    public void location(View view) {
        SwToast.resetToast();
        SwToast.setGravity(Gravity.CENTER);
        SwToast.showLong("location");
    }

    public void bgDrawable(View view) {
        SwToast.resetToast();
        SwToast.setBgResource(R.mipmap.ic_launcher_round);
        SwToast.showLong("bgDrawable");
    }

    public void customView(View view) {
        SwToast.resetToast();
        TextView textView = new TextView(getApplicationContext());
        textView.setPadding(30, 30, 30, 30);
        textView.setBackgroundColor(Color.TRANSPARENT);
        textView.setTextColor(Color.BLUE);
         //这里传的是sp值，不是px值
        textView.setTextSize(20);
        textView.setText("customView");
        SwToast.showCustomLong(textView);
    }

    private interface Callback {
        void function();
    }
}
