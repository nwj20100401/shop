package com.example.shop.bean;

/**
 * Created by Android Studio.
 * 项目名称：shop
 * 类描述：选项卡信息实体
 * 创建人：sony
 * 创建时间：2016/2/29 10:09
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @version V1.0
 */
public class Tab {

    //选项卡的标题
    private int title;
    //选项卡的标题
    private int icon;
    //Fragment
    private Class fragment;

    public Tab(Class fragment, int title, int icon) {
        this.title = title;
        this.icon = icon;
        this.fragment = fragment;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Class getFragment() {
        return fragment;
    }

    public void setFragment(Class fragment) {
        this.fragment = fragment;
    }
}
