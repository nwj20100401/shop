package com.example.shop.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * Created by Android Studio.
 * 项目名称：shop
 * 类描述：Json数据解析工具类
 * 功能描述:解析json数据
 * 创建人：sony
 * 创建时间：2016/2/27 12:14
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @version V1.0
 */
public class JSONUtil {


    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public static Gson getGson() {
        return gson;
    }

    public static <T> T fromJson(String json, Class<T> clz) {

        return gson.fromJson(json, clz);
    }

    public static <T> T fromJson(String json, Type type) {

        return gson.fromJson(json, type);
    }

    public static String toJSON(Object object) {

        return gson.toJson(object);
    }

}
