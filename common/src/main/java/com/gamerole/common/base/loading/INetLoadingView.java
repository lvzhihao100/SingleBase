package com.gamerole.common.base.loading;

/**
 * Created by vzhihao on 2017/3/12.
 */
public interface INetLoadingView {
    /**
     * 显示加载框
     */
    void showLoading();

    /**
     * 设置加载时数据
     *
     * @param msg
     */
    void showLoading(String msg);

    /**
     * 切换加载信息
     *
     * @param msg
     * @param isContinue
     */

    void showLoading(String msg, boolean isContinue);

    /**
     * 关闭加载框
     *
     * @param msg
     */
    void hideLoading(String msg);

    /**
     * 关闭加载匡
     */
    void dismiss();

    /**
     * 关闭加载匡
     */
    void hideLoading();
}
