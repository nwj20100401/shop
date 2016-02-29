package com.example.shop.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.Html;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;


import com.example.shop.R;
import com.example.shop.bean.ShoppingCart;
import com.example.shop.utils.CartProvider;
import com.example.shop.widget.NumberAddSubView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Iterator;
import java.util.List;


/**
 * Created by Android Studio.
 * 项目名称：shop
 * 类描述：购物车适配器
 * 功能描述:将商品信息显示在页面中
 * 创建人：sony
 * 创建时间：2016/2/27 11:26
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @version V1.0
 */
public class CartAdapter extends SimpleAdapter<ShoppingCart> implements BaseAdapter.OnItemClickListener {

    public static final String TAG = "CartAdapter";

    //商品信息前单选按钮
    private CheckBox checkBox;
    //商品信息
    private TextView textView;

    //定义购物车商品信息提供者
    private CartProvider cartProvider;

    /**
     * 构造函数
     *
     * @param context
     * @param datas    商品信息列表
     * @param checkBox 单选框
     * @param tv
     */
    public CartAdapter(Context context, List<ShoppingCart> datas, final CheckBox checkBox, TextView tv) {
        super(context, R.layout.template_cart, datas);

        setCheckBox(checkBox);
        setTextView(tv);

        //实例化购物车数据提供
        cartProvider = new CartProvider(context);

        //注册列表条目单击事件
        setOnItemClickListener(this);

        //显示所有选中商品的价格
        showTotalPrice();
    }


    @Override
    protected void convert(BaseViewHolder viewHoder, final ShoppingCart item) {

        //设置商品标题
        viewHoder.getTextView(R.id.text_title).setText(item.getName());
        //设置商品价格
        viewHoder.getTextView(R.id.text_price).setText("￥" + item.getPrice());
        //显示商品图片
        SimpleDraweeView draweeView = (SimpleDraweeView) viewHoder.getView(R.id.drawee_view);
        draweeView.setImageURI(Uri.parse(item.getImgUrl()));

        //设置单选框
        CheckBox checkBox = (CheckBox) viewHoder.getView(R.id.checkbox);
        checkBox.setChecked(item.isChecked());

        //获取商品数量控件
        NumberAddSubView numberAddSubView = (NumberAddSubView) viewHoder.getView(R.id.num_control);
        //设置商品数量
        numberAddSubView.setValue(item.getCount());

        //添加商品按钮的点击事件
        numberAddSubView.setOnButtonClickListener(new NumberAddSubView.OnButtonClickListener() {
            @Override
            public void onButtonAddClick(View view, int value) {

                //设置商品数量
                item.setCount(value);
                //更新数据
                cartProvider.update(item);
                //显示商品总价值
                showTotalPrice();
            }

            /**
             * 减少商品按钮的点击事件
             * @param view
             * @param value
             */
            @Override
            public void onButtonSubClick(View view, int value) {
                //设置商品数量
                item.setCount(value);
                //更新数据
                cartProvider.update(item);
                //显示商品总价值
                showTotalPrice();
            }
        });


    }

    /**
     * 显示商品总价值
     */
    public void showTotalPrice() {
        //获取商品总价值
        float total = getTotalPrice();

        //设置文本的显示
        textView.setText(Html.fromHtml("合计 ￥<span style='color:#eb4f38'>" + total + "</span>"), TextView.BufferType.SPANNABLE);
    }

    /**
     * 获取商品总价值
     *
     * @return
     */
    private float getTotalPrice() {

        float sum = 0;
        if (!isNull()) {
            return sum;
        }

        for (ShoppingCart cart : datas) {
            if (cart.isChecked()) {
                sum += cart.getCount() * cart.getPrice();
            }
        }

        return sum;
    }


    /**
     * 购物车商品信息列表不为空
     *
     * @return
     */
    private boolean isNull() {

        return (datas != null && datas.size() > 0);
    }


    /**
     * 购物车商品信息列表的点击事件
     *
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(View view, int position) {

        ShoppingCart cart = getItem(position);
        cart.setIsChecked(!cart.isChecked());
        notifyItemChanged(position);

        checkListen();
        showTotalPrice();

    }


    private void checkListen() {


        int count = 0;
        int checkNum = 0;
        if (datas != null) {
            count = datas.size();

            for (ShoppingCart cart : datas) {
                if (!cart.isChecked()) {
                    checkBox.setChecked(false);
                    break;
                } else {
                    checkNum = checkNum + 1;
                }
            }

            if (count == checkNum) {
                checkBox.setChecked(true);
            }

        }
    }


    public void checkAll_None(boolean isChecked) {


        if (!isNull())
            return;


        int i = 0;
        for (ShoppingCart cart : datas) {
            cart.setIsChecked(isChecked);
            notifyItemChanged(i);
            i++;
        }


    }


    public void delCart() {


        if (!isNull())
            return;

//        for (ShoppingCart cart : datas){
//
//            if(cart.isChecked()){
//                int position = datas.indexOf(cart);
//                cartProvider.delete(cart);
//                datas.remove(cart);
//                notifyItemRemoved(position);
//            }
//        }


        for (Iterator iterator = datas.iterator(); iterator.hasNext(); ) {

            ShoppingCart cart = (ShoppingCart) iterator.next();
            if (cart.isChecked()) {
                int position = datas.indexOf(cart);
                cartProvider.delete(cart);
                iterator.remove();
                notifyItemRemoved(position);
            }

        }


    }


    public void setTextView(TextView textview) {
        this.textView = textview;
    }

    public void setCheckBox(CheckBox ck) {
        this.checkBox = ck;

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkAll_None(checkBox.isChecked());
                showTotalPrice();

            }
        });
    }


}
