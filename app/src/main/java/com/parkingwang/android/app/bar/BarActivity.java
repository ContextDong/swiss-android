package com.parkingwang.android.app.bar;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.parkingwang.android.SwBar;
import com.parkingwang.android.SwColor;
import com.parkingwang.android.app.R;

/**
 * @author DongMS
 * @since 2019/5/27
 */
public class BarActivity extends AppCompatActivity {

    private TextView mTvBarColor;
    private ScrollView mSvRoot;
    private int mStatusBarColor;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);
        CheckBox switchMode = findViewById(R.id.cb_mode);
        mTvBarColor = findViewById(R.id.tv_bar_color);
        mSvRoot = findViewById(R.id.sv_root);

        //默认浅色字体
        switchMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SwBar.setStatusBarLightMode(BarActivity.this, isChecked);
            }
        });

        setStatusBarColor();
        SwBar.addMarginTopEqualStatusBarHeight(mSvRoot);//调用一次即可
        setNavBarColor();
        getBarColor();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setNavBarColor() {
        if (SwBar.isSupportNavBar()) {
            int randomColor = getRandomColor();
            SwBar.setNavBarColor(this, randomColor);
            getBarColor();
        }
    }

    private void setStatusBarColor() {
        mStatusBarColor = getRandomColor();
        SwBar.setStatusBarColor(this, mStatusBarColor);
        getBarColor();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void getBarColor() {
        StringBuilder builder = new StringBuilder();
        builder.append("状态栏颜色:").append(SwColor.int2ArgbString(mStatusBarColor));
        if (SwBar.isSupportNavBar()) {
            builder.append("\n").append("虚拟导航栏颜色:").append(SwColor.int2RgbString(SwBar.getNavBarColor(this)));
        }
        mTvBarColor.setText(builder.toString());
    }

    public void changeStatusColor(View view) {
        setStatusBarColor();
    }

    public void picStatusTransparent(View view) {
        startActivity(new Intent(this, PicTransparentActivity.class));
    }

    public void toDrawLayout(View view) {
        startActivity(new Intent(this, DrawerActivity.class));
    }

    public void openNotification(View view) {
        SwBar.setNotificationBarVisibility(true);
        mSvRoot.postDelayed(new Runnable() {
            @Override
            public void run() {
                SwBar.setNotificationBarVisibility(false);
            }
        }, 1000);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void changeNavColor(View view) {
        setNavBarColor();
    }

    public int getRandomColor() {
        int high = (int) (Math.random() * 0x100) << 24;
        return high | (int) (Math.random() * 0x1000000);
    }

}
