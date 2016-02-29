package com.example.shop.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.shop.R;
import com.example.shop.bean.Wares;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;


/**
 * Created by Android Studio.
 * 项目名称：shop
 * 类描述：热门商品信息适配器
 * 创建人：sony
 * 创建时间：2016/2/29 09:52
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @version V1.0
 */
public class HotWaresAdapter extends RecyclerView.Adapter<HotWaresAdapter.ViewHolder> {

    //定义商品列表列表
    private List<Wares> mDatas;

    private LayoutInflater mInflater;

    public HotWaresAdapter(List<Wares> wares) {

        mDatas = wares;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.template_hot_wares, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Wares wares = getData(position);

        holder.draweeView.setImageURI(Uri.parse(wares.getImgUrl()));
        holder.textTitle.setText(wares.getName());
        holder.textPrice.setText("￥" + wares.getPrice());
    }


    public Wares getData(int position) {
        return mDatas.get(position);
    }


    public List<Wares> getDatas() {
        return mDatas;
    }

    public void clearData() {
        mDatas.clear();
        notifyItemRangeRemoved(0, mDatas.size());
    }

    public void addData(List<Wares> datas) {
        addData(0, datas);
    }

    public void addData(int position, List<Wares> datas) {
        if (datas != null && datas.size() > 0) {
            mDatas.addAll(datas);
            notifyItemRangeChanged(position, mDatas.size());
        }
    }

    @Override
    public int getItemCount() {

        if (mDatas != null && mDatas.size() > 0) {
            return mDatas.size();
        }
        return 0;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        /*获取控件信息*/
        SimpleDraweeView draweeView;
        TextView textTitle;
        TextView textPrice;


        public ViewHolder(View itemView) {
            super(itemView);

            draweeView = (SimpleDraweeView) itemView.findViewById(R.id.drawee_view);
            textTitle = (TextView) itemView.findViewById(R.id.text_title);
            textPrice = (TextView) itemView.findViewById(R.id.text_price);
        }
    }
}
