package com.example.shop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.shop.R;
import com.example.shop.activity.Contants;
import com.example.shop.activity.WareListActivity;
import com.example.shop.adapter.HomeCatgoryAdapter;
import com.example.shop.adapter.decoration.CardViewtemDecortion;
import com.example.shop.bean.Banner;
import com.example.shop.bean.Campaign;
import com.example.shop.bean.HomeCampaign;
import com.example.shop.http.BaseCallback;
import com.example.shop.http.OkHttpHelper;
import com.example.shop.http.SpotsCallBack;
import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.List;

/**
 * Created by Android Studio.
 * 项目名称：shop
 * 类描述：主页fragment
 * 创建人：sony
 * 创建时间：2016/2/26 16:04
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @version V1.0
 */
public class HomeFragment extends BaseFragment {

    private static final String TAG = "HomeFragment";

    //AndroidImageSlider用于实现图片轮播效果
    @ViewInject(R.id.slider)
    private SliderLayout mSliderLayout;

    //直接把ViewHolder的实现封装起来，自动回收复用每一个item，用于替换listView
    @ViewInject(R.id.recyclerview)
    private RecyclerView mRecyclerView;

    //定义主页分类适配器
    private HomeCatgoryAdapter mAdatper;

    //定义gson
    private Gson mGson = new Gson();

    //定义图片轮播的图片列表
    private List<Banner> mBanner;

    //获取网络请求实例
    private OkHttpHelper httpHelper = OkHttpHelper.getInstance();


    /**
     * 创建视图
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //返回主页布局
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void init() {

        requestImages();

        initRecyclerView();
    }


    /**
     * 获取图片
     */
    private void requestImages() {

        String url = "http://112.124.22.238:8081/course_api/banner/query?type=1";

        //网络请求
        httpHelper.get(url, new SpotsCallBack<List<Banner>>(getContext()) {

            @Override
            public void onSuccess(Response response, List<Banner> banners) {
                //获取的图片列表
                mBanner = banners;
                //开源项目，实现图片轮播
                initSlider();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }


    /**
     * 初始化列表
     */
    private void initRecyclerView() {

        httpHelper.get(Contants.API.CAMPAIGN_HOME, new BaseCallback<List<HomeCampaign>>() {
            @Override
            public void onBeforeRequest(Request request) {

            }

            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onSuccess(Response response, List<HomeCampaign> homeCampaigns) {

                //初始化动态模块数据
                initData(homeCampaigns);
            }


            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    /**
     * 初始化动态模块数据
     *
     * @param homeCampaigns
     */
    private void initData(List<HomeCampaign> homeCampaigns) {

        //初始化分类列表
        mAdatper = new HomeCatgoryAdapter(homeCampaigns, getActivity());

        //注册动态模块的监听事件
        mAdatper.setOnCampaignClickListener(new HomeCatgoryAdapter.OnCampaignClickListener() {
            @Override
            public void onClick(View view, Campaign campaign) {

                //跳转到商品列表activity
                Intent intent = new Intent(getActivity(), WareListActivity.class);
                //传递点击的动态模块
                intent.putExtra(Contants.COMPAINGAIN_ID, campaign.getId());
                //启动activity
                startActivity(intent);
            }
        });

        mRecyclerView.setAdapter(mAdatper);

        mRecyclerView.addItemDecoration(new CardViewtemDecortion());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    }


    /**
     * 初始化slideLayout
     */
    private void initSlider() {

        if (mBanner != null) {

            for (Banner banner : mBanner) {
                TextSliderView textSliderView = new TextSliderView(this.getActivity());
                textSliderView.image(banner.getImgUrl());
                textSliderView.description(banner.getName());
                textSliderView.setScaleType(BaseSliderView.ScaleType.Fit);
                mSliderLayout.addSlider(textSliderView);
            }
        }

        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSliderLayout.setCustomAnimation(new DescriptionAnimation());
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.RotateUp);
        mSliderLayout.setDuration(3000);
    }

    /**
     * 退出主页Fragment，释放内存资源
     */
    @Override
    public void onDestroy() {
        super.onDestroy();

        mSliderLayout.stopAutoCycle();
    }
}
