package com.example.shop.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.TintTypedArray;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shop.R;


/**
 * Created by Android Studio.
 * 项目名称：shop
 * 类描述：自定义控件
 * 功能描述:添加减少商品的数量
 * 创建人：sony
 * 创建时间：2016/2/27 11:26
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @version V1.0
 */
public class NumberAddSubView extends LinearLayout implements View.OnClickListener {

    public static final String TAG = "NumberAddSubView";

    //默认的最大值
    public static final int DEFUALT_MAX = 1000;

    //数字显示
    private TextView mEtxtNum;
    //添加按钮
    private Button mBtnAdd;
    //减少按钮
    private Button mBtnSub;

    //按钮的点击监听事件
    private OnButtonClickListener onButtonClickListener;

    //定义添加布局对象
    private LayoutInflater mInflater;

    //定义动态显示的值
    private int value;
    //最小商品数量
    private int minValue;
    //最大商品数量
    private int maxValue = DEFUALT_MAX;


    /**
     * 构造函数
     *
     * @param context
     */
    public NumberAddSubView(Context context) {
        this(context, null);
    }

    /**
     * 构造函数
     *
     * @param context
     * @param attrs
     */
    public NumberAddSubView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 构造函数
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public NumberAddSubView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //初始化添加布局选项
        mInflater = LayoutInflater.from(context);

        //初始化视图
        initView();

        if (attrs != null) {

            //属性数组
            final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                    R.styleable.NumberAddSubView, defStyleAttr, 0);

            //从typeArray获取相应值，第二个参数为默认值，如第一个参数在atts.xml中没有定义，返回第二个参数值
            int val = a.getInt(R.styleable.NumberAddSubView_value, 0);
            setValue(val);

            int maxVal = a.getInt(R.styleable.NumberAddSubView_maxValue, 0);
            if (maxVal != 0)
                setMaxValue(maxVal);

            int minVal = a.getInt(R.styleable.NumberAddSubView_minValue, 0);
            setMinValue(minVal);

            Drawable etBackground = a.getDrawable(R.styleable.NumberAddSubView_editBackground);
            if (etBackground != null) {
                setEditTextBackground(etBackground);
            }

            Drawable buttonAddBackground = a.getDrawable(R.styleable.NumberAddSubView_buttonAddBackgroud);
            if (buttonAddBackground != null) {
                setButtonAddBackgroud(buttonAddBackground);
            }

            Drawable buttonSubBackground = a.getDrawable(R.styleable.NumberAddSubView_buttonSubBackgroud);
            if (buttonSubBackground != null) {
                setButtonSubBackgroud(buttonSubBackground);
            }

            a.recycle();
        }
    }


    /**
     * 初始化视图
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void initView() {

        /**
         * 添加布局
         */
        View view = mInflater.inflate(R.layout.widet_num_add_sub, this, true);

        //获取商品数量显示控件
        mEtxtNum = (TextView) view.findViewById(R.id.etxt_num);
        //输入类型为没有指定明确的类型的特殊内容类型
        mEtxtNum.setInputType(InputType.TYPE_NULL);
        //设置文本为不可编辑
        mEtxtNum.setKeyListener(null);
        //当设置了不可编辑后，显示时仍有编辑框或者底部横线，影响美观
        mEtxtNum.setBackground(null);

        //获取添加减少按钮控件
        mBtnAdd = (Button) view.findViewById(R.id.btn_add);
        mBtnSub = (Button) view.findViewById(R.id.btn_sub);

        //注册点击事件
        mBtnAdd.setOnClickListener(this);
        mBtnSub.setOnClickListener(this);
    }


    /**
     * 按钮的点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_add) {

            //添加商品数量
            numAdd();

            if (onButtonClickListener != null) {
                onButtonClickListener.onButtonAddClick(v, this.value);
            }
        } else if (v.getId() == R.id.btn_sub) {
            //减少商品数量
            numSub();
            if (onButtonClickListener != null) {
                onButtonClickListener.onButtonSubClick(v, this.value);
            }
        }
    }


    /**
     * 添加商品数量
     */
    private void numAdd() {

        //获取原先商品的数量
        getValue();

        //添加商品数量
        if (this.value <= maxValue) {
            this.value = +this.value + 1;
        }
        //设置商品的数量
        mEtxtNum.setText(value + "");
    }

    /**
     * 减少商品数量
     */
    private void numSub() {

        //获取原先商品的数量
        getValue();

        //减少商品数量
        if (this.value > minValue) {
            this.value = this.value - 1;
        }

        //设置商品的数量
        mEtxtNum.setText(value + "");
    }


    /**
     * 获取原先商品的数量
     *
     * @return
     */
    public int getValue() {

        //获取数量数量
        String value = mEtxtNum.getText().toString();

        if (value != null && !"".equals(value)) {
            this.value = Integer.parseInt(value);
        }
        return this.value;
    }

    /**
     * 设置商品的数量
     *
     * @param value
     */
    public void setValue(int value) {
        mEtxtNum.setText(value + "");
        this.value = value;
    }

    /**
     * 设置商品的数量的最大值
     *
     * @param maxValue
     */
    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * 设置商品的数量的最小值
     *
     * @param minValue
     */
    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }


    /**
     * 设置文本框的背景
     *
     * @param drawable
     */
    public void setEditTextBackground(Drawable drawable) {

        mEtxtNum.setBackgroundDrawable(drawable);
    }


    /**
     * 设置文本框的背景
     *
     * @param drawableId
     */
    public void setEditTextBackground(int drawableId) {

        setEditTextBackground(getResources().getDrawable(drawableId));

    }


    /**
     * 设置按钮的背景
     *
     * @param backgroud
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setButtonAddBackgroud(Drawable backgroud) {
        this.mBtnAdd.setBackground(backgroud);
    }


    /**
     * 设置按钮的背景
     *
     * @param backgroud
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setButtonSubBackgroud(Drawable backgroud) {
        this.mBtnSub.setBackground(backgroud);
    }

    /**
     * 设置按钮的监听
     *
     * @param onButtonClickListener
     */
    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }


    /**
     * 按钮的监听事件接口
     */
    public interface OnButtonClickListener {

        public void onButtonAddClick(View view, int value);

        public void onButtonSubClick(View view, int value);
    }
}
