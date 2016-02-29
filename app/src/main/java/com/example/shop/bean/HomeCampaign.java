package com.example.shop.bean;

import java.io.Serializable;

/**
 * Created by Android Studio.
 * 项目名称：shop
 * 类描述：首页动态模块实体类
 * 创建人：sony
 * 创建时间：2016/2/26 15:23
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @version V1.0
 */
public class HomeCampaign implements Serializable {

    private Long id;
    //模块标题
    private String title;
    //模块1
    private Campaign cpOne;
    //模块2
    private Campaign cpTwo;
    //模块3
    private Campaign cpThree;


    public Campaign getCpOne() {
        return cpOne;
    }

    public void setCpOne(Campaign cpOne) {
        this.cpOne = cpOne;
    }

    public Campaign getCpTwo() {
        return cpTwo;
    }

    public void setCpTwo(Campaign cpTwo) {
        this.cpTwo = cpTwo;
    }

    public Campaign getCpThree() {
        return cpThree;
    }

    public void setCpThree(Campaign cpThree) {
        this.cpThree = cpThree;
    }


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
}
