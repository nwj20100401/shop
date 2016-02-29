package com.example.shop.bean;

import java.io.Serializable;

/**
 * Created by Android Studio.
 * 项目名称：shop
 * 类描述：模块信息实体类
 * 创建人：sony
 * 创建时间：2016/2/26 15:23
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @version V1.0
 */
public class Campaign implements Serializable {


    private Long id;
    //标题
    private String title;
    //图片位置
    private String imgUrl;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
