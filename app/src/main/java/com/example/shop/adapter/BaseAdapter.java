package com.example.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Android Studio.
 * 项目名称：shop
 * 类描述：基础适配器
 * 功能描述:将信息显示在页面中
 * 创建人：sony
 * 创建时间：2016/2/27 11:26
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @version V1.0
 */
public abstract class BaseAdapter<T, H extends BaseViewHolder> extends RecyclerView.Adapter<BaseViewHolder> {

    protected static final String TAG = BaseAdapter.class.getSimpleName();

    protected final Context context;

    protected int layoutResId;

    protected List<T> datas;

    private OnItemClickListener mOnItemClickListener = null;


    /**
     * 注册列表中信息的点击事件
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


    /**
     * 构造函数
     *
     * @param context
     * @param layoutResId
     */
    public BaseAdapter(Context context, int layoutResId) {
        this(context, layoutResId, null);
    }


    /**
     * 构造函数
     *
     * @param context
     * @param layoutResId
     * @param datas
     */
    public BaseAdapter(Context context, int layoutResId, List<T> datas) {
        this.datas = datas == null ? new ArrayList<T>() : datas;
        this.context = context;
        this.layoutResId = layoutResId;
    }

    /**
     * 创建viewHolder
     *
     * @param viewGroup
     * @param viewType
     * @return
     */
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layoutResId, viewGroup, false);
        BaseViewHolder vh = new BaseViewHolder(view, mOnItemClickListener);
        return vh;
    }

    /**
     * 绑定viewHolder
     *
     * @param viewHoder
     * @param position
     */
    @Override
    public void onBindViewHolder(BaseViewHolder viewHoder, int position) {
        T item = getItem(position);
        convert((H) viewHoder, item);
    }

    /**
     * 获取购物车列表中全部商品
     *
     * @return
     */
    @Override
    public int getItemCount() {
        if (datas == null || datas.size() <= 0) {
            return 0;
        }

        return datas.size();
    }

    /**
     * 根据位置获取商品信息
     *
     * @param position
     * @return
     */
    public T getItem(int position) {
        if (position >= datas.size()) {
            return null;
        }
        return datas.get(position);
    }


    /**
     * 清除所有数据
     */
    public void clear() {
//        int itemCount = datas.size();
//        datas.clear();
//        this.notifyItemRangeRemoved(0,itemCount);

        for (Iterator it = datas.iterator(); it.hasNext(); ) {

            T t = (T) it.next();
            int position = datas.indexOf(t);
            it.remove();
            notifyItemRemoved(position);
        }
    }

    /**
     * 从列表中删除某项
     *
     * @param t
     */
    public void removeItem(T t) {

        int position = datas.indexOf(t);
        datas.remove(position);
        notifyItemRemoved(position);
    }


    /**
     * 获取数据
     *
     * @return
     */
    public List<T> getDatas() {

        return datas;
    }


    /**
     * 添加数据
     *
     * @param datas
     */
    public void addData(List<T> datas) {

        addData(0, datas);
    }

    /**
     * 添加数据
     *
     * @param position
     * @param list
     */
    public void addData(int position, List<T> list) {

        if (list != null && list.size() > 0) {

            for (T t : list) {
                datas.add(position, t);
                notifyItemInserted(position);
            }
        }
    }


    /**
     * 刷新数据
     *
     * @param list
     */
    public void refreshData(List<T> list) {

        if (list != null && list.size() > 0) {

            //清除数据
            clear();
            //重新添加数据
            int size = list.size();
            for (int i = 0; i < size; i++) {
                datas.add(i, list.get(i));
                notifyItemInserted(i);
            }
        }
    }

    /**
     * @param list
     */
    public void loadMoreData(List<T> list) {

        if (list != null && list.size() > 0) {

            int size = list.size();
            int begin = datas.size();
            for (int i = 0; i < size; i++) {
                datas.add(list.get(i));
                notifyItemInserted(i + begin);
            }
        }
    }


    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param viewHoder A fully initialized helper.
     * @param item      The item that needs to be displayed.
     */
    protected abstract void convert(H viewHoder, T item);

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

}
