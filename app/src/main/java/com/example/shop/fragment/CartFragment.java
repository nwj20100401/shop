package com.example.shop.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.shop.R;
import com.example.shop.adapter.CartAdapter;
import com.example.shop.adapter.decoration.DividerItemDecoration;
import com.example.shop.bean.ShoppingCart;
import com.example.shop.utils.CartProvider;
import com.example.shop.widget.CNiaoToolBar;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.List;

/**
 * Created by Android Studio.
 * 项目名称：shop
 * 类描述：购物车activity
 * 功能描述:实现购物车功能
 * 创建人：sony
 * 创建时间：2016/2/27 11:23
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @version V1.0
 */

public class CartFragment extends BaseFragment implements View.OnClickListener {

    //编辑
    public static final int ACTION_EDIT = 1;
    //全选
    public static final int ACTION_CAMPLATE = 2;

    //定义商品列表
    @ViewInject(R.id.recycler_view)
    private RecyclerView mRecyclerView;

    //定义全选按钮
    @ViewInject(R.id.checkbox_all)
    private CheckBox mCheckBox;

    //定义商品总价文本
    @ViewInject(R.id.txt_total)
    private TextView mTextTotal;

    //定义结算按钮
    @ViewInject(R.id.btn_order)
    private Button mBtnOrder;

    //定义删除按钮
    @ViewInject(R.id.btn_del)
    private Button mBtnDel;

    //定义ToolBar
    @ViewInject(R.id.toolbar)
    protected CNiaoToolBar mToolbar;

    //定义购物车适配器
    private CartAdapter mAdapter;
    private CartProvider cartProvider;


    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }


    @Override
    public void init() {
        cartProvider = new CartProvider(getContext());

        changeToolbar();
        showData();
    }


    @OnClick(R.id.btn_del)
    public void delCart(View view) {

        mAdapter.delCart();
    }


    private void showData() {


        List<ShoppingCart> carts = cartProvider.getAll();

        mAdapter = new CartAdapter(getContext(), carts, mCheckBox, mTextTotal);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));


    }


    public void refData() {

        mAdapter.clear();
        List<ShoppingCart> carts = cartProvider.getAll();
        mAdapter.addData(carts);
        mAdapter.showTotalPrice();


    }


    public void changeToolbar() {

        mToolbar.hideSearchView();
        mToolbar.showTitleView();
        mToolbar.setTitle(R.string.cart);
        mToolbar.getRightButton().setVisibility(View.VISIBLE);
        mToolbar.setRightButtonText("编辑");

        mToolbar.getRightButton().setOnClickListener(this);

        mToolbar.getRightButton().setTag(ACTION_EDIT);


    }


    private void showDelControl() {
        mToolbar.getRightButton().setText("完成");
        mTextTotal.setVisibility(View.GONE);
        mBtnOrder.setVisibility(View.GONE);
        mBtnDel.setVisibility(View.VISIBLE);
        mToolbar.getRightButton().setTag(ACTION_CAMPLATE);

        mAdapter.checkAll_None(false);
        mCheckBox.setChecked(false);

    }

    private void hideDelControl() {

        mTextTotal.setVisibility(View.VISIBLE);
        mBtnOrder.setVisibility(View.VISIBLE);


        mBtnDel.setVisibility(View.GONE);
        mToolbar.setRightButtonText("编辑");
        mToolbar.getRightButton().setTag(ACTION_EDIT);

        mAdapter.checkAll_None(true);
        mAdapter.showTotalPrice();

        mCheckBox.setChecked(true);
    }


    @Override
    public void onClick(View v) {
        int action = (int) v.getTag();
        if (ACTION_EDIT == action) {

            showDelControl();
        } else if (ACTION_CAMPLATE == action) {
            hideDelControl();
        }
    }
}
