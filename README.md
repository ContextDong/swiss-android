# Swiss Android - Android常用工具库

### 使用

```java
    public class App extends Application {

        @Override
        public void onCreate() {
            super.onCreate();
            //使用前必须初始化
            SwUtils.init(this);
        }
    }
```

### SwBar - 沉浸式设计

```
    //设置状态栏颜色
    SwBar.setStatusBarColor(activity, statusBarColor);

    //设置底部导航栏颜色
    SwBar.setNavBarColor(activity, navBarColor);

    //改变状态栏字体的颜色,true:深色;false:浅色
    SwBar.setStatusBarLightMode(activity, false);

    //状态栏隐藏，需要占位原先状态栏的高度
    SwBar.addMarginTopEqualStatusBarHeight(addMarginTopView);

    //兼容DrawerLayout
    SwBar.setStatusBarColor4Drawer(drawer, barFakeView, Color.TRANSPARENT, false);
```

###  SwKeyboard - 系统键盘工具类

```
    //隐藏键盘
    void hideSoftInput(final Activity activity)

    //显示键盘
    void showSoftInput(final Activity activity)

    // 修复软键盘的内存泄漏，activity的onDestroy()回调
    void fixSoftInputLeaks(final Activity activity)

    //另外，提供点击外部区域隐藏键盘的方法
    1.KeyboardActivity extends AppCompatActivity implements KeyboardListener

    2.@Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return mKeyboardHelper.dispatchTouchEvent(this, event) || super.dispatchTouchEvent(event);
    }

    3.@Override
    protected void onDestroy() {
        mKeyboardHelper.onDestroy(this);
        super.onDestroy();
        ...
    }

```

### SwResource - 更方便地获取资源文件

```
    int getColor(@ColorRes int colorResId)

    String getString(@StringRes int strResId)

    String[] getStringArray(@ArrayRes int strArrResId)

    getDrawable(@DrawableRes int drawableResId)
```

### SwOnResult - 更友好地处理onActivityResult回调

```
    new SwOnResult(this).startForResult(ToastActivity.class, (resultCode, data) -> {
        if (resultCode == Activity.RESULT_OK) {
            SwToast.showShort(data.getStringExtra("msg"));
        }
   });
```

### SwDoubleClick - 双击退出App

```java
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mDoubleClick.onKeyDown(keyCode, event) ||
                super.onKeyDown(keyCode, event);
    }
}
```

### SwLoading - 一款Loading弹窗口组件

```
    SwLoading.create(context).show();
```

### SwStateToast - 提供默认状态布局Toast提示组件

支持 Tip, Success, Failed, Warning 三个提示类型；

----

## 依赖

```gradle
dependencies {
    implementation 'com.cherry.tools:swiss-android:1.0.0'
}
```


