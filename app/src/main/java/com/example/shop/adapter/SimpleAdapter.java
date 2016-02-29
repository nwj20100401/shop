package com.example.shop.adapter;

import android.content.Context;

import java.util.List;

/**
 * Created by Android Studio.
 * 项目名称：shop
 * 类描述：基础适配器
 * 创建人：sony
 * 创建时间：2016/2/29 09:49
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @version V1.0
 */
public abstract class SimpleAdapter<T> extends BaseAdapter<T, BaseViewHolder> {

    public SimpleAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
    }

    public SimpleAdapter(Context context, int layoutResId, List<T> datas) {
        super(context, layoutResId, datas);
    }
}
