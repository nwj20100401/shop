package com.example.shop.bean;

import java.io.Serializable;

/**
 * Created by Android Studio.
 * 项目名称：shop
 * 类描述：购物车商品信息实体类
 * 功能描述:保存购物车中商品的信息
 * 创建人：sony
 * 创建时间：2016/2/27 11:30
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @version V1.0
 */
public class ShoppingCart extends Wares {

    //商品数量
    private int count;
    //商品是否被选中
    private boolean isChecked = true;


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
