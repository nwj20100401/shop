package com.example.shop.http;

import android.content.Context;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import dmax.dialog.SpotsDialog;

/**
 * Created by Android Studio.
 * 项目名称：shop
 * 类描述：开源项目，spotsDialog工具类
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

    //开原项目,进度对话框
    private SpotsDialog mDialog;

    public SpotsCallBack(Context context) {

        mContext = context;

        //初始化进度对话框
        initSpotsDialog();
    }


    /**
     * 初始化进度对话框
     */
    private void initSpotsDialog() {
		
		//创建对话框
        mDialog = new SpotsDialog(mContext, "拼命加载中...");
    }

    /**
     * 弹出对话框
     */
    public void showDialog() {
        mDialog.show();
    }

    /**
     * 关闭对话框
     */
    public void dismissDialog() {
        mDialog.dismiss();
    }

    /**
     * 根据资源id获取对话框显示的文字
     *
     * @param resId
     */
    public void setLoadMessage(int resId) {
        mDialog.setMessage(mContext.getString(resId));
    }

    /**
     * 网络请求失败，关闭对话框
     *
     * @param request
     * @param e
     */
    @Override
    public void onFailure(Request request, Exception e) {
        dismissDialog();
    }

    /**
     * 网络请求之前打开对话框
     *
     * @param request
     */
    @Override
    public void onBeforeRequest(Request request) {
        showDialog();
    }

    /**
     * 网络请求响应之后，关闭对话框
     *
     * @param response
     */
    @Override
    public void onResponse(Response response) {
        dismissDialog();
    }
}
