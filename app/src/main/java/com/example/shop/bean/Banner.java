package com.example.shop.bean;

/**
 * Created by Android Studio.
 * 项目名称：shop
 * 类描述：图片轮播实体类
 * 功能描述：保存轮播图片的具体信息
 * 创建人：sony
 * 创建时间：2016/2/26 15:54
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @version V1.0
 */
public class Banner extends BaseBean {

    private String name;
    private String imgUrl;
    private String description;

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
}
