package com.example.shop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shop.R;

/**
 * Created by Android Studio.
 * 项目名称：shop
 * 类描述：我的信息fragment
 * 创建人：sony
 * 创建时间：2016/2/26 16:04
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @version V1.0
 */
public class MineFragment extends BaseFragment {


    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @Override
    public void init() {

    }
}
