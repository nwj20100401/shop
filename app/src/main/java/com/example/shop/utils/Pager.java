package com.example.shop.utils;

import android.content.Context;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.shop.bean.Page;
import com.example.shop.http.OkHttpHelper;
import com.example.shop.http.SpotsCallBack;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Android Studio.
 * 项目名称：shop
 * 类描述：分页商品信息实体类
 * 创建人：sony
 * 创建时间：2016/2/27 12:14
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @version V1.0
 */
public class Pager {

    private static Builder builder;

    private OkHttpHelper httpHelper;

    //正常
    private static final int STATE_NORMAL = 0;
    //刷新
    private static final int STATE_REFREH = 1;
    //加载更多
    private static final int STATE_MORE = 2;

    private int state = STATE_NORMAL;

    private Pager() {

        httpHelper = OkHttpHelper.getInstance();
        //初始化商品信息列表
        initRefreshLayout();
    }


    public static Builder newBuilder() {

        builder = new Builder();
        return builder;
    }


    public void request() {
        //请求数据
        requestData();
    }

    public void putParam(String key, Object value) {
        builder.params.put(key, value);
    }


    /**
     * 初始化列表
     */
    private void initRefreshLayout() {

        //可以加载更多
        builder.mRefreshLayout.setLoadMore(builder.canLoadMore);

        //下拉刷新
        builder.mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                builder.mRefreshLayout.setLoadMore(builder.canLoadMore);
                refresh();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {

                if (builder.pageIndex < builder.totalPage) {
                    //加载更多数据
                    loadMore();
                } else {
                    Toast.makeText(builder.mContext, "无更多数据", Toast.LENGTH_LONG).show();
                    //停止加载更多
                    materialRefreshLayout.finishRefreshLoadMore();
                    //设置不能加载更多
                    materialRefreshLayout.setLoadMore(false);
                }
            }
        });
    }

    /**
     * 请求数据
     */
    private void requestData() {

        String url = buildUrl();

        //网络请求
        httpHelper.get(url, new RequestCallBack(builder.mContext));
    }

    /**
     * 显示数据
     */
    private <T> void showData(List<T> datas, int totalPage, int totalCount) {

        if (datas == null || datas.size() <= 0) {
            Toast.makeText(builder.mContext, "加载不到数据", Toast.LENGTH_LONG).show();
            return;
        }

        if (STATE_NORMAL == state) {

            if (builder.onPageListener != null) {
                builder.onPageListener.load(datas, totalPage, totalCount);
            }
        } else if (STATE_REFREH == state) {
            builder.mRefreshLayout.finishRefresh();
            if (builder.onPageListener != null) {
                builder.onPageListener.refresh(datas, totalPage, totalCount);
            }

        } else if (STATE_MORE == state) {

            builder.mRefreshLayout.finishRefreshLoadMore();
            if (builder.onPageListener != null) {
                builder.onPageListener.loadMore(datas, totalPage, totalCount);
            }

        }
    }

    /**
     * 刷新数据
     */
    private void refresh() {

        state = STATE_REFREH;
        builder.pageIndex = 1;
        requestData();
    }

    /**
     * 隐藏数据
     */
    private void loadMore() {

        state = STATE_MORE;
        builder.pageIndex = ++builder.pageIndex;
        requestData();
    }

    /**
     * 构建URL
     *
     * @return
     */
    private String buildUrl() {

        return builder.mUrl + "?" + buildUrlParams();
    }

    private String buildUrlParams() {

        HashMap<String, Object> map = builder.params;

        map.put("curPage", builder.pageIndex);
        map.put("pageSize", builder.pageSize);

        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }


    /**
     * 自定义分页商品信息
     */
    public static class Builder {

        private Context mContext;
        private Type mType;
        private String mUrl;

        //第三方下拉刷新控件
        private MaterialRefreshLayout mRefreshLayout;

        //是否加载更多
        private boolean canLoadMore;


        private int totalPage = 1;
        private int pageIndex = 1;
        private int pageSize = 10;

        private HashMap<String, Object> params = new HashMap<>(5);

        private OnPageListener onPageListener;

        public Builder setUrl(String url) {

            builder.mUrl = url;

            return builder;
        }

        public Builder setPageSize(int pageSize) {
            this.pageSize = pageSize;
            return builder;
        }

        public Builder putParam(String key, Object value) {
            params.put(key, value);
            return builder;
        }

        public Builder setLoadMore(boolean loadMore) {
            this.canLoadMore = loadMore;
            return builder;
        }

        public Builder setRefreshLayout(MaterialRefreshLayout refreshLayout) {

            this.mRefreshLayout = refreshLayout;
            return builder;
        }


        public Builder setOnPageListener(OnPageListener onPageListener) {
            this.onPageListener = onPageListener;
            return builder;
        }

        public Pager build(Context context, Type type) {

            this.mType = type;
            this.mContext = context;

            valid();
            return new Pager();
        }

        private void valid() {

            if (this.mContext == null)
                throw new RuntimeException("content can't be null");

            if (this.mUrl == null || "".equals(this.mUrl))
                throw new RuntimeException("url can't be  null");

            if (this.mRefreshLayout == null)
                throw new RuntimeException("MaterialRefreshLayout can't be  null");
        }
    }


    /**
     * 网络请求回调接口
     *
     * @param <T>
     */
    class RequestCallBack<T> extends SpotsCallBack<Page<T>> {

        public RequestCallBack(Context context) {
            super(context);

            super.mType = builder.mType;
        }

        @Override
        public void onFailure(Request request, Exception e) {

            //关闭对话框
            dismissDialog();
            Toast.makeText(builder.mContext, "请求出错：" + e.getMessage(), Toast.LENGTH_LONG).show();

            if (STATE_REFREH == state) {
                builder.mRefreshLayout.finishRefresh();
            } else if (STATE_MORE == state) {

                builder.mRefreshLayout.finishRefreshLoadMore();
            }
        }

        @Override
        public void onSuccess(Response response, Page<T> page) {


            builder.pageIndex = page.getCurrentPage();
            builder.pageSize = page.getPageSize();
            builder.totalPage = page.getTotalPage();

            showData(page.getList(), page.getTotalPage(), page.getTotalCount());
        }

        @Override
        public void onError(Response response, int code, Exception e) {

            Toast.makeText(builder.mContext, "加载数据失败", Toast.LENGTH_LONG).show();

            if (STATE_REFREH == state) {
                builder.mRefreshLayout.finishRefresh();
            } else if (STATE_MORE == state) {

                builder.mRefreshLayout.finishRefreshLoadMore();
            }
        }
    }

    public interface OnPageListener<T> {


        void load(List<T> datas, int totalPage, int totalCount);

        void refresh(List<T> datas, int totalPage, int totalCount);

        void loadMore(List<T> datas, int totalPage, int totalCount);

    }
}
