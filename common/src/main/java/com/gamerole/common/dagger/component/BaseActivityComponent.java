package com.gamerole.common.dagger.component;

import android.support.v7.app.AppCompatActivity;


import com.gamerole.common.dagger.module.UserModule;

import dagger.Component;

/**
 * @author qingchen
 * @date 17-11-12
 */
@Component(modules = UserModule.class)
public interface BaseActivityComponent {
    void inject(AppCompatActivity activity);
}
