package com.example.shop.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.shop.R;
import com.example.shop.bean.Campaign;
import com.example.shop.bean.HomeCampaign;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Android Studio.
 * 项目名称：shop
 * 类描述：首页动态模块适配器
 * 创建人：sony
 * 创建时间：2016/2/26 16:16
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @version V1.0
 */
public class HomeCatgoryAdapter extends RecyclerView.Adapter<HomeCatgoryAdapter.ViewHolder> {

    //视图左边
    private static int VIEW_TYPE_L = 0;
    //视图右边
    private static int VIEW_TYPE_R = 1;

    //定义添加布局对象
    private LayoutInflater mInflater;

    //动态模块数据列表
    private List<HomeCampaign> mDatas;

    private Context mContext;

    //模块的点击事件
    private OnCampaignClickListener mListener;


    /**
     * 构造函数
     *
     * @param datas
     * @param context
     */
    public HomeCatgoryAdapter(List<HomeCampaign> datas, Context context) {
        mDatas = datas;
        this.mContext = context;
    }


    /**
     * 注册监听事件
     *
     * @param listener
     */
    public void setOnCampaignClickListener(OnCampaignClickListener listener) {
        this.mListener = listener;
    }

    /**
     * 创建viewHolder
     *
     * @param viewGroup
     * @param type
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {

        mInflater = LayoutInflater.from(viewGroup.getContext());
        if (type == VIEW_TYPE_R) {

            return new ViewHolder(mInflater.inflate(R.layout.template_home_cardview2, viewGroup, false));
        }

        return new ViewHolder(mInflater.inflate(R.layout.template_home_cardview, viewGroup, false));
    }

    /**
     * 绑定viewHolder
     *
     * @param viewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        //根据位置获取动态模块
        HomeCampaign homeCampaign = mDatas.get(i);
        //设置模块的标题
        viewHolder.textTitle.setText(homeCampaign.getTitle());

        /*图片的动态轮播*/
        Picasso.with(mContext).load(homeCampaign.getCpOne().getImgUrl()).into(viewHolder.imageViewBig);
        Picasso.with(mContext).load(homeCampaign.getCpTwo().getImgUrl()).into(viewHolder.imageViewSmallTop);
        Picasso.with(mContext).load(homeCampaign.getCpThree().getImgUrl()).into(viewHolder.imageViewSmallBottom);
    }

    /**
     * 获取动态模块的数量
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * 根据位置获取动态模块的类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {

        if (position % 2 == 0) {
            return VIEW_TYPE_R;
        } else {
            return VIEW_TYPE_L;
        }
    }

    /**
     * ViewHolder
     */
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textTitle;
        ImageView imageViewBig;
        ImageView imageViewSmallTop;
        ImageView imageViewSmallBottom;

        public ViewHolder(View itemView) {
            super(itemView);

            //获取控件
            textTitle = (TextView) itemView.findViewById(R.id.text_title);
            imageViewBig = (ImageView) itemView.findViewById(R.id.imgview_big);
            imageViewSmallTop = (ImageView) itemView.findViewById(R.id.imgview_small_top);
            imageViewSmallBottom = (ImageView) itemView.findViewById(R.id.imgview_small_bottom);

            //设置控件的监听事件
            imageViewBig.setOnClickListener(this);
            imageViewSmallTop.setOnClickListener(this);
            imageViewSmallBottom.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (mListener != null) {
                //点击图片的动画效果
                anim(v);
            }
        }

        /**
         * 点击图片的动画效果
         *
         * @param v
         */
        private void anim(final View v) {

            ObjectAnimator animator = ObjectAnimator.ofFloat(v, "rotationX", 0.0F, 360.0F)
                    .setDuration(200);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {

                    //根据布局位置获取动态模块
                    HomeCampaign campaign = mDatas.get(getLayoutPosition());

                    /*绑定图片与动态模块*/
                    switch (v.getId()) {

                        case R.id.imgview_big:
                            mListener.onClick(v, campaign.getCpOne());
                            break;

                        case R.id.imgview_small_top:
                            mListener.onClick(v, campaign.getCpTwo());
                            break;

                        case R.id.imgview_small_bottom:
                            mListener.onClick(v, campaign.getCpThree());
                            break;
                    }

                }
            });
            animator.start();
        }
    }

    /**
     * 模块的点击事件接口
     */
    public interface OnCampaignClickListener {

        void onClick(View view, Campaign campaign);
    }

}
