package com.gamerole.common.base.presenter;

import android.content.Context;

import com.gamerole.common.base.loading.INetLoadingView;
import com.gamerole.common.base.view.IBaseView;
import com.lzy.okgo.OkGo;

import java.lang.ref.WeakReference;

/**
 * eated by Lee on 2018/1/25 0025.
 */
public class BasePresenter<VIEW extends IBaseView> implements INetLoadingView {
    public VIEW mView;
    private WeakReference<VIEW> mReference;
    public Context mContext;
    private INetLoadingView iNetLoadingView;


    public Context getContext() {
        return mContext;
    }

    /**
     * 绑定View
     *
     * @param view
     * @param context
     */
    public void attachView(VIEW view, Context context) {
        mReference = new WeakReference<VIEW>(view);
        mView = mReference.get();
        mContext = context;

    }


    @Override
    public void showLoading() {
        if (iNetLoadingView != null) {
            iNetLoadingView.showLoading();
        }
    }

    @Override
    public void showLoading(String msg) {
        if (iNetLoadingView != null) {
            iNetLoadingView.showLoading(msg);
        }
    }

    @Override
    public void showLoading(String msg, boolean isContinue) {
        if (iNetLoadingView != null) {
            iNetLoadingView.showLoading(msg, isContinue);
        }
    }

    @Override
    public void hideLoading(String msg) {
        if (iNetLoadingView != null) {
            iNetLoadingView.hideLoading(msg);
        }
    }

    @Override
    public void dismiss() {
        if (iNetLoadingView != null) {
            iNetLoadingView.hideLoading();
        }
    }

    @Override
    public void hideLoading() {
        if (iNetLoadingView != null) {
            iNetLoadingView.hideLoading();
        }
    }


    /**
     * 解除绑定
     */
    public void detachView() {
        if (mReference != null) {
            mReference.clear();
        }
    }

    public void cancelRequest() {
        OkGo.getInstance().cancelTag(this);
    }

}
