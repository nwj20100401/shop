package com.example.shop.bean;


/**
 * Created by Android Studio.
 * 项目名称：shop
 * 类描述：商品分类信息实体类
 * 创建人：sony
 * 创建时间：2016/2/29 10:09
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @version V1.0
 */
public class Category extends BaseBean {

    private String name;

    public Category() {
    }

    public Category(String name) {

        this.name = name;
    }

    public Category(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
