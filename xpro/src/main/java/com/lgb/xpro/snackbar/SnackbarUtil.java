package com.lgb.xpro.snackbar;

import android.annotation.TargetApi;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.Space;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lgb.xpro.R;
import com.lgb.xpro.logger.Logger;
import com.lgb.xpro.utils.DisplayMetricsUtil;

/**
 * Created by LGB on 2017/07/01.
 */
public class SnackbarUtil {

    //设置Snackbar背景颜色
    private static final int color_info = 0XFF2094F3;
    private static final int color_confirm = 0XFF4CB04E;
    private static final int color_warning = 0XFFFEC005;
    private static final int color_danger = 0XFFF44336;

    //工具类当前持有的Snackbar实例
    private Snackbar mSnackbar = null;

    private SnackbarUtil(){
        throw new RuntimeException("禁止无参创建实例");
    }

    private SnackbarUtil(@NonNull Snackbar snackbar){
        this.mSnackbar = snackbar;
    }

    /**
     * 获取 mSnackbar
     * @return
     */
    public Snackbar getSnackbar() {
        return mSnackbar;
    }

    /**
     * 初始化Snackbar实例
     *      展示时间:HttpSnackbar.LENGTH_SHORT
     * @param view
     * @param message
     * @return
     */
    public static SnackbarUtil Short(View view, String message){
        /*
        <view xmlns:android="http://schemas.android.com/apk/res/android"
          class="android.support.design.widget.HttpSnackbar$SnackbarLayout"
          android:layout_width="match_parent"
          android:layout_height="wrap_content"
          android:layout_gravity="bottom"
          android:theme="@style/ThemeOverlay.AppCompat.Dark"
          style="@style/Widget.Design.HttpSnackbar" />
        <style name="Widget.Design.HttpSnackbar" parent="android:Widget">
            <item name="android:minWidth">@dimen/design_snackbar_min_width</item>
            <item name="android:maxWidth">@dimen/design_snackbar_max_width</item>
            <item name="android:background">@drawable/design_snackbar_background</item>
            <item name="android:paddingLeft">@dimen/design_snackbar_padding_horizontal</item>
            <item name="android:paddingRight">@dimen/design_snackbar_padding_horizontal</item>
            <item name="elevation">@dimen/design_snackbar_elevation</item>
            <item name="maxActionInlineWidth">@dimen/design_snackbar_action_inline_max_width</item>
        </style>
        <shape xmlns:android="http://schemas.android.com/apk/res/android"
            android:shape="rectangle">
            <corners android:radius="@dimen/design_snackbar_background_corner_radius"/>
            <solid android:color="@color/design_snackbar_background_color"/>
        </shape>
        <color name="design_snackbar_background_color">#323232</color>
        */
        return new SnackbarUtil(Snackbar.make(view,message, Snackbar.LENGTH_SHORT)).backColor(0XFF323232);
    }

    /**
     * 初始化Snackbar实例
     *      展示时间:HttpSnackbar.LENGTH_LONG
     * @param view
     * @param message
     * @return
     */
    public static SnackbarUtil Long(View view, String message){
        return new SnackbarUtil(Snackbar.make(view,message, Snackbar.LENGTH_LONG)).backColor(0XFF323232);
    }

    /**
     * 初始化Snackbar实例
     *      展示时间:HttpSnackbar.LENGTH_INDEFINITE
     * @param view
     * @param message
     * @return
     */
    public static SnackbarUtil Indefinite(View view, String message){
        return new SnackbarUtil(Snackbar.make(view,message, Snackbar.LENGTH_INDEFINITE)).backColor(0XFF323232);
    }

    /**
     * 初始化Snackbar实例
     *      展示时间:duration 毫秒
     * @param view
     * @param message
     * @param duration 展示时长(毫秒)
     * @return
     */
    public static SnackbarUtil Custom(View view, String message, int duration){
        return new SnackbarUtil(Snackbar.make(view,message, Snackbar.LENGTH_SHORT).setDuration(duration)).backColor(0XFF323232);
    }

    /**
     * 设置mSnackbar背景色为  color_info
     */
    public SnackbarUtil info(){
        mSnackbar.getView().setBackgroundColor(color_info);
        return new SnackbarUtil(mSnackbar);
    }

    /**
     * 设置mSnackbar背景色为  color_confirm
     */
    public SnackbarUtil confirm(){
        mSnackbar.getView().setBackgroundColor(color_confirm);
        return new SnackbarUtil(mSnackbar);
    }

    /**
     * 设置Snackbar背景色为   color_warning
     */
    public SnackbarUtil warning(){
        mSnackbar.getView().setBackgroundColor(color_warning);
        return new SnackbarUtil(mSnackbar);
    }

