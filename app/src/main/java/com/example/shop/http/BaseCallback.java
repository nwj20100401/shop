package com.example.shop.http;

import com.google.gson.internal.$Gson$Types;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Android Studio.
 * 项目名称：shop
 * 类描述：网络请求回调抽象类,获取gson规范的父类类型参数
 * 创建人：sony
 * 创建时间：2016/2/26 16:04
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @version V1.0
 */
public abstract class BaseCallback<T> {

	//父类类型
    public Type mType;

    static Type getSuperclassTypeParameter(Class<?> subclass) {
		//获取泛型类型数组
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
		//将泛型类型转为参数化的类型
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }


    public BaseCallback() {
        mType = getSuperclassTypeParameter(getClass());
    }

    /**
     * 请求之前时调用此方法
     * @param request
     */
    public abstract void onBeforeRequest(Request request);

    /**
     * 请求失败时调用此方法
     * @param request
     * @param e
     */
    public abstract void onFailure(Request request, Exception e);

    /**
     * 请求成功时获取响应调用此方法
     *
     * @param response
     */
    public abstract void onResponse(Response response);

    /**
     * 状态码大于200，小于300 时调用此方法
     *
     * @param response
     * @param t
     * @throws IOException
     */
    public abstract void onSuccess(Response response, T t);

    /**
     * 状态码400，404，403，500等时调用此方法
     *
     * @param response
     * @param code
     * @param e
     */
    public abstract void onError(Response response, int code, Exception e);

}