package com.example.shop.bean;

import java.io.Serializable;

/**
 * Created by Android Studio.
 * 项目名称：shop
 * 类描述：基础实体类
 * 功能描述：实现序列化接口，用于保存对象状态，保存到磁盘中，也可以方便传输
 * 创建人：sony
 * 创建时间：2016/2/26 15:54
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @version V1.0
 */
public class BaseBean implements Serializable {

    protected long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
