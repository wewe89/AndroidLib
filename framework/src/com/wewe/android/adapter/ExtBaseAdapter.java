package com.wewe.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.wewe.android.domain.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2014/10/31.
 */
public abstract class ExtBaseAdapter<T extends BaseBean> extends BaseAdapter {
    @Override
    public int getCount() {
        return mData.size();
    }

    public void clear() {
        if(mData!=null)mData.clear();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    abstract public View getView(int position, View convertView, ViewGroup parent);

    protected LayoutInflater mInflater;
    protected List<T> mData = null;

    public ExtBaseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mData = new ArrayList<T>();
    }

    public void addItem(T item) {
        mData.add(item);
    }

    public void setData(List<T> data) {
        mData.clear();
        mData.addAll(data);
    }
}
