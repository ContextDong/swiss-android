package com.parkingwang.android.adapter;

import java.util.List;

/**
 * 抽象的PagerAdapter实现类,封装了内容为View,数据为List类型的适配器实现.
 *
 * @author DongMS
 * @since 2019/9/12
 */
public abstract class AbstractPagerListAdapter<T> extends AbstractViewPagerAdapter {

    protected List<T> mData;

    public AbstractPagerListAdapter(List<T> data) {
        mData = data;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    public T getItem(int position) {
        return mData.get(position);
    }
}
