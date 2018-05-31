package com.gamerole.common.adapter.slimadapter;


import com.gamerole.common.adapter.slimadapter.viewinjector.IViewInjector;

/**
 * Created by linshuaibin on 01/04/2017.
 */

public interface SlimInjector<T> {
    void onInject(T data, IViewInjector injector);
}
