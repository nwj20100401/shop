package com.example.shop.utils;

import android.content.Context;
import android.util.SparseArray;

import com.example.shop.bean.ShoppingCart;
import com.example.shop.bean.Wares;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Android Studio.
 * 项目名称：shop
 * 类描述：购物车数据提供
 * 功能描述:为购物车商品信息提供数据功能
 * 创建人：sony
 * 创建时间：2016/2/27 12:14
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @version V1.0
 */
public class CartProvider {

    public static final String CART_JSON = "cart_json";

    //商品数量列表
    private SparseArray<ShoppingCart> datas = null;

    private Context mContext;


    /**
     * 构造函数
     *
     * @param context
     */
    public CartProvider(Context context) {

        mContext = context;
        //初始化数组
        datas = new SparseArray<>(10);
        //解析数据
        listToSparse();
    }


    /**
     * 将选中的商品放到商品信息列表中
     *
     * @param cart
     */
    public void put(ShoppingCart cart) {

        ShoppingCart temp = datas.get(cart.getId().intValue());

        if (temp != null) {
            //设置当前商品的数量
            temp.setCount(temp.getCount() + 1);
        } else {
            temp = cart;
            temp.setCount(1);
        }
        //将选中的商品添加商品信息列表中
        datas.put(cart.getId().intValue(), temp);
        //保存商品信息列表到本地
        commit();
    }


    /**
     * 将商品放到购物车中
     * @param wares
     */
    public void put(Wares wares) {

        ShoppingCart cart = convertData(wares);
        put(cart);
    }

    /**
     * 更新商品数量
     *
     * @param cart
     */
    public void update(ShoppingCart cart) {
        datas.put(cart.getId().intValue(), cart);
        commit();
    }

    /**
     * 删除选中的商品
     *
     * @param cart
     */
    public void delete(ShoppingCart cart) {
        datas.delete(cart.getId().intValue());
        commit();
    }

    /**
     * 获取所有商品数量列表
     *
     * @return
     */
    public List<ShoppingCart> getAll() {

        return getDataFromLocal();
    }


    /**
     * 将解析成的的商品列表保存在本地
     */
    public void commit() {

        List<ShoppingCart> carts = sparseToList();

        PreferencesUtils.putString(mContext, CART_JSON, JSONUtil.toJSON(carts));
    }


    /**
     * 将商品数据键值对解析为列表
     *
     * @return
     */
    private List<ShoppingCart> sparseToList() {

        int size = datas.size();
        List<ShoppingCart> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(datas.valueAt(i));
        }
        return list;

    }


    /**
     * 将获取的数据列表放在键值对中
     */
    private void listToSparse() {

        List<ShoppingCart> carts = getDataFromLocal();

        if (carts != null && carts.size() > 0) {

            for (ShoppingCart cart : carts) {
                datas.put(cart.getId().intValue(), cart);
            }
        }
    }

    /**
     * 从本地获取商品数量列表数据
     *
     * @return
     */
    public List<ShoppingCart> getDataFromLocal() {

        String json = PreferencesUtils.getString(mContext, CART_JSON);
        List<ShoppingCart> carts = null;

        //解析服务器端返回的数据
        if (json != null) {
            carts = JSONUtil.fromJson(json, new TypeToken<List<ShoppingCart>>() {
            }.getType());
        }

        return carts;
    }

    /**
     * 保存为购物车中的商品信息
     *
     * @param item
     * @return
     */
    public ShoppingCart convertData(Wares item) {

        ShoppingCart cart = new ShoppingCart();

        cart.setId(item.getId());
        cart.setDescription(item.getDescription());
        cart.setImgUrl(item.getImgUrl());
        cart.setName(item.getName());
        cart.setPrice(item.getPrice());

        return cart;
    }
}