    /**
     * 设置Snackbar背景色为   color_warning
     */
    public SnackbarUtil danger(){
        mSnackbar.getView().setBackgroundColor(color_danger);
        return new SnackbarUtil(mSnackbar);
    }

    /**
     * 设置Snackbar背景色
     * @param backgroundColor
     */
    public SnackbarUtil backColor(@ColorInt int backgroundColor){
        mSnackbar.getView().setBackgroundColor(backgroundColor);
        return new SnackbarUtil(mSnackbar);
    }

    /**
     * 设置TextView(@+id/snackbar_text)的文字颜色
     * @param messageColor
     */
    public SnackbarUtil messageColor(@ColorInt int messageColor){
        ((TextView)mSnackbar.getView().findViewById(R.id.snackbar_text)).setTextColor(messageColor);
        return new SnackbarUtil(mSnackbar);
    }

    /**
     * 设置Button(@+id/snackbar_action)的文字颜色
     * @param actionTextColor
     */
    public SnackbarUtil actionColor(@ColorInt int actionTextColor){
        ((Button)mSnackbar.getView().findViewById(R.id.snackbar_action)).setTextColor(actionTextColor);
        return new SnackbarUtil(mSnackbar);
    }

    /**
     * 设置   Snackbar背景色 + TextView(@+id/snackbar_text)的文字颜色 + Button(@+id/snackbar_action)的文字颜色
     * @param backgroundColor
     * @param messageColor
     * @param actionTextColor
     */
    public SnackbarUtil colors(@ColorInt int backgroundColor, @ColorInt int messageColor, @ColorInt int actionTextColor){
        mSnackbar.getView().setBackgroundColor(backgroundColor);
        ((TextView)mSnackbar.getView().findViewById(R.id.snackbar_text)).setTextColor(messageColor);
        ((Button)mSnackbar.getView().findViewById(R.id.snackbar_action)).setTextColor(actionTextColor);
        return new SnackbarUtil(mSnackbar);
    }

    /**
     * 设置Snackbar 背景透明度
     * @param alpha
     * @return
     */
    public SnackbarUtil alpha(float alpha){
        alpha = alpha>=1.0f?1.0f:(alpha<=0.0f?0.0f:alpha);
        mSnackbar.getView().setAlpha(alpha);
        return new SnackbarUtil(mSnackbar);
    }

