package com.gamerole.common.model.room;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;


import com.gamerole.common.model.bean.User;
import com.gamerole.common.model.repository.UserRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by lv on 18-5-7.
 */

public class UserViewModel extends ViewModel {
    private UserRepository userRepository;

    @Inject
    public UserViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LiveData<List<User>> getUsers() {
        if (userRepository == null) {
            Log.e("---->", "isNull");
            return null;
        }
        return userRepository.getUser();
    }

}
