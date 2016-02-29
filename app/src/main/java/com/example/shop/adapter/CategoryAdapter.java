package com.example.shop.adapter;

import android.content.Context;

import com.example.shop.R;
import com.example.shop.bean.Category;

import java.util.List;


/**
 * Created by Android Studio.
 * 项目名称：shop
 * 类描述：商品分组适配器
 * 创建人：sony
 * 创建时间：2016/2/29 09:49
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @version V1.0
 */
public class CategoryAdapter extends SimpleAdapter<Category> {


    public CategoryAdapter(Context context, List<Category> datas) {
        super(context, R.layout.template_single_text, datas);
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, Category item) {

        viewHoder.getTextView(R.id.textView).setText(item.getName());
    }
}
