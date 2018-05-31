package com.gamerole.common.base.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gamerole.common.R;
import com.gamerole.common.base.AppManager;
import com.gamerole.common.base.presenter.BasePresenter;
import com.gamerole.common.util.ClickUtil;
import com.gamerole.common.util.ToastUtil;

import java.util.Date;
import java.util.Stack;


/**
 * Created by Lee on 2018/1/25 0025.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements IBaseView {
    public T mPresenter;
    public BaseActivity mActivity;
    private long oldTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTheme();
        setContentView(getLayoutResId());
        mActivity = this;
        mPresenter = getPresenter();
        mPresenter.attachView(this, this);
        onViewBinding();
        initView();
        initData();
    }

    protected void initData() {
    }

    protected void initView() {
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
    }

    private void initTheme() {

    }

    /**
     * 初始化布局ID
     *
     * @return
     */
    protected abstract int getLayoutResId();

    /**
     * 初始化当前View 的 presenter
     *
     * @return
     */
    public abstract T getPresenter();

    /**
     * 初始化
     */
    protected void onViewBinding() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.cancelRequest();
        mPresenter.detachView();

    }

    @Override
    public void onBackPressed() {
        Stack<Activity> allActivities = AppManager.getAppManager().getAllActivities();
        if (allActivities.size() <= 1) {
            long nowTime = new Date().getTime();
            if (nowTime - oldTime <= 2000) {
                AppManager.getAppManager().AppExit(this);
            } else {
                oldTime = nowTime;
                ToastUtil.showShort("再次点击退出程序");
            }
        } else {
            super.onBackPressed();
        }
    }

    /**
     * [页面跳转]
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putInt("requestCode", requestCode);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    /**
     * @param visible 返回键是否显示 back
     * @param title   标题 tv_title
     * @param resBg   整体背景色 rl_title
     */
    public void initTopTitleBar(int visible, String title, int resBg) {
        RelativeLayout rl_title = findViewById(R.id.common_rl_title);
        rl_title.setBackgroundColor(resBg);
        ImageView back = findViewById(R.id.common_back);
        back.setVisibility(visible);
        if (visible == View.VISIBLE) {
            ClickUtil.click(back, this::onBackPressed);
        }
        TextView tvTitle = findViewById(R.id.common_tv_title);
        tvTitle.setText(title);
    }

    public void initTopTitleBar(int visible, CharSequence title) {
        ImageView back = findViewById(R.id.common_back);
        back.setVisibility(visible);
        if (visible == View.VISIBLE) {
            ClickUtil.click(back, this::onBackPressed);
        }
        TextView topbar_title = findViewById(R.id.common_tv_title);
        topbar_title.setText(title);
    }

    public void initTopTitleBar(CharSequence title) {
        initTopTitleBar(View.VISIBLE, title);
    }

    public void initTopRightText(String title, View.OnClickListener onClickListener) {
        TextView textView = findViewById(R.id.common_tv_right);
        textView.setText(title);
        textView.setVisibility(View.VISIBLE);
        if (onClickListener != null) {
            ClickUtil.click(textView, () -> onClickListener.onClick(textView));

        }
    }


}
