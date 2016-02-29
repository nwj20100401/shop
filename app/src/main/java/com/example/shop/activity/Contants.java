package com.example.shop.activity;

/**
 * Created by Android Studio.
 * 项目名称：shop
 * 类描述：服务器端请求链接
 * 创建人：sony
 * 创建时间：2016/2/29 08:54
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @version V1.0
 */
public class Contants {

    //动态模块id
    public static final String COMPAINGAIN_ID = "compaigin_id";
    //商品
    public static final String WARE = "ware";

    /**
     * 服务端接口
     */
    public static class API {

        //基础接口
        public static final String BASE_URL = "http://112.124.22.238:8080/course_api/";

        //首页
        public static final String CAMPAIGN_HOME = BASE_URL + "campaign/recommend";

        //图片轮播集合
        public static final String BANNER = BASE_URL + "banner/query";

        //热门商品
        public static final String WARES_HOT = BASE_URL + "wares/hot";
        //商品列表
        public static final String WARES_LIST = BASE_URL + "wares/list";
        //商品模块列表
        public static final String WARES_CAMPAIN_LIST = BASE_URL + "wares/campaign/list";
        //详细商品信息
        public static final String WARES_DETAIL = BASE_URL + "wares/detail.html";

        //商品分类列表
        public static final String CATEGORY_LIST = BASE_URL + "category/list";

    }
}
