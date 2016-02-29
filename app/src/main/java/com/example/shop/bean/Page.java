package com.example.shop.bean;

import java.util.List;

/**
 * Created by Android Studio.
 * 项目名称：shop
 * 类描述：商品分页信息实体类
 * 创建人：sony
 * 创建时间：2016/2/29 10:10
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @version V1.0
 */
public class Page<T> {

    private int currentPage;
    private int pageSize;
    private int totalPage;
    private int totalCount;

    private List<T> list;


    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
