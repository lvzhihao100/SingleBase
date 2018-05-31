package com.gamerole.common.base.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gamerole.common.base.presenter.BasePresenter;


/**
 * Created by Lee on 2018/2/28 0028.
 */

public abstract class BaseFragment<T extends BaseActivity, P extends BasePresenter> extends Fragment implements IBaseView {
    private ViewDataBinding fragmentCustom;

    protected T mActivity;
    protected P mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (fragmentCustom == null) {
            fragmentCustom = initBinding(DataBindingUtil.inflate(inflater, getLayoutId(), null, false));
        }
        ViewGroup parent = (ViewGroup) fragmentCustom.getRoot().getParent();
        if (parent != null) {
            parent.removeView(fragmentCustom.getRoot());
        }
        mActivity = (T) getActivity();
        mPresenter = getPresenter();
        mPresenter.attachView(this, mActivity);
        initView();
        initData();
        return fragmentCustom.getRoot();
    }


    protected abstract void initData();

    protected abstract void initView();

    protected abstract ViewDataBinding initBinding(ViewDataBinding inflate);

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    public abstract int getLayoutId();

    public abstract P getPresenter();


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
        intent.setClass(getActivity(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
}
