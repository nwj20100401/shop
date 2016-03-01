package com.example.shop.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.shop.R;
import com.example.shop.bean.Wares;
import com.example.shop.utils.CartProvider;
import com.example.shop.utils.ToastUtils;
import com.example.shop.widget.CNiaoToolBar;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.Serializable;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import dmax.dialog.SpotsDialog;

/**
 * Created by Android Studio.
 * 项目名称：shop
 * 类描述：详细商品信息activity
 * 创建人：sony
 * 创建时间：2016/2/2908:58
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @version V1.0
 */
public class WareDetailActivity extends AppCompatActivity implements View.OnClickListener {

    @ViewInject(R.id.webView)
    private WebView mWebView;

    @ViewInject(R.id.toolbar)
    private CNiaoToolBar mToolBar;

    //定义商品信息
    private Wares mWare;

    private WebAppInterface mAppInterfce;

    private CartProvider cartProvider;

    //自定义进度对话框
    private SpotsDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ware_detail);
        ViewUtils.inject(this);

        //获取商品信息序列化
        Serializable serializable = getIntent().getSerializableExtra(Contants.WARE);
        if (serializable == null) {
            this.finish();
        }

        //实例化进度对话框
        mDialog = new SpotsDialog(this, "loading....");
        //显示对话框
        mDialog.show();

        //初始化商品信息
        mWare = (Wares) serializable;
        //初始化购物车信息数据提供者
        cartProvider = new CartProvider(this);

        //初始化自定义搜索框
        initToolBar();
        //初始化webView控件
        initWebView();
    }

    /**
     * 初始化自定义搜索框,分享商品信息到朋友圈  空间等
     */
    private void initToolBar() {

        //添加导航的监听事件
        mToolBar.setNavigationOnClickListener(this);
        mToolBar.setRightButtonText("分享");

        mToolBar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //分享商品信息
                showShare();
            }
        });
    }

    /**
     * 初始化webView控件
     */
    private void initWebView() {

        WebSettings settings = mWebView.getSettings();

        //webView的相关设置
        settings.setJavaScriptEnabled(true);
        settings.setBlockNetworkImage(false);
        settings.setAppCacheEnabled(true);

        //加载url
        mWebView.loadUrl(Contants.API.WARES_DETAIL);

        //初始化应用接口
        mAppInterfce = new WebAppInterface(this);
        //添加js接口
        mWebView.addJavascriptInterface(mAppInterfce, "appInterface");
        //网页在应用中显示
        mWebView.setWebViewClient(new WC());
    }

    /**
     * 分享商品信息
     */
    private void showShare() {

        ShareSDK.initSDK(this);

        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.share));

        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://www.cniao5.com");

        // text是分享文本，所有平台都需要这个字段
        oks.setText(mWare.getName());

        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        oks.setImageUrl(mWare.getImgUrl());

        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://www.cniao5.com");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(mWare.getName());

        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));

        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://www.cniao5.com");

        // 启动分享GUI
        oks.show(this);
    }

    /**
     * 销毁activity
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        ShareSDK.stopSDK(this);
    }

    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        this.finish();
    }


    /**
     * webView客户端
     */
    class WC extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }

            mAppInterfce.showDetail();
        }
    }


    /**
     * 商品详细信息应用接口
     */
    class WebAppInterface {

        private Context mContext;

        public WebAppInterface(Context context) {
            mContext = context;
        }

        /**
         * 显示商品的详细信息
         */
        @JavascriptInterface
        public void showDetail() {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //加载URL
                    mWebView.loadUrl("javascript:showDetail(" + mWare.getId() + ")");
                }
            });
        }


        /**
         * 添加到购物车
         *
         * @param id
         */
        @JavascriptInterface
        public void buy(long id) {

            cartProvider.put(mWare);
            ToastUtils.show(mContext, "已添加到购物车");

        }

        /**
         * 将商品收藏
         *
         * @param id
         */
        @JavascriptInterface
        public void addFavorites(long id) {

        }
    }
}
