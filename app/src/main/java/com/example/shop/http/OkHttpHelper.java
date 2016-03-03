package com.example.shop.http;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Android Studio.
 * 项目名称：shop
 * 类描述：网络请求工具类
 * 创建人：sony
 * 创建时间：2016/2/26 16:04
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @version V1.0
 */
public class OkHttpHelper {

    public static final String TAG = "OkHttpHelper";

    //网络请求工具类
    private static OkHttpHelper mInstance;
    //网络请求客户端
    private OkHttpClient mHttpClient;

    private Gson mGson;

    //异步任务处理
    private Handler mHandler;

    /**
     * 网络请求的两种方式  GET   POST
     */
    enum HttpMethodType {
        GET,
        POST,
    }

    /**
     * 获取网络请求实例
     */
    static {
        mInstance = new OkHttpHelper();
    }

    private OkHttpHelper() {
        mHttpClient = new OkHttpClient();
        //设置网络客户端的连接超过时间
        mHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        //设置网络客户端的读取超过时间
        mHttpClient.setReadTimeout(10, TimeUnit.SECONDS);
        //设置网络客户端的写超过时间
        mHttpClient.setWriteTimeout(30, TimeUnit.SECONDS);

        //初始化gson
        mGson = new Gson();

        //初始化异步处理对象
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 单例模式
     *
     * @return
     */
    public static OkHttpHelper getInstance() {
        return mInstance;
    }


    /**
     * get请求
     *
     * @param url
     * @param callback
     */
    public void get(String url, BaseCallback callback) {

        //创建get请求
        Request request = buildGetRequest(url);

        //进行网络请求
        request(request, callback);
    }

    /**
     * 根据url创建get请求
     *
     * @param url
     * @return
     */
    private Request buildGetRequest(String url) {

        return buildRequest(url, HttpMethodType.GET, null);
    }


    /**
     * post请求
     *
     * @param url
     * @param param
     * @param callback
     */
    public void post(String url, Map<String, String> param, BaseCallback callback) {

        //创建post请求
        Request request = buildPostRequest(url, param);

        //进行网络请求
        request(request, callback);
    }

    /**
     * 根据url 请求的参数创建post请求
     *
     * @param url
     * @param params
     * @return
     */
    private Request buildPostRequest(String url, Map<String, String> params) {

        return buildRequest(url, HttpMethodType.POST, params);
    }


    /**
     * 根据url  请求的类型  请求的参数创建请求
     *
     * @param url
     * @param methodType
     * @param params
     * @return
     */
    private Request buildRequest(String url, HttpMethodType methodType, Map<String, String> params) {

        Request.Builder builder = new Request.Builder()
                .url(url);

        if (methodType == HttpMethodType.POST) {
            //请求方式为post时，格式化请求的参数
            RequestBody body = builderFormData(params);
            builder.post(body);
        } else if (methodType == HttpMethodType.GET) {
            builder.get();
        }

        return builder.build();
    }

    /**
     * 请求方式为post时，格式化请求的参数，创建为RequestBody
     *
     * @param params
     * @return
     */
    private RequestBody builderFormData(Map<String, String> params) {

        FormEncodingBuilder builder = new FormEncodingBuilder();

        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }



    /**
     * 网络请求
     *
     * @param request
     * @param callback 网络请求回调接口
     */
    public void request(final Request request, final BaseCallback callback) {

        /**
         * 处理请求前工作
         */
        callback.onBeforeRequest(request);

        //客户端网络请求
        mHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                //请求失败
                callbackFailure(callback, request, e);
            }

            /**
             * 请求成功
             * @param response
             * @throws IOException
             */
            @Override
            public void onResponse(Response response) throws IOException {

                //请求成功
                callbackResponse(callback, response);

                //解析数据
                if (response.isSuccessful()) {

                    //获取网络请求返回的信息
                    String resultStr = response.body().string();

                    Log.d(TAG, "result=" + resultStr);

                    //返回的信息为文本
                    if (callback.mType == String.class) {
                        //请求成功,并返回信息
                        callbackSuccess(callback, response, resultStr);
                    } else {
                        try {
                            //把json转为对象实体
                            Object obj = mGson.fromJson(resultStr, callback.mType);
                            //请求成功,并返回信息
                            callbackSuccess(callback, response, obj);
                        } catch (com.google.gson.JsonParseException e) {
                            // Json解析的错误
                            callback.onError(response, response.code(), e);
                        }
                    }
                } else {
                    //异步请求出现错误
                    callbackError(callback, response, null);
                }
            }
        });
    }

    /**
     * 请求失败
     *
     * @param callback
     * @param request
     * @param e
     */
    private void callbackFailure(final BaseCallback callback, final Request request, final IOException e) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onFailure(request, e);
            }
        });
    }

    /**
     * 请求成功
     *
     * @param callback
     * @param response
     */
    private void callbackResponse(final BaseCallback callback, final Response response) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(response);
            }
        });
    }

    /**
     * 异步请求成功，并且返回信息
     *
     * @param callback
     * @param response
     * @param obj
     */
    private void callbackSuccess(final BaseCallback callback, final Response response, final Object obj) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(response, obj);
            }
        });
    }

    /**
     * 异步请求出现错误
     *
     * @param callback
     * @param response
     * @param e
     */
    private void callbackError(final BaseCallback callback, final Response response, final Exception e) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(response, response.code(), e);
            }
        });
    }
}
