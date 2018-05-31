package com.gamerole.common.model.repository;


import android.arch.lifecycle.LiveData;

import com.gamerole.common.model.bean.User;
import com.gamerole.common.model.room.UserDao;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by lv on 18-5-7.
 */

public class UserRepository {

    public UserDao userDao;

    @Inject
    public UserRepository(@Named("UserDao") UserDao userDao){
        this.userDao=userDao;
    }
    public LiveData<List<User>> getUser() {
        updateUser();
        return userDao.getAll();
    }

    public void updateUser() {
        //@todo 获取用户信息，存储数据库
    }
}
