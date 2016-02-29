package com.example.shop.adapter;

import android.content.Context;
import android.net.Uri;


import com.example.shop.R;
import com.example.shop.bean.Wares;
import com.facebook.drawee.view.SimpleDraweeView;

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
public class WaresAdapter extends SimpleAdapter<Wares> {


    public WaresAdapter(Context context, List<Wares> datas) {
        super(context, R.layout.template_grid_wares, datas);
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, Wares item) {

        viewHoder.getTextView(R.id.text_title).setText(item.getName());
        viewHoder.getTextView(R.id.text_price).setText("￥" + item.getPrice());
        SimpleDraweeView draweeView = (SimpleDraweeView) viewHoder.getView(R.id.drawee_view);
        draweeView.setImageURI(Uri.parse(item.getImgUrl()));
    }
}
