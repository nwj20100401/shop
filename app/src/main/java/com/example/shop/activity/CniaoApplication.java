package com.example.shop.activity;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by Android Studio.
 * 项目名称：shop
 * 类描述：自定义应用上下文
 * 创建人：sony
 * 创建时间：2016/2/26 15:17
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @version V1.0
 */
public class CniaoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        初始化fresco图片加载工具
        Fresco.initialize(this);
    }
}
