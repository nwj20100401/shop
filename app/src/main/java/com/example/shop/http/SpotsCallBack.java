package com.example.shop.http;

import android.content.Context;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import dmax.dialog.SpotsDialog;


/**
 * Created by Android Studio.
 * 项目名称：shop
 * 类描述：网络请求回调
 * 创建人：sony
 * 创建时间：2016/2/26 16:04
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @version V1.0
 */
public abstract class SpotsCallBack<T> extends BaseCallback<T> {


    private Context mContext;

    private SpotsDialog mDialog;

    public SpotsCallBack(Context context) {

        mContext = context;

        initSpotsDialog();
    }


    private void initSpotsDialog() {

        mDialog = new SpotsDialog(mContext, "拼命加载中...");

    }

    public void showDialog() {
        mDialog.show();
    }

    public void dismissDialog() {
        mDialog.dismiss();
    }

    public void setLoadMessage(int resId) {
        mDialog.setMessage(mContext.getString(resId));
    }


    @Override
    public void onFailure(Request request, Exception e) {
        dismissDialog();
    }

    @Override
    public void onBeforeRequest(Request request) {

        showDialog();
    }

    @Override
    public void onResponse(Response response) {
        dismissDialog();
    }
}
