package com.parkingwang.android.adapter;

import android.util.SparseArray;

/**
 * 抽象的PagerAdapter实现类,封装了内容为View,数据为SparseArray类型的适配器实现.
 *
 * @author DongMS
 * @since 2019/9/12
 */
public abstract class AbstractPagerSparseAdapter<T> extends AbstractViewPagerAdapter {
    protected SparseArray<T> mData;

    public AbstractPagerSparseAdapter(SparseArray<T> data) {
        mData = data;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    public T getItem(int position) {
        return mData.valueAt(position);
    }
}
