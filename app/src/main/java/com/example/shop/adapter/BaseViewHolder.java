package com.example.shop.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Android Studio.
 * 项目名称：shop
 * 类描述：视图持有者
 * 功能描述:重新使用view来减少对象重建，listview滚动时快速设置值
 * 创建人：sony
 * 创建时间：2016/2/27 11:26
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @version V1.0
 */
public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    //类似HashMap,但是键值对的key只能是Integer，如果key为String类型，只能用HashMap
    private SparseArray<View> views;

    //定义视图控件的监听事件
    private BaseAdapter.OnItemClickListener mOnItemClickListener;

    public BaseViewHolder(View itemView, BaseAdapter.OnItemClickListener onItemClickListener) {
        super(itemView);
        itemView.setOnClickListener(this);

        this.mOnItemClickListener = onItemClickListener;

        this.views = new SparseArray<View>();
    }

    /**
     * 获取视图中的文本控件
     *
     * @param viewId
     * @return
     */
    public TextView getTextView(int viewId) {
        return retrieveView(viewId);
    }

    /**
     * 获取视图中的按钮控件
     *
     * @param viewId
     * @return
     */
    public Button getButton(int viewId) {
        return retrieveView(viewId);
    }

    /**
     * 获取视图中的图片控件
     *
     * @param viewId
     * @return
     */
    public ImageView getImageView(int viewId) {
        return retrieveView(viewId);
    }

    /**
     * 获取视图
     *
     * @param viewId
     * @return
     */
    public View getView(int viewId) {
        return retrieveView(viewId);
    }


    protected <T extends View> T retrieveView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 根据布局位置注册点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, getLayoutPosition());
        }
    }
}
