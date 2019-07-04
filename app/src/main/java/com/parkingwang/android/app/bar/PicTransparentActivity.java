package com.parkingwang.android.app.bar;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.parkingwang.android.SwBar;
import com.parkingwang.android.app.R;

/**
 * @author DongMS
 * @date 2019/5/28
 */
public class PicTransparentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_transparent);
        Toolbar toolbar = findViewById(R.id.toolbar);
        SwBar.setStatusBarColor(this, Color.TRANSPARENT);
        SwBar.addMarginTopEqualStatusBarHeight(toolbar);
    }
}
