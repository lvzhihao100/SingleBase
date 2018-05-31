package com.gamerole.common.dagger.module;

import com.gamerole.common.base.App;
import com.gamerole.common.model.room.UserDao;
import com.gamerole.common.model.room.UserDao_Impl;
import com.gamerole.common.model.room.UserDataBase;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lv on 18-5-7.
 */


@Module
public class UserModule {
    @Provides
    @Named("UserDao")
    UserDao provideUserDao() {
        return new UserDao_Impl(UserDataBase.getInstance(App.INSTANCE));
    }
}
