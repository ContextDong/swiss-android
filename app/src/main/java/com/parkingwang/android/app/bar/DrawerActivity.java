package com.parkingwang.android.app.bar;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.parkingwang.android.SwBar;
import com.parkingwang.android.app.R;

/**
 * @author DongMS
 * @date 2019/7/4
 */
public class DrawerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        DrawerLayout drawer = findViewById(R.id.drawer);
        View barFakeView = findViewById(R.id.barStatusDrawerFakeStatusBar);
        SwBar.setStatusBarColor4Drawer(drawer, barFakeView, Color.TRANSPARENT, false);
//        SwBar.setStatusBarColor4Drawer(drawer, barFakeView, Color.YELLOW, true);
    }
}
