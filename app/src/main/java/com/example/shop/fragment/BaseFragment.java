package com.example.shop.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;


/**
 * Created by Android Studio.
 * 项目名称：shop
 * 类描述：抽象出基础fragment，BaseFragment
 * 创建人：sony
 * 创建时间：2016/2/26 15:23
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @version V1.0
 */
public abstract class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //创建view视图
        View view = createView(inflater, container, savedInstanceState);
        //进行UI绑定
        ViewUtils.inject(this, view);
        //初始化ToolBar,ToolBar用于替代ActionBar
        initToolBar();
        //空的初始化方法，子类实现抽象类时，重写该方法
        init();
        //返回视图
        return view;
    }

    /**
     * 初始化ToolBar
     */
    public void initToolBar() {

    }

    /**
     * 创建view视图
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public abstract View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * 空的初始化方法
     */
    public abstract void init();
}
