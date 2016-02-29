package com.example.shop.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.Button;


import com.example.shop.R;
import com.example.shop.bean.Wares;
import com.example.shop.utils.CartProvider;
import com.example.shop.utils.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;


/**
 * Created by Android Studio.
 * 项目名称：shop
 * 类描述：商品信息列表适配器
 * 创建人：sony
 * 创建时间：2016/2/29 09:52
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @version V1.0
 */
public class HWAdatper extends SimpleAdapter<Wares> {

    CartProvider provider;

    public HWAdatper(Context context, List<Wares> datas) {
        super(context, R.layout.template_hot_wares, datas);

        //初始化商品信息提供者
        provider = new CartProvider(context);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, final Wares wares) {

        //开源项目，加载商品图片
        SimpleDraweeView draweeView = (SimpleDraweeView) viewHolder.getView(R.id.drawee_view);
        draweeView.setImageURI(Uri.parse(wares.getImgUrl()));

        viewHolder.getTextView(R.id.text_title).setText(wares.getName());
        viewHolder.getTextView(R.id.text_price).setText("￥ " + wares.getPrice());

        Button button = viewHolder.getButton(R.id.btn_add);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //将商品信息放到购物车中
                    provider.put(wares);
                    ToastUtils.show(context, "已添加到购物车");
                }
            });
        }
    }


    /**
     * 刷新布局
     *
     * @param layoutId
     */
    public void resetLayout(int layoutId) {

        this.layoutResId = layoutId;
        notifyItemRangeChanged(0, getDatas().size());
    }
}