    /**
     * 设置Snackbar显示的位置
     * @param gravity
     */
    public SnackbarUtil gravityFrameLayout(int gravity){
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mSnackbar.getView().getLayoutParams().width,mSnackbar.getView().getLayoutParams().height);
        params.gravity = gravity;
        mSnackbar.getView().setLayoutParams(params);
        return new SnackbarUtil(mSnackbar);
    }

    /**
     * 设置Snackbar显示的位置,当Snackbar和CoordinatorLayout组合使用的时候
     * @param gravity
     */
    public SnackbarUtil gravityCoordinatorLayout(int gravity){
        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(mSnackbar.getView().getLayoutParams().width,mSnackbar.getView().getLayoutParams().height);
        params.gravity = gravity;
        mSnackbar.getView().setLayoutParams(params);
        return new SnackbarUtil(mSnackbar);
    }

    /**
     * 设置Snackbar显示的位置，最底部为参照
     * @param left
     * @param top
     * @param right
     * @param buttom
     * @return
     */
    public SnackbarUtil gravityMarginsLayout(int left,int top,int right,int buttom){
        ViewGroup.LayoutParams params = mSnackbar.getView().getLayoutParams();
        ((ViewGroup.MarginLayoutParams) params).setMargins(left,top,right,buttom);
        mSnackbar.getView().setLayoutParams(params);
        return new SnackbarUtil(mSnackbar);
    }

    /**
     * 设置按钮文字内容 及 点击监听
     *      {@link Snackbar#setAction(CharSequence, View.OnClickListener)}
     * @param resId
     * @param listener
     * @return
     */
    public SnackbarUtil setAction(@StringRes int resId, View.OnClickListener listener){
        return setAction(getSnackbar().getView().getResources().getText(resId), listener);
    }

    /**
     * 设置按钮文字内容 及 点击监听
     *      {@link Snackbar#setAction(CharSequence, View.OnClickListener)}
     * @param text
     * @param listener
     * @return
     */
    public SnackbarUtil setAction(CharSequence text, View.OnClickListener listener){
        mSnackbar.setAction(text,listener);
        return new SnackbarUtil(mSnackbar);
    }

    /**
     * 设置 mSnackbar 展示完成 及 隐藏完成 的监听
     * @param setCallback
     * @return
     */
    public SnackbarUtil setCallback(Snackbar.Callback setCallback){
        mSnackbar.setCallback(setCallback);
        return new SnackbarUtil(mSnackbar);
    }

    /**
     * 设置TextView(@+id/snackbar_text)左右两侧的图片
     * @param leftDrawable
     * @param rightDrawable
     * @return
     */
    public SnackbarUtil leftAndRightDrawable(@Nullable @DrawableRes Integer leftDrawable, @Nullable @DrawableRes Integer rightDrawable){
        Drawable drawableLeft = null;
        Drawable drawableRight = null;
        if(leftDrawable!=null){
            try {
                drawableLeft = getSnackbar().getView().getResources().getDrawable(leftDrawable.intValue());
            }catch (Exception e){
                Logger.e("getSnackbar().getView().getResources().getDrawable(leftDrawable.intValue())");
            }
        }
        if(rightDrawable!=null){
            try {
                drawableRight = getSnackbar().getView().getResources().getDrawable(rightDrawable.intValue());
            }catch (Exception e){
                Logger.e("getSnackbar().getView().getResources().getDrawable(rightDrawable.intValue())");
            }
        }
        return leftAndRightDrawable(drawableLeft,drawableRight);
    }

    /**
     * 设置TextView(@+id/snackbar_text)左右两侧的图片
     * @param leftDrawable
     * @param rightDrawable
     * @return
     */
    public SnackbarUtil leftAndRightDrawable(@Nullable Drawable leftDrawable, @Nullable Drawable rightDrawable){
        TextView message = (TextView) mSnackbar.getView().findViewById(R.id.snackbar_text);
        LinearLayout.LayoutParams paramsMessage = (LinearLayout.LayoutParams) message.getLayoutParams();
        paramsMessage = new LinearLayout.LayoutParams(paramsMessage.width, paramsMessage.height,0.0f);
        message.setLayoutParams(paramsMessage);
        message.setCompoundDrawablePadding(message.getPaddingLeft());
        int textSize = (int) message.getTextSize();
        Logger.e("textSize:"+textSize);
        if(leftDrawable!=null){
            leftDrawable.setBounds(0,0,textSize,textSize);
        }
        if(rightDrawable!=null){
            rightDrawable.setBounds(0,0,textSize,textSize);
        }
        message.setCompoundDrawables(leftDrawable,null,rightDrawable,null);
        LinearLayout.LayoutParams paramsSpace = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,1.0f);
        ((Snackbar.SnackbarLayout)mSnackbar.getView()).addView(new Space(mSnackbar.getView().getContext()),1,paramsSpace);
        return new SnackbarUtil(mSnackbar);
    }

    /**
     * 设置TextView(@+id/snackbar_text)中文字的对齐方式 居中
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public SnackbarUtil messageCenter(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            TextView message = (TextView) mSnackbar.getView().findViewById(R.id.snackbar_text);
            //View.setTextAlignment需要SDK>=17
            message.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
            message.setGravity(Gravity.CENTER);
        }
        return new SnackbarUtil(mSnackbar);
    }

    /**
     * 设置TextView(@+id/snackbar_text)中文字的对齐方式 居右
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public SnackbarUtil messageRight(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            TextView message = (TextView) mSnackbar.getView().findViewById(R.id.snackbar_text);
            //View.setTextAlignment需要SDK>=17
            message.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
            message.setGravity(Gravity.CENTER_VERTICAL| Gravity.RIGHT);
        }
        return new SnackbarUtil(mSnackbar);
    }

    /**
     * 向Snackbar布局中添加View(Google不建议,复杂的布局应该使用DialogFragment进行展示)
     * @param layoutId  要添加的View的布局文件ID
     * @param index
     * @return
     */
    public SnackbarUtil addView(int layoutId, int index) {
        //加载布局文件新建View
        View addView = LayoutInflater.from(mSnackbar.getView().getContext()).inflate(layoutId,null);
        return addView(addView,index);
    }

    /**
     * 向Snackbar布局中添加View(Google不建议,复杂的布局应该使用DialogFragment进行展示)
     * @param addView
     * @param index
     * @return
     */
    public SnackbarUtil addView(View addView, int index) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);//设置新建布局参数
        //设置新建View在Snackbar内垂直居中显示
        params.gravity= Gravity.CENTER_VERTICAL;
        addView.setLayoutParams(params);
        ((Snackbar.SnackbarLayout)mSnackbar.getView()).addView(addView,index);
        return new SnackbarUtil(mSnackbar);
    }

    /**
     * 设置Snackbar布局的外边距
     *      注:经试验发现,调用margins后再调用 gravityFrameLayout,则margins无效.
     *          为保证margins有效,应该先调用 gravityFrameLayout,在 show() 之前调用 margins
     * @param margin
     * @return
     */
    public SnackbarUtil margins(int margin){
        return margins(margin,margin,margin,margin);
    }

    /**
     * 设置Snackbar布局的外边距
     *      注:经试验发现,调用margins后再调用 gravityFrameLayout,则margins无效.
     *         为保证margins有效,应该先调用 gravityFrameLayout,在 show() 之前调用 margins
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @return
     */
    public SnackbarUtil margins(int left, int top, int right, int bottom){
        ViewGroup.LayoutParams params = mSnackbar.getView().getLayoutParams();
        ((ViewGroup.MarginLayoutParams) params).setMargins(left,top,right,bottom);
        mSnackbar.getView().setLayoutParams(params);
        return new SnackbarUtil(mSnackbar);
    }

    /**
     * 经试验发现:
     *      执行过{@link SnackbarUtil#backColor(int)}后:background instanceof ColorDrawable
     *      未执行过{@link SnackbarUtil#backColor(int)}:background instanceof GradientDrawable
     * @return
     */
    /*
    public SnackbarUtil radius(){
        Drawable background = mSnackbar.getView().getBackground();
        if(background instanceof GradientDrawable){
            Logger.e("radius():GradientDrawable");
        }
        if(background instanceof ColorDrawable){
            Logger.e("radius():ColorDrawable");
        }
        if(background instanceof StateListDrawable){
            Logger.e("radius():StateListDrawable");
        }
        Logger.e("radius()background:"+background.getClass().getSimpleName());
        return new SnackbarUtil(mSnackbar);
    }
    */

    /**
     * 通过SnackBar现在的背景,获取其设置圆角值时候所需的GradientDrawable实例
     * @param backgroundOri
     * @return
     */
    private GradientDrawable getRadiusDrawable(Drawable backgroundOri){
        GradientDrawable background = null;
        if(backgroundOri instanceof GradientDrawable){
            background = (GradientDrawable) backgroundOri;
        }else if(backgroundOri instanceof ColorDrawable){
            int backgroundColor = ((ColorDrawable)backgroundOri).getColor();
            background = new GradientDrawable();
            background.setColor(backgroundColor);
        }else {
        }
        return background;
    }
    /**
     * 设置Snackbar布局的圆角半径值
     * @param radius    圆角半径
     * @return
     */
    public SnackbarUtil radius(float radius){
        //将要设置给mSnackbar的背景
        GradientDrawable background = getRadiusDrawable(mSnackbar.getView().getBackground());
        if(background != null){
            radius = radius<=0?12:radius;
            background.setCornerRadius(radius);
            mSnackbar.getView().setBackgroundDrawable(background);
        }
        return new SnackbarUtil(mSnackbar);
    }

    /**
     * 设置Snackbar布局的圆角半径值及边框颜色及边框宽度
     * @param radius
     * @param strokeWidth
     * @param strokeColor
     * @return
     */
    public SnackbarUtil radius(int radius, int strokeWidth, @ColorInt int strokeColor){
        //将要设置给mSnackbar的背景
        GradientDrawable background = getRadiusDrawable(mSnackbar.getView().getBackground());
        if(background != null){
            radius = radius<=0?12:radius;
            strokeWidth = strokeWidth<=0?1:(strokeWidth>=mSnackbar.getView().findViewById(R.id.snackbar_text).getPaddingTop()?2:strokeWidth);
            background.setCornerRadius(radius);
            background.setStroke(strokeWidth,strokeColor);
            mSnackbar.getView().setBackgroundDrawable(background);
        }
        return new SnackbarUtil(mSnackbar);
    }

    /**
     * 计算单行的Snackbar的高度值(单位 pix)
     * @return
     */
    private int calculateSnackBarHeight(){
        /*
        <TextView
                android:id="@+id/snackbar_text"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:paddingTop="@dimen/design_snackbar_padding_vertical"
                android:paddingBottom="@dimen/design_snackbar_padding_vertical"
                android:paddingLeft="@dimen/design_snackbar_padding_horizontal"
                android:paddingRight="@dimen/design_snackbar_padding_horizontal"
                android:textAppearance="@style/TextAppearance.Design.HttpSnackbar.Message"
                android:maxLines="@integer/design_snackbar_text_max_lines"
                android:layout_gravity="center_vertical|left|start"
                android:ellipsize="end"
                android:textAlignment="viewStart"/>
        */
        //文字高度+paddingTop+paddingBottom : 14sp + 14dp*2
        int SnackbarHeight = DisplayMetricsUtil.dp2px(mSnackbar.getView().getContext(),28) + DisplayMetricsUtil.sp2px(mSnackbar.getView().getContext(),14);
        Logger.e("直接获取MessageView高度:"+mSnackbar.getView().findViewById(R.id.snackbar_text).getHeight());
        return SnackbarHeight;
    }

    /**
     * 设置Snackbar显示在指定View的上方
     *      注:暂时仅支持单行的Snackbar,因为{@link SnackbarUtil#calculateSnackBarHeight()}暂时仅支持单行Snackbar的高度计算
     * @param targetView        指定View
     * @param contentViewTop    Activity中的View布局区域 距离屏幕顶端的距离
     * @param marginLeft        左边距
     * @param marginRight       右边距
     * @return
     */
    public SnackbarUtil above(View targetView, int contentViewTop, int marginLeft, int marginRight){
        marginLeft = marginLeft<=0?0:marginLeft;
        marginRight = marginRight<=0?0:marginRight;
        int[] locations = new int[2];
        targetView.getLocationOnScreen(locations);
        Logger.e("距离屏幕左侧:"+locations[0]+"==距离屏幕顶部:"+locations[1]);
        int snackbarHeight = calculateSnackBarHeight();
        Logger.e("Snackbar高度:"+snackbarHeight);
        //必须保证指定View的顶部可见 且 单行Snackbar可以完整的展示
        if(locations[1] >= contentViewTop+snackbarHeight){
            gravityFrameLayout(Gravity.BOTTOM);
            ViewGroup.LayoutParams params = mSnackbar.getView().getLayoutParams();
            ((ViewGroup.MarginLayoutParams) params).setMargins(marginLeft,0,marginRight,mSnackbar.getView().getResources().getDisplayMetrics().heightPixels-locations[1]);
            mSnackbar.getView().setLayoutParams(params);
        }
        return new SnackbarUtil(mSnackbar);
    }

    /**
     * 设置Snackbar显示在指定View的下方
     *      注:暂时仅支持单行的Snackbar,因为{@link SnackbarUtil#calculateSnackBarHeight()}暂时仅支持单行Snackbar的高度计算
     * @param targetView        指定View
     * @param contentViewTop    Activity中的View布局区域 距离屏幕顶端的距离
     * @param marginLeft        左边距
     * @param marginRight       右边距
     * @return
     */
    public SnackbarUtil bellow(View targetView, int contentViewTop, int marginLeft, int marginRight){
        marginLeft = marginLeft<=0?0:marginLeft;
        marginRight = marginRight<=0?0:marginRight;
        int[] locations = new int[2];
        targetView.getLocationOnScreen(locations);
        int snackbarHeight = calculateSnackBarHeight();
        int screenHeight = DisplayMetricsUtil.getScreenHeight(mSnackbar.getView().getContext());
        //必须保证指定View的底部可见 且 单行Snackbar可以完整的展示
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            //为什么要'+2'? 因为在Android L(Build.VERSION_CODES.LOLLIPOP)以上,例如Button会有一定的'阴影(shadow)',阴影的大小由'高度(elevation)'决定.
            //为了在Android L以上的系统中展示的Snackbar不要覆盖targetView的阴影部分太大比例,所以人为减小2px的layout_marginBottom属性.
            if(locations[1]+targetView.getHeight()>=contentViewTop&&locations[1]+targetView.getHeight()+snackbarHeight+2<=screenHeight){
                gravityFrameLayout(Gravity.BOTTOM);
                ViewGroup.LayoutParams params = mSnackbar.getView().getLayoutParams();
                ((ViewGroup.MarginLayoutParams) params).setMargins(marginLeft,0,marginRight,screenHeight - (locations[1]+targetView.getHeight()+snackbarHeight+2));
                mSnackbar.getView().setLayoutParams(params);
            }
        }else {
            if(locations[1]+targetView.getHeight()>=contentViewTop&&locations[1]+targetView.getHeight()+snackbarHeight<=screenHeight){
                gravityFrameLayout(Gravity.BOTTOM);
                ViewGroup.LayoutParams params = mSnackbar.getView().getLayoutParams();
                ((ViewGroup.MarginLayoutParams) params).setMargins(marginLeft,0,marginRight,screenHeight - (locations[1]+targetView.getHeight()+snackbarHeight));
                mSnackbar.getView().setLayoutParams(params);
            }
        }
        return new SnackbarUtil(mSnackbar);
    }
    
    /**
     * 显示 mSnackbar
     */
    public void show(){
        if(mSnackbar!=null){
            mSnackbar.show();
        }
    }
    
}
