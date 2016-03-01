package com.example.shop.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.shop.R;
import com.example.shop.bean.Tab;
import com.example.shop.fragment.CartFragment;
import com.example.shop.fragment.CategoryFragment;
import com.example.shop.fragment.HomeFragment;
import com.example.shop.fragment.HotFragment;
import com.example.shop.fragment.MineFragment;
import com.example.shop.widget.FragmentTabHost;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * 项目名称：shop
 * 类描述：主页框架activity，动态切换fragment
 * 创建人：sony
 * 创建时间：2016/2/26 15:23
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @version V1.0
 */
public class MainActivity extends AppCompatActivity {
    // 添加布局
    private LayoutInflater mInflater;
    // 定义fragmentTabhost对象
    private FragmentTabHost mTabhost;

    private CartFragment cartFragment;
    // 底部选项卡为5个
    private List<Tab> mTabs = new ArrayList<>(5);

    /**
     * 创建MainActivity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置MainActivity的布局文件
        setContentView(R.layout.activity_main);

        //初始化选项卡
        initTab();
    }


    /**
     * 初始化底部选项卡
     */
    private void initTab() {

        Tab tab_home = new Tab(HomeFragment.class, R.string.home, R.drawable.selector_icon_home);
        Tab tab_hot = new Tab(HotFragment.class, R.string.hot, R.drawable.selector_icon_hot);
        Tab tab_category = new Tab(CategoryFragment.class, R.string.catagory, R.drawable.selector_icon_category);
        Tab tab_cart = new Tab(CartFragment.class, R.string.cart, R.drawable.selector_icon_cart);
        Tab tab_mine = new Tab(MineFragment.class, R.string.mine, R.drawable.selector_icon_mine);

        mTabs.add(tab_home);
        mTabs.add(tab_hot);
        mTabs.add(tab_category);
        mTabs.add(tab_cart);
        mTabs.add(tab_mine);

        mInflater = LayoutInflater.from(this);
        mTabhost = (FragmentTabHost) this.findViewById(android.R.id.tabhost);

        mTabhost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        for (Tab tab : mTabs) {

            TabHost.TabSpec tabSpec = mTabhost.newTabSpec(getString(tab.getTitle()));

            //设置选项卡空间
            tabSpec.setIndicator(buildIndicator(tab));

            mTabhost.addTab(tabSpec, tab.getFragment(), null);
        }

        mTabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

                if (tabId == getString(R.string.cart)) {

                    refData();
                }
            }
        });

        mTabhost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        //默认选项卡时主页
        mTabhost.setCurrentTab(0);
    }


    /**
     * 刷新数据
     */
    private void refData() {

        if (cartFragment == null) {

            Fragment fragment = getSupportFragmentManager().findFragmentByTag(getString(R.string.cart));
            if (fragment != null) {
                cartFragment = (CartFragment) fragment;
                cartFragment.refData();
            }
        } else {
            cartFragment.refData();
        }
    }


    /**
     * 创建选项卡
     *
     * @param tab
     * @return
     */
    private View buildIndicator(Tab tab) {

        //添加布局
        View view = mInflater.inflate(R.layout.tab_indicator, null);
        //获取控件
        ImageView img = (ImageView) view.findViewById(R.id.icon_tab);
        TextView text = (TextView) view.findViewById(R.id.txt_indicator);

        //设置选项卡图片
        img.setBackgroundResource(tab.getIcon());
        //设置选项卡文字
        text.setText(tab.getTitle());

        return view;
    }
}
