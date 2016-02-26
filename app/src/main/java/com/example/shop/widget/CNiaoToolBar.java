package com.example.shop.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shop.R;

/**
 * Created by Android Studio.
 * 项目名称：shop
 * 类描述：自定义搜索框ToolBar
 * 功能描述：得到自己想要样子的ToolBar
 * 创建人：sony
 * 创建时间：2016/2/26 16:54
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @version V1.0
 */
public class CNiaoToolBar extends Toolbar {

    //定义添加布局
    private LayoutInflater mInflater;

    //定义视图
    private View mView;
    //定义textView 未点击之前的提示信息
    private TextView mTextTitle;
    //定义editView 点击之后搜索视图输入框
    private EditText mSearchView;
    //定义button 点击之后搜索视图的搜索按钮
    private Button mRightButton;

    /**
     * 构造函数
     *
     * @param context
     */
    public CNiaoToolBar(Context context) {
        this(context, null);
    }

    /**
     * 加属性的构造函数
     *
     * @param context
     * @param attrs
     */
    public CNiaoToolBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 加属性以及其他的构造函数
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public CNiaoToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //初始化视图
        initView();
        //设置内容的相对位置
        setContentInsetsRelative(10, 10);

        if (attrs != null) {
            final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                    R.styleable.CNiaoToolBar, defStyleAttr, 0);

            final Drawable rightIcon = a.getDrawable(R.styleable.CNiaoToolBar_rightButtonIcon);
            if (rightIcon != null) {

                //setNavigationIcon(navIcon);
                //设置右边按钮的图标样式
                setRightButtonIcon(rightIcon);
            }

            //是否显示搜索视图，默认否
            boolean isShowSearchView = a.getBoolean(R.styleable.CNiaoToolBar_isShowSearchView, false);

            if (isShowSearchView) {
                //显示搜索视图
                showSearchView();
                //提示文本隐藏
                hideTitleView();
            }


            //获取右边按钮的文本
            CharSequence rightButtonText = a.getText(R.styleable.CNiaoToolBar_rightButtonText);
            if (rightButtonText != null) {
                //设置按钮文本
                setRightButtonText(rightButtonText);
            }

            //回收字符串
            a.recycle();
        }
    }

    /**
     * 初始化视图
     */
    private void initView() {

        if (mView == null) {

            mInflater = LayoutInflater.from(getContext());
            //添加toolBar布局
            mView = mInflater.inflate(R.layout.toolbar, null);

            //获取布局上的控件
            mTextTitle = (TextView) mView.findViewById(R.id.toolbar_title);
            mSearchView = (EditText) mView.findViewById(R.id.toolbar_searchview);
            mRightButton = (Button) mView.findViewById(R.id.toolbar_rightButton);

            //布局样式
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);

            //添加视图
            addView(mView, lp);
        }
    }


    /**
     * 获取搜索视图的按钮控件
     *
     * @return
     */
    public Button getRightButton() {

        return this.mRightButton;
    }


    /**
     * 设置按钮图标
     *
     * @param icon
     */
    public void setRightButtonIcon(int icon) {
        //通过id获取资源，设置按钮图标
        setRightButtonIcon(getResources().getDrawable(icon));
    }

    /**
     * 通过id获取资源，设置按钮图标
     *
     * @param icon
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setRightButtonIcon(Drawable icon) {

        if (mRightButton != null) {
            //设置按钮的背景图标
            mRightButton.setBackground(icon);
            //按钮状态为可见
            mRightButton.setVisibility(VISIBLE);
        }
    }

    /**
     * 设置按钮的点击事件
     *
     * @param li
     */
    public void setRightButtonOnClickListener(OnClickListener li) {

        mRightButton.setOnClickListener(li);
    }


    /**
     * 设置按钮的文本
     *
     * @param id
     */
    public void setRightButtonText(int id) {
        //通过id获取资源，设置按钮的文本
        setRightButtonText(getResources().getString(id));
    }

    /**
     * 通过id获取资源，设置按钮的文本
     *
     * @param text
     */
    public void setRightButtonText(CharSequence text) {
        //设置文本
        mRightButton.setText(text);
        //按钮状态为可见
        mRightButton.setVisibility(VISIBLE);
    }


    /**
     * 设置标题
     *
     * @param resId
     */
    @Override
    public void setTitle(int resId) {
        //通过id获取资源，设置搜索视图提示信息
        setTitle(getContext().getText(resId));
    }

    /**
     * 设置标题
     *
     * @param title
     */
    @Override
    public void setTitle(CharSequence title) {

        //初始化视图
        initView();
        if (mTextTitle != null) {
            //设置提示信息
            mTextTitle.setText(title);
            //显示提示信息视图
            showTitleView();
        }
    }


    /**
     * 显示搜索视图
     */
    public void showSearchView() {

        if (mSearchView != null)
            //设置搜索视图输入框状态为可见
            mSearchView.setVisibility(VISIBLE);
    }


    /**
     * 隐藏搜索框提示信息
     */
    public void hideSearchView() {
        if (mSearchView != null)
            //设置提示消息状态为不可见
            mSearchView.setVisibility(GONE);
    }

    /**
     * 显示搜索栏提示信息
     */
    public void showTitleView() {
        if (mTextTitle != null)
            //设置提示信息状态为可见
            mTextTitle.setVisibility(VISIBLE);
    }

    /**
     * 隐藏提示信息
     */
    public void hideTitleView() {
        if (mTextTitle != null)
            //设置提示信息状态为不可见
            mTextTitle.setVisibility(GONE);
    }


//
//    private void ensureRightButtonView() {
//        if (mRightImageButton == null) {
//            mRightImageButton = new ImageButton(getContext(), null,
//                    android.support.v7.appcompat.R.attr.toolbarNavigationButtonStyle);
//            final LayoutParams lp = generateDefaultLayoutParams();
//            lp.gravity = GravityCompat.START | (Gravity.VERTICAL_GRAVITY_MASK);
//            mRightImageButton.setLayoutParams(lp);
//        }
//    }

}
