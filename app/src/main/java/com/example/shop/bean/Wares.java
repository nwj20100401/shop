package com.example.shop.bean;

import java.io.Serializable;

/**
 * Created by Android Studio.
 * 项目名称：shop
 * 类描述：商品信息实体类
 * 功能描述:保存商品信息
 * 创建人：sony
 * 创建时间：2016/2/27 11:28
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @version V1.0
 */
public class Wares implements Serializable {

    //商品id
    private Long id;
    //商品名臣
    private String name;
    //商品图片地址
    private String imgUrl;
    //商品描述
    private String description;
    //商品价格
    private Float price;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
